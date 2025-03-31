import express from 'express';
import cors from 'cors';
import morgan from 'morgan';
import dotenv from 'dotenv';
import mongoose from 'mongoose';

import authRoutes from './routes/auth.js';
import userRoutes from './routes/users.js';
import eventRoutes from './routes/events.js';
import mandalRoutes from './routes/mandals.js';
import zoneRoutes from './routes/zones.js';
import { API_ROUTES } from './constants/index.js';

dotenv.config();

// Debug environment variables
console.log('Environment variables:', {
  MONGODB_URI: process.env.MONGODB_URI,
  JWT_SECRET: process.env.JWT_SECRET,
  PORT: process.env.PORT
});

const app = express();

// Middleware
app.use(cors());
app.use(morgan('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Routes
app.use(`${API_ROUTES.BASE}${API_ROUTES.AUTH.BASE}`, authRoutes);
app.use(`${API_ROUTES.BASE}${API_ROUTES.USERS.BASE}`, userRoutes);
app.use(`${API_ROUTES.BASE}${API_ROUTES.EVENTS.BASE}`, eventRoutes);
app.use(`${API_ROUTES.BASE}${API_ROUTES.MANDALS.BASE}`, mandalRoutes);
app.use(`${API_ROUTES.BASE}${API_ROUTES.ZONES.BASE}`, zoneRoutes);

// Database connection
const MONGODB_URI = process.env.MONGODB_URI || 'mongodb://127.0.0.1:27017/samanvay_admin';
console.log('Attempting to connect to MongoDB at:', MONGODB_URI);

mongoose
  .connect(MONGODB_URI, {
    useNewUrlParser: true,
    useUnifiedTopology: true
  })
  .then(() => console.log('Connected to MongoDB successfully'))
  .catch((err) => console.error('MongoDB connection error:', err));

// Start server
const PORT = process.env.PORT || 3001;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
