# Samanvay Admin API Documentation

## Authentication

### Register
- **URL**: `/api/auth/register`
- **Method**: `POST`
- **Access**: Public
- **Request Body**:
  ```json
  {
    "name": "string",
    "email": "string",
    "password": "string",
    "phone": "string",
    "role": "string" // Optional, defaults to "user"
  }
  ```
- **Response**:
  ```json
  {
    "status": "success",
    "data": {
      "user": {
        "_id": "string",
        "name": "string",
        "email": "string",
        "role": "string"
      },
      "token": "string"
    }
  }
  ```

### Login
- **URL**: `/api/auth/login`
- **Method**: `POST`
- **Access**: Public
- **Request Body**:
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Response**: Same as Register

## Users

### Get Current User
- **URL**: `/api/users/me`
- **Method**: `GET`
- **Access**: Authenticated Users
- **Response**:
  ```json
  {
    "status": "success",
    "data": {
      "user": {
        "_id": "string",
        "name": "string",
        "email": "string",
        "role": "string",
        "eventsAttending": [
          {
            "event": "string",
            "ride": "string"
          }
        ]
      }
    }
  }
  ```

### Update Current User
- **URL**: `/api/users/updateMe`
- **Method**: `PATCH`
- **Access**: Authenticated Users
- **Request Body**:
  ```json
  {
    "name": "string",
    "email": "string",
    "phone": "string"
  }
  ```

### Update Password
- **URL**: `/api/users/updatePassword`
- **Method**: `PATCH`
- **Access**: Authenticated Users
- **Request Body**:
  ```json
  {
    "password": "string"
  }
  ```

### Get All Users
- **URL**: `/api/users`
- **Method**: `GET`
- **Access**: Admin, Sampark
- **Query Parameters**:
  - `role`: Filter by role
  - `mandal`: Filter by mandal ID

### Register New User
- **URL**: `/api/users/register`
- **Method**: `POST`
- **Access**: Admin, Sampark
- **Request Body**:
  ```json
  {
    "name": "string",
    "email": "string",
    "password": "string",
    "phone": "string",
    "role": "string",
    "mandal": "string"
  }
  ```

## Events

### Get All Events
- **URL**: `/api/events`
- **Method**: `GET`
- **Access**: All authenticated users
- **Query Parameters**:
  - `status`: Filter by status
  - `mandal`: Filter by mandal ID
  - `date`: Filter by date range

### Create Event
- **URL**: `/api/events`
- **Method**: `POST`
- **Access**: Admin only
- **Request Body**:
  ```json
  {
    "title": "string",
    "description": "string",
    "date": "date",
    "mandal": "string",
    "status": "string"
  }
  ```

### Register for Event
- **URL**: `/api/events/:id/register`
- **Method**: `POST`
- **Access**: All authenticated users
- **Request Body**:
  ```json
  {
    "ride": "string" // Optional
  }
  ```

### Update Ride Details
- **URL**: `/api/events/:id/updateRideDetails`
- **Method**: `PATCH`
- **Access**: Admin, Ride Manager, Sampark
- **Request Body**:
  ```json
  {
    "ride": "string"
  }
  ```

### Confirm Attendance
- **URL**: `/api/events/:id/confirmAttendance`
- **Method**: `POST`
- **Access**: Admin, Sampark
- **Request Body**:
  ```json
  {
    "attendance": "boolean"
  }
  ```

## Mandals

### Get All Mandals
- **URL**: `/api/mandals`
- **Method**: `GET`
- **Access**: All authenticated users
- **Query Parameters**:
  - `zone`: Filter by zone ID

### Create Mandal
- **URL**: `/api/mandals`
- **Method**: `POST`
- **Access**: Admin only
- **Request Body**:
  ```json
  {
    "name": "string",
    "description": "string",
    "zone": "string"
  }
  ```

### Add Member to Mandal
- **URL**: `/api/mandals/:id/members`
- **Method**: `POST`
- **Access**: Admin only
- **Request Body**:
  ```json
  {
    "userId": "string"
  }
  ```

## Zones

### Get All Zones
- **URL**: `/api/zones`
- **Method**: `GET`
- **Access**: All authenticated users

### Create Zone
- **URL**: `/api/zones`
- **Method**: `POST`
- **Access**: Admin only
- **Request Body**:
  ```json
  {
    "name": "string",
    "description": "string",
    "region": "string"
  }
  ```

## Role-Based Access

### Admin
- Full access to all endpoints
- Can create/update/delete all resources
- Can manage user roles

### Sampark
- Can view all users
- Can register new users
- Can manage event attendance
- Can update ride details

### Ride Manager
- Can update ride details for events
- Can view events and attendees

### Yuvak
- Can view events
- Can register for events
- Can update their own profile

## Running the Mock Data Script

To populate the database with mock data:

1. Ensure MongoDB is running
2. Run the seed script:
```bash
node src/scripts/seedData.js
```

This will create:
- 3 zones (North, South, East)
- 3 mandals (one in each zone)
- 5 users with different roles
- 3 events (past, current, future)
- Sample event registrations and attendance
