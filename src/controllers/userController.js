import User from '../models/user.js';

export const getAllUsers = async (req, res) => {
  try {
    const users = await User.find()
      .populate('mandal', 'name')
      .populate('zone', 'name');

    res.status(200).json({
      status: 'success',
      results: users.length,
      data: users
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const getUser = async (req, res) => {
  try {
    const user = await User.findById(req.params.id)
      .populate('mandal', 'name')
      .populate('zone', 'name')
      .populate('eventsAttending');

    if (!user) {
      return res.status(404).json({
        status: 'error',
        message: 'User not found'
      });
    }

    res.status(200).json({
      status: 'success',
      data: user
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const updateUser = async (req, res) => {
  try {
    // Prevent password update through this route
    if (req.body.password) {
      return res.status(400).json({
        status: 'error',
        message: 'This route is not for password updates. Please use /updatePassword.'
      });
    }

    const user = await User.findByIdAndUpdate(
      req.params.id,
      req.body,
      {
        new: true,
        runValidators: true
      }
    );

    if (!user) {
      return res.status(404).json({
        status: 'error',
        message: 'User not found'
      });
    }

    res.status(200).json({
      status: 'success',
      data: user
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const deleteUser = async (req, res) => {
  try {
    const user = await User.findByIdAndDelete(req.params.id);

    if (!user) {
      return res.status(404).json({
        status: 'error',
        message: 'User not found'
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

export const registerUser = async (req, res) => {
  try {
    if (!req.body.email || !req.body.password) {
      return res.status(400).json({
        status: 'error',
        message: 'Email and password are required'
      });
    }

    if (req.body.role !== ROLES.ADMIN && req.body.role !== ROLES.SAMPARK) {
      return res.status(400).json({
        status: 'error',
        message: 'Invalid role'
      });
    }

    if (req.body.isActive) {
      return res.status(400).json({
        status: 'error',
        message: 'Invalid isActive value'
      });
    }

    const user = await User.create({
      ...req.body,
      isActive: false,
      role: ROLES.USER
    });
    
    res.status(201).json({
      status: 'success',
      data: user
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const getMe = (req, res) => {
  res.status(200).json({
    status: 'success',
    data: req.user
  });
};

export const updateMe = async (req, res) => {
  try {
    // Prevent password update through this route
    if (req.body.password) {
      return res.status(400).json({
        status: 'error',
        message: 'This route is not for password updates. Please use /updatePassword.'
      });
    }

    const updatedUser = await User.findByIdAndUpdate(
      req.user._id,
      req.body,
      {
        new: true,
        runValidators: true
      }
    );

    res.status(200).json({
      status: 'success',
      data: updatedUser
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const updatePassword = async (req, res) => {
  try {
    if (!req.body.password) {
      return res.status(400).json({
        status: 'error',
        message: 'Password is required'
      });
    }

    const user = await User.findById(req.user._id);

    if (!user) {
      return res.status(404).json({
        status: 'error',
        message: 'User not found'
      });
    }

    user.password = req.body.password;
    await user.save();

    res.status(200).json({
      status: 'success',
      data: user
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};
