# Samanvay Admin Application

A Node.js-based admin application for managing events, users, mandals (groups), and zones.

## Features

- User Authentication with JWT
- Role-based Access Control (Admin/User)
- Event Management
- Mandal (Group) Management
- Zone Management
- User Management

## Tech Stack

- Node.js
- Express.js
- MongoDB
- JWT Authentication
- Mongoose ODM

## Project Structure

```
samanvay-admin/
├── src/
│   ├── controllers/
│   │   ├── authController.js
│   │   ├── eventController.js
│   │   ├── mandalController.js
│   │   ├── userController.js
│   │   └── zoneController.js
│   ├── middleware/
│   │   └── auth.js
│   ├── models/
│   │   ├── event.js
│   │   ├── mandal.js
│   │   ├── user.js
│   │   └── zone.js
│   ├── routes/
│   │   ├── auth.js
│   │   ├── events.js
│   │   ├── mandals.js
│   │   ├── users.js
│   │   └── zones.js
│   └── index.js
├── .env.example
├── .env
├── package.json
├── README.md
└── DEPLOYMENT.md
```

## API Endpoints

### Authentication
- POST /api/auth/register - Register a new user
- POST /api/auth/login - Login user

### Users
- GET /api/users/me - Get current user's profile
- PATCH /api/users/updateMe - Update current user's profile
- GET /api/users - Get all users (admin only)
- GET /api/users/:id - Get specific user (admin only)
- PATCH /api/users/:id - Update user (admin only)
- DELETE /api/users/:id - Delete user (admin only)

### Events
- GET /api/events - Get all events
- POST /api/events - Create new event (admin only)
- GET /api/events/:id - Get specific event
- PATCH /api/events/:id - Update event (admin only)
- DELETE /api/events/:id - Delete event (admin only)
- POST /api/events/:id/register - Register for an event

### Mandals (Groups)
- GET /api/mandals - Get all mandals
- POST /api/mandals - Create new mandal (admin only)
- GET /api/mandals/:id - Get specific mandal
- PATCH /api/mandals/:id - Update mandal (admin only)
- DELETE /api/mandals/:id - Delete mandal (admin only)
- POST /api/mandals/:id/members - Add member to mandal (admin only)

### Zones
- GET /api/zones - Get all zones
- POST /api/zones - Create new zone (admin only)
- GET /api/zones/:id - Get specific zone
- PATCH /api/zones/:id - Update zone (admin only)
- DELETE /api/zones/:id - Delete zone (admin only)

## Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd samanvay-admin
```

2. Install dependencies:
```bash
npm install
```

3. Create environment file:
```bash
cp .env.example .env
```

4. Update .env with your configuration:
```env
PORT=3001
MONGODB_URI=mongodb://localhost:27017/samanvay_admin
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRES_IN=7d
```

5. Start MongoDB:
```bash
# macOS (with Homebrew)
brew services start mongodb-community

# Linux
sudo systemctl start mongod
```

6. Start the application:
```bash
# Development
npm run dev

# Production
npm start
```

## Development

- Uses ES Modules
- Nodemon for development auto-reload
- MongoDB for data persistence
- JWT for authentication
- Express.js for API routing

## Testing API Endpoints

You can test the API endpoints using curl or Postman. Here are some example curl commands:

1. Register a user:
```bash
curl -X POST -H "Content-Type: application/json" \\
  -d '{"name": "Admin User", "email": "admin@example.com", "password": "password123", "role": "admin"}' \\
  http://localhost:3001/api/auth/register
```

2. Login:
```bash
curl -X POST -H "Content-Type: application/json" \\
  -d '{"email": "admin@example.com", "password": "password123"}' \\
  http://localhost:3001/api/auth/login
```

3. Create a zone (with auth token):
```bash
curl -X POST \\
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \\
  -H "Content-Type: application/json" \\
  -d '{"name": "North Zone", "description": "Northern region zone", "region": "North"}' \\
  http://localhost:3001/api/zones
```

## Deployment

For deployment instructions, please refer to [DEPLOYMENT.md](DEPLOYMENT.md).

## License

MIT

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request
