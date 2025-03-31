import express from 'express';
import { protect, restrictTo } from '../middleware/auth.js';
import {
  createEvent,
  getAllEvents,
  getEvent,
  updateEvent,
  deleteEvent,
  registerForEvent,
  confirmAttendance,
  updateAttendance,
  updateRideDetails
} from '../controllers/eventController.js';
import { API_ROUTES, ROLES } from '../constants/index.js';

const router = express.Router();

// Protect all routes
router.use(protect);

const eventRestrictTo = restrictTo([ROLES.ADMIN]);
const rideRestrictTo = restrictTo([ROLES.ADMIN, ROLES.RIDE_MANAGER, ROLES.SAMPARK]);
const attendanceRestrictedTo = restrictTo([ROLES.ADMIN, ROLES.SAMPARK]);

router
  .route('/')
  .get(getAllEvents)
  .post(eventRestrictTo, createEvent);

router
  .route(API_ROUTES.EVENTS.BY_ID)
  .get(getEvent)
  .patch(eventRestrictTo, updateEvent)
  .delete(eventRestrictTo, deleteEvent);

router.post(API_ROUTES.EVENTS.REGISTER, registerForEvent);

router
  .route(API_ROUTES.EVENTS.CONFIRM_ATTENDANCE)
  .post(attendanceRestrictedTo, confirmAttendance)
  .patch(attendanceRestrictedTo, updateAttendance);

router
  .route(API_ROUTES.EVENTS.UPDATE_RIDE_DETAILS)
  .patch(rideRestrictTo, updateRideDetails);

export default router;
