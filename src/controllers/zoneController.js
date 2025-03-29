import Zone from '../models/zone.js';

export const createZone = async (req, res) => {
  try {
    const zone = await Zone.create(req.body);
    res.status(201).json({
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
    const zone = await Zone.findByIdAndUpdate(
      req.params.id,
      req.body,
      {
        new: true,
        runValidators: true
      }
    );

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
