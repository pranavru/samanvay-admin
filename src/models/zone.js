import mongoose from 'mongoose';

const zoneSchema = new mongoose.Schema({
  name: {
    type: String,
    required: [true, 'Zone name is required'],
    unique: true,
    trim: true
  },
  description: {
    type: String,
    required: [true, 'Zone description is required']
  },
  coordinator: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: [true, 'Zone coordinator is required']
  },
  region: {
    type: String,
    required: [true, 'Region is required']
  },
  active: {
    type: Boolean,
    default: true
  }
}, {
  timestamps: true,
  toJSON: { virtuals: true },
  toObject: { virtuals: true }
});

// Virtual populate for mandals
zoneSchema.virtual('mandals', {
  ref: 'Mandal',
  foreignField: 'zone',
  localField: '_id'
});

const Zone = mongoose.model('Zone', zoneSchema);

export default Zone;
