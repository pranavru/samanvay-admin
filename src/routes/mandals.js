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
import { API_ROUTES, ROLES } from '../constants/index.js';

const router = express.Router();

// Protect all routes
router.use(protect);

const mandalRestrictTo = restrictTo([ROLES.ADMIN]);

router
  .route(API_ROUTES.MANDALS.BASE)
  .get(getAllMandals)
  .post(mandalRestrictTo, createMandal);

router
  .route(API_ROUTES.MANDALS.BY_ID)
  .get(getMandal)
  .patch(mandalRestrictTo, updateMandal)
  .delete(mandalRestrictTo, deleteMandal);

router
  .route(API_ROUTES.MANDALS.MEMBERS)
  .post(mandalRestrictTo, addMember);

export default router;
