import mongoose from 'mongoose';

const mandalSchema = new mongoose.Schema({
  name: {
    type: String,
    required: [true, 'Mandal name is required'],
    unique: true,
    trim: true
  },
  description: {
    type: String,
    required: [true, 'Mandal description is required']
  },
  zone: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Zone',
    required: [true, 'Zone is required']
  },
  leader: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: [true, 'Mandal leader is required']
  },
  members: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
  }],
  active: {
    type: Boolean,
    default: true
  },
  contactEmail: {
    type: String,
    required: [true, 'Contact email is required'],
    lowercase: true
  },
  contactPhone: {
    type: String
  },
  address: {
    street: String,
    city: String,
    state: String,
    zipCode: String
  }
}, {
  timestamps: true,
  toJSON: { virtuals: true },
  toObject: { virtuals: true }
});

// Virtual populate for events
mandalSchema.virtual('events', {
  ref: 'Event',
  foreignField: 'mandal',
  localField: '_id'
});

const Mandal = mongoose.model('Mandal', mandalSchema);

export default Mandal;
