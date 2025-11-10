# Weather App - Spring Boot + React with Auth0 Authentication

A secure weather application built with Spring Boot backend and React frontend, featuring Auth0 authentication and authorization.

## 🌟 Features

- **Secure Authentication**: Auth0 integration with OAuth 2.0
- **Multi-Factor Authentication (MFA)**: Email verification for enhanced security
- **Protected Routes**: API endpoints and pages accessible only to authenticated users
- **City Weather Information**: Browse cities and view real-time weather data
- **Restricted Signups**: Only pre-registered users can access the application
- **Modern Tech Stack**: Spring Boot 3.2, React 18, Vite

## 📋 Prerequisites

- Java 17 or higher
- Node.js 18 or higher
- Maven 3.6+
- Auth0 Account
- (Optional) OpenWeatherMap API key for real weather data

## 🚀 Getting Started

### Step 1: Backend Setup

#### 1.1 Configure Environment Variables
1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Copy the example environment file:
   ```bash
   cp .env.example .env
   ```

3. Edit the `.env` file with your Auth0 credentials:
   ```properties
   AUTH0_DOMAIN=your-tenant.auth0.com
   AUTH0_AUDIENCE=https://weather-app-api
   AUTH0_ISSUER_URI=https://your-tenant.auth0.com/
   WEATHER_API_KEY=your-openweathermap-api-key (optional)
   ```

#### 1.2 Build and Run the Backend
```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Step 2: Frontend Setup

#### 2.1 Install Dependencies
1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install npm packages:
   ```bash
   npm install
   ```

#### 2.2 Configure Environment Variables
1. Copy the example environment file:
   ```bash
   cp .env.example .env
   ```

2. Edit the `.env` file with your Auth0 credentials:
   ```properties
   VITE_AUTH0_DOMAIN=your-tenant.auth0.com
   VITE_AUTH0_CLIENT_ID=your-client-id-from-auth0
   VITE_AUTH0_AUDIENCE=https://weather-app-api
   VITE_API_BASE_URL=http://localhost:8080
   ```

#### 2.3 Run the Frontend
```bash
npm run dev
```

The frontend will start on `http://localhost:3000`

## 🧪 Testing the Application

### Test User Credentials
- **Email**: `careers@fidenz.com`
- **Password**: `Pass#fidenz`

### Testing Flow
1. Open `http://localhost:3000` in your browser
2. Click **Login to Continue**
3. Enter the test credentials
4. Complete MFA if prompted (check email)
5. You should be redirected to the Cities page
6. Click on any city to view its weather information
7. Test the logout functionality

### Testing Protected Routes
1. Try accessing `http://localhost:3000/cities` without logging in - you should be redirected to the landing page
2. Try accessing backend APIs directly:
   ```bash
   # Without token (should return 401)
   curl http://localhost:8080/api/cities
   
   # Public endpoint (should work)
   curl http://localhost:8080/api/public/health
   ```

## 🔒 Security Features

### 1. Authentication & Authorization
- **JWT-based authentication** using Auth0
- **OAuth 2.0 protocol** for secure token exchange
- **Protected API endpoints** requiring valid access tokens
- **Protected React routes** redirecting unauthenticated users

### 2. Multi-Factor Authentication (MFA)
- **Email-based MFA** enabled in Auth0
- Additional security layer for user accounts
- Configurable MFA policies (Always, Adaptive, or Optional)

### 3. Restricted Signups
- **Disabled public registration** in Auth0
- Only administrators can create user accounts
- Pre-registered users only can access the application

### 4. CORS Configuration
- **Restricted origins** to prevent unauthorized access
- Configured for localhost development
- Easily configurable for production domains

## 📁 Project Structure

```
spring-boot-react-app/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/bimsara/weatherapp/
│   │   │   │   ├── config/
│   │   │   │   │   ├── SecurityConfig.java
│   │   │   │   │   └── WebClientConfig.java
│   │   │   │   ├── controller/
│   │   │   │   │   ├── CityController.java
│   │   │   │   │   ├── WeatherController.java
│   │   │   │   │   └── PublicController.java
│   │   │   │   ├── model/
│   │   │   │   │   ├── City.java
│   │   │   │   │   └── WeatherData.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── CityService.java
│   │   │   │   │   └── WeatherService.java
│   │   │   │   └── WeatherAppApplication.java
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   ├── pom.xml
│   ├── .env
│   └── .gitignore
│
└── frontend/
    ├── src/
    │   ├── components/
    │   │   ├── Loading.jsx
    │   │   ├── Navbar.jsx
    │   │   └── ProtectedRoute.jsx
    │   ├── pages/
    │   │   ├── LandingPage.jsx
    │   │   ├── CitiesPage.jsx
    │   │   └── CityWeatherPage.jsx
    │   ├── services/
    │   │   └── api.js
    │   ├── App.jsx
    │   ├── App.css
    │   ├── main.jsx
    │   └── index.css
    ├── index.html
    ├── package.json
    ├── vite.config.js
    ├── .env
    └── .gitignore
```

## 🔧 API Endpoints

### Public Endpoints
- `GET /api/public/health` - Health check endpoint

### Protected Endpoints (Requires Authentication)
- `GET /api/cities` - Get all cities
- `GET /api/cities/{id}` - Get city by ID
- `GET /api/weather/city/{cityId}` - Get weather by city ID
- `GET /api/weather/city/name/{cityName}` - Get weather by city name

## 🌐 Frontend Routes

- `/` - Landing page (public)
- `/cities` - Cities list page (protected)
- `/city/:cityId` - City weather detail page (protected)

## 🔑 Environment Variables

### Backend (.env)
```properties
AUTH0_DOMAIN=your-tenant.auth0.com
AUTH0_AUDIENCE=https://your-api-identifier
AUTH0_ISSUER_URI=https://your-tenant.auth0.com/
WEATHER_API_KEY=your-weather-api-key (optional)
```

### Frontend (.env)
```properties
VITE_AUTH0_DOMAIN=your-tenant.auth0.com
VITE_AUTH0_CLIENT_ID=your-client-id
VITE_AUTH0_AUDIENCE=https://your-api-identifier
VITE_API_BASE_URL=http://localhost:8080
```

## 📝 Notes

1. **Weather API**: The application uses OpenWeatherMap API as an example. If no API key is provided, it returns mock data.
2. **HTTPS in Production**: For production, ensure you use HTTPS for both frontend and backend.
3. **Auth0 URLs**: Update all callback URLs and origins in Auth0 dashboard when deploying to production.
4. **Token Expiration**: Access tokens expire after a certain period. The app automatically handles token refresh.


## 🚀 Production Deployment

### Backend
1. Update `application.yml` with production values
2. Set up environment variables on your hosting platform
3. Use HTTPS
4. Update Auth0 Allowed Callback URLs

### Frontend
1. Build the production bundle: `npm run build`
2. Update `.env` with production URLs
3. Deploy to a static hosting service (Netlify, Vercel, etc.)
4. Update Auth0 application settings with production URLs
