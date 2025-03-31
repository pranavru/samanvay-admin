import express from 'express';
import { protect, restrictTo } from '../middleware/auth.js';
import {
  getAllUsers,
  getUser,
  updateUser,
  deleteUser,
  getMe,
  updateMe,
  updatePassword,
  registerUser
} from '../controllers/userController.js';
import { ROLES } from '../constants/index.js';

const router = express.Router();

// Protect all routes
router.use(protect);

// Routes for logged-in user's own profile
router.get('/me', getMe);
router.patch('/updateMe', updateMe);
router.patch('/updatePassword', updatePassword);

// Admin only routes
router.use(restrictTo([ROLES.ADMIN, ROLES.SAMPARK]));

router
  .route('/')
  .get(getAllUsers);

router
  .route('/:id')
  .get(getUser)
  .patch(updateUser)
  .delete(deleteUser);

router
  .route('/register')
  .post(registerUser);

export default router;
