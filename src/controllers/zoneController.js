import Zone from '../models/zone.js';

export const createZone = async (req, res) => {
  try {
    const zonesData = Array.isArray(req.body) ? req.body : [req.body];

    zonesData.map(zoneData => { zoneData.coordinator = req.user._id});

    // Validate all zones before creating any
    const validationPromises = zonesData.map(async (zoneData) => {
      const zone = new Zone(zoneData);
      return zone.validate();
    });

    try {
      await Promise.all(validationPromises);
    } catch (validationError) {
      return res.status(400).json({
        status: 'error',
        message: 'Validation failed',
        error: validationError.message
      });
    }

    // Create all zones
    const createdZones = await Zone.create(zonesData);

    // Populate coordinator details for response
    const populatedZones = await Zone.populate(createdZones, {
      path: 'coordinator',
      select: 'name email'
    });

    res.status(201).json({
      status: 'success',
      results: populatedZones.length,
      data: populatedZones
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const getAllZones = async (req, res) => {
  try {
    const zones = await Zone.find()
      .populate('coordinator', 'name email');

    res.status(200).json({
      status: 'success',
      results: zones.length,
      data: zones
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const getZone = async (req, res) => {
  try {
    const zone = await Zone.findById(req.params.id)
      .populate('coordinator', 'name email')
      .populate({
        path: 'mandals',
        populate: {
          path: 'leader',
          select: 'name email'
        }
      });

    if (!zone) {
      return res.status(404).json({
        status: 'error',
        message: 'Zone not found'
      });
    }

    res.status(200).json({
      status: 'success',
      data: zone
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const updateZone = async (req, res) => {
  try {
    const updates = Array.isArray(req.body) ? req.body : [{ id: req.params.id, ...req.body }];

    // Validate all updates
    for (const update of updates) {
      if (!update.id) {
        return res.status(400).json({
          status: 'error',
          message: 'Zone ID is required for each update'
        });
      }
    }

    // Update all zones
    const updatePromises = updates.map(async (update) => {
      const { id, ...updateData } = update;

      console.log('Updating zone with ID:', id, 'with data:', updateData);
    
      const updatedZone = await Zone.findByIdAndUpdate(
        id,
        updateData,
        {
          new: true,
          runValidators: true
        }
      ).populate('coordinator', 'name email');

      if (!updatedZone) {
        throw new Error(`Zone not found with ID: ${id}`);
      }

      return updatedZone;
    });

    const updatedZones = await Promise.all(updatePromises);

    res.status(200).json({
      status: 'success',
      results: updatedZones.length,
      data: updatedZones
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const deleteZone = async (req, res) => {
  try {
    const zone = await Zone.findById(req.params.id);

    if (!zone) {
      return res.status(404).json({
        status: 'error',
        message: 'Zone not found'
      });
    }

    // Check if zone has any mandals
    const mandalsCount = await zone.mandals.length;
    if (mandalsCount > 0) {
      return res.status(400).json({
        status: 'error',
        message: 'Cannot delete zone with existing mandals. Please reassign or delete the mandals first.'
      });
    }

    await Zone.findByIdAndDelete(req.params.id);

    res.status(204).json({
      status: 'success',
      data: null
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};
