import express from 'express';
import { protect, restrictTo } from '../middleware/auth.js';
import {
  createZone,
  getAllZones,
  getZone,
  updateZone,
  deleteZone
} from '../controllers/zoneController.js';
import { API_ROUTES, ROLES } from '../constants/index.js';

const router = express.Router();

// Protect all routes
router.use(protect);

const restrictToAdmin = restrictTo('admin');

router
  .route(API_ROUTES.ZONES.BASE)
  .get(getAllZones)
  .patch(restrictToAdmin, updateZone)
  .post(restrictToAdmin, createZone);

router
  .route(API_ROUTES.ZONES.BY_ID)
  .get(getZone)
  .delete(restrictToAdmin, deleteZone);

export default router;
