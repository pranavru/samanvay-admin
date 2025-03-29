import express from 'express';
import { protect, restrictTo } from '../middleware/auth.js';
import {
  createEvent,
  getAllEvents,
  getEvent,
  updateEvent,
  deleteEvent,
  registerForEvent
} from '../controllers/eventController.js';

const router = express.Router();

// Protect all routes
router.use(protect);

router
  .route('/')
  .get(getAllEvents)
  .post(restrictTo('admin'), createEvent);

router
  .route('/:id')
  .get(getEvent)
  .patch(restrictTo('admin'), updateEvent)
  .delete(restrictTo('admin'), deleteEvent);

router.post('/:id/register', registerForEvent);

export default router;
