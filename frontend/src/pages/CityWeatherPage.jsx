import React, { useState, useEffect } from 'react'
import { useAuth0 } from '@auth0/auth0-react'
import { useParams, useNavigate, Navigate } from 'react-router-dom'
import Navbar from '../components/Navbar'
import Loading from '../components/Loading'
import { getWeatherByCityId, getCityById } from '../services/api'

const CityWeatherPage = () => {
  const { isAuthenticated, getAccessTokenSilently, isLoading } = useAuth0()
  const { cityId } = useParams()
  const navigate = useNavigate()
  const [weather, setWeather] = useState(null)
  const [city, setCity] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchWeatherData = async () => {
      if (!isAuthenticated) {
        return
      }

      try {
        setLoading(true)
        const token = await getAccessTokenSilently()
        
        // Fetch both city and weather data
        const [cityData, weatherData] = await Promise.all([
          getCityById(cityId, token),
          getWeatherByCityId(cityId, token)
        ])
        
        setCity(cityData)
        setWeather(weatherData)
        setError(null)
      } catch (err) {
        console.error('Error fetching weather data:', err)
        setError('Failed to load weather data. Please try again.')
      } finally {
        setLoading(false)
      }
    }

    fetchWeatherData()
  }, [cityId, isAuthenticated, getAccessTokenSilently])

  if (isLoading) {
    return <Loading />
  }

  if (!isAuthenticated) {
    return <Navigate to="/" replace />
  }

  const handleBackClick = () => {
    navigate('/cities')
  }

  return (
    <div className="app">
      <Navbar />
      
      {error && <div className="error-message">{error}</div>}

      {loading ? (
        <Loading />
      ) : weather ? (
        <div className="weather-detail-container">
          <h2>
            {weather.cityName}, {weather.country}
          </h2>
          
          <div style={{ fontSize: '3rem', margin: '1rem 0' }}>
            {weather.icon && (
              <img 
                src={`https://openweathermap.org/img/wn/${weather.icon}@2x.png`}
                alt={weather.description}
                style={{ width: '100px', height: '100px' }}
              />
            )}
          </div>
          
          <p style={{ fontSize: '1.2rem', textTransform: 'capitalize', marginBottom: '1rem' }}>
            {weather.description}
          </p>

          <div className="weather-info">
            <div className="weather-info-item">
              <label>Temperature</label>
              <strong>{weather.temperature?.toFixed(1)}°C</strong>
            </div>
            
            <div className="weather-info-item">
              <label>Feels Like</label>
              <strong>{weather.feelsLike?.toFixed(1)}°C</strong>
            </div>
            
            <div className="weather-info-item">
              <label>Humidity</label>
              <strong>{weather.humidity}%</strong>
            </div>
            
            <div className="weather-info-item">
              <label>Wind Speed</label>
              <strong>{weather.windSpeed?.toFixed(1)} m/s</strong>
            </div>
          </div>

          <div className="back-button">
            <button className="btn btn-primary" onClick={handleBackClick}>
              ← Back to Cities
            </button>
          </div>
        </div>
      ) : (
        <div>No weather data available</div>
      )}
    </div>
  )
}

export default CityWeatherPage
