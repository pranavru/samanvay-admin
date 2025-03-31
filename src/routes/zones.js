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

const zoneRestrictTo = restrictTo([ROLES.ADMIN]);

router
  .route(API_ROUTES.ZONES.BASE)
  .get(getAllZones)
  .post(zoneRestrictTo, createZone);

router
  .route(API_ROUTES.ZONES.BY_ID)
  .get(getZone)
  .patch(zoneRestrictTo, updateZone)
  .delete(zoneRestrictTo, deleteZone);

export default router;
