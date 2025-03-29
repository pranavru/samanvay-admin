import express from 'express';
import { protect, restrictTo } from '../middleware/auth.js';
import {
  getAllUsers,
  getUser,
  updateUser,
  deleteUser,
  getMe,
  updateMe
} from '../controllers/userController.js';

const router = express.Router();

// Protect all routes
router.use(protect);

// Routes for logged-in user's own profile
router.get('/me', getMe);
router.patch('/updateMe', updateMe);

// Admin only routes
router.use(restrictTo('admin'));

router
  .route('/')
  .get(getAllUsers);

router
  .route('/:id')
  .get(getUser)
  .patch(updateUser)
  .delete(deleteUser);

export default router;
