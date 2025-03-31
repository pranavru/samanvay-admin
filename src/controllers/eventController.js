import Event from '../models/event.js';
import User from '../models/user.js';

export const createEvent = async (req, res) => {
  try {
    const event = await Event.create({
      ...req.body,
      organizer: req.user._id
    });

    res.status(201).json({
      status: 'success',
      data: event
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const getAllEvents = async (req, res) => {
  try {
    const events = await Event.find()
      .populate('mandal', 'name')
      .populate('organizer', 'name');

    res.status(200).json({
      status: 'success',
      results: events.length,
      data: events
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const getEvent = async (req, res) => {
  try {
    const event = await Event.findById(req.params.id)
      .populate('mandal', 'name')
      .populate('organizer', 'name')
      .populate('attendees', 'name email');

    if (!event) {
      return res.status(404).json({
        status: 'error',
        message: 'Event not found'
      });
    }

    res.status(200).json({
      status: 'success',
      data: event
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const updateEvent = async (req, res) => {
  try {
    const event = await Event.findByIdAndUpdate(
      req.params.id,
      req.body,
      {
        new: true,
        runValidators: true
      }
    );

    if (!event) {
      return res.status(404).json({
        status: 'error',
        message: 'Event not found'
      });
    }

    res.status(200).json({
      status: 'success',
      data: event
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const deleteEvent = async (req, res) => {
  try {
    const event = await Event.findByIdAndDelete(req.params.id);

    if (!event) {
      return res.status(404).json({
        status: 'error',
        message: 'Event not found'
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

export const registerForEvent = async (req, res) => {
  try {
    const event = await Event.findById(req.params.id);

    if (!event) {
      return res.status(404).json({
        status: 'error',
        message: 'Event not found'
      });
    }

    if (event.isFull) {
      return res.status(400).json({
        status: 'error',
        message: 'Event is already full'
      });
    }

    if(event.date < new Date()) {
      return res.status(400).json({
        status: 'error',
        message: 'Event date is in the past'
      });
    }

    if (event.attendees.some(attendee => attendee.userId.equals(req.user._id))) {
      return res.status(400).json({
        status: 'error',
        message: 'You are already registered for this event'
      });
    }

    // Add user to event attendees
    event.attendees.push({
      userId: req.user._id,
      ride: req.body.ride
    });
    await event.save();

    // Add event to user's events list
    const user = await User.findById(req.user._id);
    user.eventsAttending.push({
      event: event._id,
      ride: req.body.ride
    });
    
    await user.save();

    res.status(200).json({
      status: 'success',
      message: 'Successfully registered for the event'
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const updateRideDetails = async (req, res) => {
  try {
    const event = await Event.findById(req.params.id);

    if (!event) {
      return res.status(404).json({
        status: 'error',
        message: 'Event not found'
      });
    }

    if (!event.attendees.some(attendee => attendee.userId.equals(req.user._id))) {
      return res.status(400).json({
        status: 'error',
        message: 'User is not registered for this event'
      });
    }

    // Update ride in event attendees
    event.attendees = event.attendees.map(attendee => {
      if (attendee.userId.equals(req.user._id)) {
        return {
          userId: req.user._id,
          ride: req.body.ride
        };
      }
      return attendee;
    });
    await event.save();

    // Update ride in user's events list
    const user = await User.findById(req.user._id);
    user.events = user.events.map(userEvent => {
      if (userEvent.event.equals(event._id)) {
        return {
          event: event._id,
          ride: req.body.ride
        };
      }
      return userEvent;
    });
    await user.save();

    res.status(200).json({
      status: 'success',
      message: 'Successfully updated ride details'
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

export const confirmAttendance = async (req, res) => {
  try {
    const event = await Event.findById(req.params.id);

    if (!event) {
      return res.status(404).json({
        status: 'error',
        message: 'Event not found'
      });
    }

    if (!event.attendees.includes(req.user._id)) {
      return res.status(400).json({
        status: 'error',
        message: 'You are not registered for this event'
      });
    }

    event.attendance.push({
      user: req.user._id,
      attendance: true
    });
    await event.save();

    res.status(200).json({
      status: 'success',
      message: 'Successfully confirmed attendance'
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};
        
export const updateAttendance = async (req, res) => {
  try {
    const event = await Event.findById(req.params.id);

    if (!event) {
      return res.status(404).json({
        status: 'error',
        message: 'Event not found'
      });
    }

    if (!event.attendance.includes(req.user._id)) {
      return res.status(400).json({
        status: 'error',
        message: 'You are not registered for this event'
      });
    }

    event.attendance.push({
      user: req.user._id,
      attendance: req.body.attendance
    });
    await event.save();

    res.status(200).json({
      status: 'success',
      message: 'Successfully updated attendance'
    });
  } catch (error) {
    res.status(400).json({
      status: 'error',
      message: error.message
    });
  }
};

