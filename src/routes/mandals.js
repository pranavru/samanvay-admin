import express from 'express';
import { protect, restrictTo } from '../middleware/auth.js';
import {
  createMandal,
  getAllMandals,
  getMandal,
  updateMandal,
  deleteMandal,
  addMember
} from '../controllers/mandalController.js';

const router = express.Router();

// Protect all routes
router.use(protect);

router
  .route('/')
  .get(getAllMandals)
  .post(restrictTo('admin'), createMandal);

router
  .route('/:id')
  .get(getMandal)
  .patch(restrictTo('admin'), updateMandal)
  .delete(restrictTo('admin'), deleteMandal);

router
  .route('/:id/members')
  .post(restrictTo('admin'), addMember);

export default router;
