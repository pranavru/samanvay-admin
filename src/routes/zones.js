import express from 'express';
import { protect, restrictTo } from '../middleware/auth.js';
import {
  createZone,
  getAllZones,
  getZone,
  updateZone,
  deleteZone
} from '../controllers/zoneController.js';
import { ROLES } from '../constants/index.js';

const router = express.Router();

// Protect all routes
router.use(protect);

const zoneRestrictTo = restrictTo([ROLES.ADMIN]);

router
  .route('/')
  .get(getAllZones)
  .post(zoneRestrictTo, createZone);

router
  .route('/:id')
  .get(getZone)
  .patch(zoneRestrictTo, updateZone)
  .delete(zoneRestrictTo, deleteZone);

export default router;
