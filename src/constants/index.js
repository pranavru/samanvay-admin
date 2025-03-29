// User Roles
export const ROLES = {
  ADMIN: 'admin',
  USER: 'user'
};

// API Routes
export const API_ROUTES = {
  BASE: '/api',
  AUTH: {
    BASE: '/auth',
    REGISTER: '/register',
    LOGIN: '/login'
  },
  USERS: {
    BASE: '/users',
    ME: '/me',
    UPDATE_ME: '/updateMe',
    BY_ID: '/:id'
  },
  EVENTS: {
    BASE: '/events',
    BY_ID: '/:id',
    REGISTER: '/:id/register'
  },
  MANDALS: {
    BASE: '/mandals',
    BY_ID: '/:id',
    MEMBERS: '/:id/members'
  },
  ZONES: {
    BASE: '/zones',
    BY_ID: '/:id'
  }
};

// Event Status
export const EVENT_STATUS = {
  UPCOMING: 'upcoming',
  ONGOING: 'ongoing',
  COMPLETED: 'completed',
  CANCELLED: 'cancelled'
};

// HTTP Status Codes
export const HTTP_STATUS = {
  OK: 200,
  CREATED: 201,
  NO_CONTENT: 204,
  BAD_REQUEST: 400,
  UNAUTHORIZED: 401,
  FORBIDDEN: 403,
  NOT_FOUND: 404,
  INTERNAL_SERVER_ERROR: 500
};

// Response Messages
export const MESSAGES = {
  AUTH: {
    USER_EXISTS: 'User already exists',
    INVALID_CREDENTIALS: 'Invalid email or password',
    LOGIN_SUCCESS: 'Logged in successfully',
    REGISTER_SUCCESS: 'Registered successfully'
  },
  USERS: {
    NOT_FOUND: 'User not found',
    UPDATE_SUCCESS: 'User updated successfully',
    DELETE_SUCCESS: 'User deleted successfully'
  },
  EVENTS: {
    NOT_FOUND: 'Event not found',
    ALREADY_REGISTERED: 'You are already registered for this event',
    EVENT_FULL: 'Event is already full',
    REGISTER_SUCCESS: 'Successfully registered for the event'
  },
  MANDALS: {
    NOT_FOUND: 'Mandal not found',
    MEMBER_EXISTS: 'User is already a member of this mandal'
  },
  ZONES: {
    NOT_FOUND: 'Zone not found',
    HAS_MANDALS: 'Cannot delete zone with existing mandals. Please reassign or delete the mandals first.'
  },
  COMMON: {
    UNAUTHORIZED: 'Please log in to access this resource',
    FORBIDDEN: 'You do not have permission to perform this action',
    NOT_FOUND: 'Resource not found',
    SERVER_ERROR: 'Internal server error'
  }
};
