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

const restrictToAdmin = restrictTo('admin');

router
  .route('/')
  .get(getAllZones)
  .patch(restrictToAdmin, updateZone)
  .post(restrictToAdmin, createZone);

router
  .route('/:id')
  .get(getZone)
  .delete(restrictToAdmin, deleteZone);

export default router;
