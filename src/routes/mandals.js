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
import { ROLES } from '../constants/index.js';

const router = express.Router();

// Protect all routes
router.use(protect);

const mandalRestrictTo = restrictTo([ROLES.ADMIN]);

router
  .route('/')
  .get(getAllMandals)
  .post(mandalRestrictTo, createMandal);

router
  .route('/:id')
  .get(getMandal)
  .patch(mandalRestrictTo, updateMandal)
  .delete(mandalRestrictTo, deleteMandal);

router
  .route('/:id/members')
  .post(mandalRestrictTo, addMember);

export default router;
