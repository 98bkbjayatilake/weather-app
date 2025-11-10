# Quick Start Guide

This guide will help you get the Weather App up and running quickly.

## Prerequisites
- Java 17+
- Node.js 18+
- Maven 3.6+
- Auth0 Account

## Quick Setup (5 minutes)

### 1. Add Configuration Files
1. Download & extract the Git repository.
2. Download the configuration files from [here](https://drive.google.com/file/d/1P-e4TNbSVUplRgjVZoWcbHlVLrmn6r5r/view?usp=sharing)
3. Extract the `.zip` file, then place the `.env` files, `application.yml` file & `cities.json` file in the correct locations inside the project:

   | file in `.zip`            | project location                             | 
      |---------------------------|----------------------------------------------| 
   | `backend/.env`            | `backend/.env`                               |
   | `backend/application.yml` | `backend/src/main/resources/application.yml` |
   | `backend/cities.json`     | `backend/src/main/resources/cities.json`     |
   | `frontend/.env`           | `frontend/.env`                              |



### 2. Backend Setup (1 minute)

```bash
cd backend
```

Run:
```bash
mvn spring-boot:run
```

### 3. Frontend Setup (2 minutes)

```bash
cd frontend
npm install
```

Run:
```bash
npm run dev
```

### 4. Test (30 seconds)

1. Open `http://localhost:3000`
2. Click "Login to Continue"
3. Login with:
   - Email: `careers@fidenz.com`
   - Password: `Pass#fidenz`
4. Complete MFA (check email)
5. Browse cities and view weather!

## Troubleshooting

**Backend won't start**: Check Java version (`java -version`) and ensure .env is configured

**Frontend won't start**: Run `npm install` again and check Node version (`node -v`)

**Can't login**: Verify Auth0 credentials in .env files match your Auth0 dashboard

**MFA code not received**: Check spam folder or resend from Auth0 dashboard


## Project Structure

```
spring-boot-react-app/
├── backend/          # Spring Boot API
├── frontend/         # React SPA
├── README.md         # Full documentation
├── AUTH0_SETUP.md    # Detailed Auth0 guide
└── QUICKSTART.md     # This file
```

## Key Features

✅ Secure Auth0 authentication
✅ Multi-Factor Authentication via email
✅ Protected API endpoints
✅ Protected React routes
✅ Disabled public signups
✅ City and weather information
✅ Modern responsive UI

## API Endpoints

**Protected** (requires authentication):
- `GET /api/cities` - List all cities
- `GET /api/cities/{id}` - Get city details
- `GET /api/weather/city/{id}` - Get weather for city

**Public**:
- `GET /api/public/health` - Health check

## Tech Stack

**Backend**: Spring Boot 3.2, Spring Security, Auth0, Maven
**Frontend**: React 18, Auth0 React SDK, Vite, React Router

