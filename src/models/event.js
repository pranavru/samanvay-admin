import mongoose from 'mongoose';
import { EVENT_STATUS } from '../constants/index.js';

const eventSchema = new mongoose.Schema({
  title: {
    type: String,
    required: [true, 'Event title is required'],
    trim: true
  },
  description: {
    type: String,
    default: '',
    trim: true
  },
  date: {
    type: Date,
    required: [true, 'Event date is required']
  },
  location: {
    type: String,
    required: [true, 'Event location is required']
  },
  mandal: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Mandal'
  },
  attendees: [{
    userId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'User'
    },
    ride: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'Ride',
    }
  }],
  attendance: [{
    user: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'User'
    },
    attendance: {
      type: Boolean,
      default: false
    }
  }],
  rides: {
    type: [mongoose.Schema.Types.ObjectId],
    ref: 'Ride'
  },
  status: {
    type: String,
    enum: Object.values(EVENT_STATUS),
    default: EVENT_STATUS.UPCOMING
  },
  organizer: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  }
}, {
  timestamps: true
});

// Virtual for checking if event is full
eventSchema.virtual('isFull').get(function() {
  if (!this.capacity) return false;
  return this.attendees.length >= this.capacity;
});

const Event = mongoose.model('Event', eventSchema);

export default Event;
