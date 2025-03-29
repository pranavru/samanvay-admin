import Mandal from '../models/mandal.js';

export const createMandal = async (req, res) => {
  try {
    const mandal = await Mandal.create(req.body);
    res.status(201).json({
      status: 'success',
      data: mandal
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const getAllMandals = async (req, res) => {
  try {
    const mandals = await Mandal.find()
      .populate('zone', 'name')
      .populate('leader', 'name email')
      .populate('members', 'name email');

    res.status(200).json({
      status: 'success',
      results: mandals.length,
      data: mandals
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const getMandal = async (req, res) => {
  try {
    const mandal = await Mandal.findById(req.params.id)
      .populate('zone', 'name')
      .populate('leader', 'name email')
      .populate('members', 'name email')
      .populate('events');

    if (!mandal) {
      return res.status(404).json({
        status: 'error',
        message: 'Mandal not found'
      });
    }

    res.status(200).json({
      status: 'success',
      data: mandal
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const updateMandal = async (req, res) => {
  try {
    const mandal = await Mandal.findByIdAndUpdate(
      req.params.id,
      req.body,
      {
        new: true,
        runValidators: true
      }
    );

    if (!mandal) {
      return res.status(404).json({
        status: 'error',
        message: 'Mandal not found'
      });
    }

    res.status(200).json({
      status: 'success',
      data: mandal
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const deleteMandal = async (req, res) => {
  try {
    const mandal = await Mandal.findByIdAndDelete(req.params.id);

    if (!mandal) {
      return res.status(404).json({
        status: 'error',
        message: 'Mandal not found'
      });
    }

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

export const addMember = async (req, res) => {
  try {
    const mandal = await Mandal.findById(req.params.id);

    if (!mandal) {
      return res.status(404).json({
        status: 'error',
        message: 'Mandal not found'
      });
    }

    const { userId } = req.body;
    
    if (mandal.members.includes(userId)) {
      return res.status(400).json({
        status: 'error',
        message: 'User is already a member of this mandal'
      });
    }

    mandal.members.push(userId);
    await mandal.save();

    res.status(200).json({
      status: 'success',
      message: 'Member added successfully'
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};
