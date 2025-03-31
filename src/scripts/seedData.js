import mongoose from 'mongoose';
import dotenv from 'dotenv';
import User from '../models/user.js';
import Zone from '../models/zone.js';
import Mandal from '../models/mandal.js';
import Event from '../models/event.js';
import { ROLES, EVENT_STATUS } from '../constants/index.js';

dotenv.config();

const MONGODB_URI = process.env.MONGODB_URI || 'mongodb://127.0.0.1:27017/samanvay_admin';

// Connect to MongoDB
mongoose.connect(MONGODB_URI)
  .then(() => console.log('Connected to MongoDB for seeding'))
  .catch(err => console.error('MongoDB connection error:', err));

// Mock data
const zones = [
  {
    name: 'North Zone',
    description: 'Northern region of the city',
    region: 'North'
  },
  {
    name: 'South Zone',
    description: 'Southern region of the city',
    region: 'South'
  },
  {
    name: 'East Zone',
    description: 'Eastern region of the city',
    region: 'East'
  }
];

const users = [
  {
    name: 'Admin User',
    email: 'admin@example.com',
    password: 'Password123!',
    role: ROLES.ADMIN,
    phone: '1234567890'
  },
  {
    name: 'Sampark User',
    email: 'sampark@example.com',
    password: 'Password123!',
    role: ROLES.SAMPARK,
    phone: '2345678901'
  },
  {
    name: 'Ride Manager',
    email: 'ride@example.com',
    password: 'Password123!',
    role: ROLES.RIDE_MANAGER,
    phone: '3456789012'
  },
  {
    name: 'Yuvak 1',
    email: 'yuvak1@example.com',
    password: 'Password123!',
    role: ROLES.YUVAK,
    phone: '4567890123'
  },
  {
    name: 'Yuvak 2',
    email: 'yuvak2@example.com',
    password: 'Password123!',
    role: ROLES.YUVAK,
    phone: '5678901234'
  }
];

const mandals = [
  {
    name: 'Mandal 1',
    description: 'First mandal in North Zone',
    contactEmail: 'mandal1@example.com',
    contactPhone: '1234567890'
  },
  {
    name: 'Mandal 2',
    description: 'Second mandal in South Zone',
    contactEmail: 'mandal2@example.com',
    contactPhone: '2345678901'
  },
  {
    name: 'Mandal 3',
    description: 'Third mandal in East Zone',
    contactEmail: 'mandal3@example.com',
    contactPhone: '3456789012'
  }
];

// Get dates
const now = new Date();
const pastDate = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000); // 7 days ago
const futureDate = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000); // 7 days from now

const events = [
  {
    title: 'Past Event',
    description: 'Event that happened in the past',
    date: pastDate,
    status: EVENT_STATUS.COMPLETED,
    location: '123 Main St, Mumbai, Maharashtra 400001'
  },
  {
    title: 'Current Event',
    description: 'Ongoing event',
    date: now,
    status: EVENT_STATUS.ONGOING,
    location: '456 Park Ave, Mumbai, Maharashtra 400002'
  },
  {
    title: 'Future Event',
    description: 'Upcoming event',
    date: futureDate,
    status: EVENT_STATUS.UPCOMING,
    location: '789 Lake Rd, Mumbai, Maharashtra 400003'
  }
];

// Seed data
async function seedData() {
  try {
    // Clear existing data
    await User.deleteMany({});
    await Zone.deleteMany({});
    await Mandal.deleteMany({});
    await Event.deleteMany({});

    // Create admin and sampark users first for zone coordinators
    const adminUser = await User.create(users[0]); // Admin
    const samparkUser = await User.create(users[1]); // Sampark
    console.log('Admin and Sampark users created');

    // Create zones with coordinators
    const zonesWithCoordinators = zones.map(zone => ({
      ...zone,
      coordinator: samparkUser._id
    }));
    const createdZones = await Zone.create(zonesWithCoordinators);
    console.log('Zones created:', createdZones.length);

    // Create remaining users
    const remainingUsers = users.slice(2); // Skip admin and sampark who are already created
    const otherUsers = await Promise.all(
      remainingUsers.map(async (user) => {
        const newUser = new User(user);
        await newUser.save();
        return newUser;
      })
    );
    const createdUsers = [adminUser, samparkUser, ...otherUsers];
    console.log('All users created:', createdUsers.length);

    // Create mandals with zones and leaders
    const createdMandals = await Promise.all(
      mandals.map(async (mandal, index) => {
        const newMandal = new Mandal({
          ...mandal,
          zone: createdZones[index]._id,
          leader: createdUsers[3 + (index % 2)]._id // Assign Yuvak users as leaders
        });
        await newMandal.save();
        return newMandal;
      })
    );
    console.log('Mandals created:', createdMandals.length);

    // Create events with mandals
    const createdEvents = await Promise.all(
      events.map(async (event, index) => {
        const newEvent = new Event({
          ...event,
          mandal: createdMandals[index]._id,
          organizer: createdUsers[0]._id // Admin as organizer
        });

        // Add some attendees
        newEvent.attendees.push({
          userId: createdUsers[3]._id, // Yuvak 1
          ride: null
        });
        newEvent.attendees.push({
          userId: createdUsers[4]._id, // Yuvak 2
          ride: null
        });

        // Add attendance for past event
        if (event.status === EVENT_STATUS.COMPLETED) {
          newEvent.attendance.push({
            user: createdUsers[3]._id,
            attendance: true
          });
          newEvent.attendance.push({
            user: createdUsers[4]._id,
            attendance: true
          });
        }

        await newEvent.save();
        return newEvent;
      })
    );
    console.log('Events created:', createdEvents.length);

    // Update users with events
    await Promise.all(
      createdUsers.slice(3).map(async (user) => {
        user.eventsAttending = createdEvents.map(event => ({
          event: event._id,
          ride: null
        }));
        await user.save();
      })
    );
    console.log('Users updated with events');

    console.log('Seeding completed successfully');
    process.exit(0);
  } catch (error) {
    console.error('Seeding error:', error);
    process.exit(1);
  }
}

seedData();
