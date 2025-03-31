import mongoose from 'mongoose';

const rideSchema = new mongoose.Schema({
  isRequired: {
    type: Boolean,
    default: false
  },
  timeRange: {
    from: {
      type: Date,
      required: [true, 'Time range is required']
    },
    to: {
      type: Date,
      required: [true, 'Time range is required']
    }
  },
  isDone: {
    type: Boolean,
    default: false
  },
  givenBy: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
  }
}, {
  timestamps: true
});

const Ride = mongoose.model('Ride', rideSchema);

export default Ride;