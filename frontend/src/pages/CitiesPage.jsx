import React, { useState, useEffect } from 'react'
import { useAuth0 } from '@auth0/auth0-react'
import { useNavigate, Navigate } from 'react-router-dom'
import Navbar from '../components/Navbar'
import Loading from '../components/Loading'
import { getAllCities } from '../services/api'

const CitiesPage = () => {
  const { isAuthenticated, getAccessTokenSilently, isLoading } = useAuth0()
  const navigate = useNavigate()
  const [cities, setCities] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchCities = async () => {
      if (!isAuthenticated) {
        return
      }

      try {
        setLoading(true)
        const token = await getAccessTokenSilently()
        const data = await getAllCities(token)
        setCities(data)
        setError(null)
      } catch (err) {
        console.error('Error fetching cities:', err)
        setError('Failed to load cities. Please try again.')
      } finally {
        setLoading(false)
      }
    }

    fetchCities()
  }, [isAuthenticated, getAccessTokenSilently])

  if (isLoading) {
    return <Loading />
  }

  if (!isAuthenticated) {
    return <Navigate to="/" replace />
  }

  const handleCityClick = (cityId) => {
    navigate(`/city/${cityId}`)
  }

  return (
    <div className="app">
      <Navbar />
      <div className="page-header">
        <h1>Cities</h1>
        <p>Select a city to view its weather information</p>
      </div>

      {error && <div className="error-message">{error}</div>}

      {loading ? (
        <Loading />
      ) : (
        <div className="cities-grid">
          {cities.map((city) => (
            <div 
              key={city.id} 
              className="city-card"
              onClick={() => handleCityClick(city.id)}
            >
              <h3>{city.name}</h3>
              {city.country && <p>📍 {city.country}</p>}
              {city.temperature !== null && city.temperature !== undefined && (
                <p style={{ fontSize: '1.5em', fontWeight: 'bold', color: '#646cff' }}>
                  {city.temperature.toFixed(1)}°C
                </p>
              )}
              {city.weatherDescription && (
                <p style={{ textTransform: 'capitalize', opacity: 0.8 }}>
                  {city.weatherDescription}
                </p>
              )}
              <p style={{ fontSize: '0.9em', opacity: 0.6 }}>
                City Code: {city.cityCode}
              </p>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}

export default CitiesPage
