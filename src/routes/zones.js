import express from 'express';
import { protect, restrictTo } from '../middleware/auth.js';
import {
  createZone,
  getAllZones,
  getZone,
  updateZone,
  deleteZone
} from '../controllers/zoneController.js';

const router = express.Router();

// Protect all routes
router.use(protect);

router
  .route('/')
  .get(getAllZones)
  .post(restrictTo('admin'), createZone);

router
  .route('/:id')
  .get(getZone)
  .patch(restrictTo('admin'), updateZone)
  .delete(restrictTo('admin'), deleteZone);

export default router;
