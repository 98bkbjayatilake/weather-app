import React from 'react'
import { useAuth0 } from '@auth0/auth0-react'
import { useNavigate } from 'react-router-dom'

const LandingPage = () => {
  const { isAuthenticated, loginWithRedirect } = useAuth0()
  const navigate = useNavigate()

  React.useEffect(() => {
    if (isAuthenticated) {
      navigate('/cities')
    }
  }, [isAuthenticated, navigate])

  return (
    <div className="app">
      <div className="landing-page">
        <h1>🌤️ Welcome to Weather App</h1>
        <p>Get real-time weather information for cities around the world</p>
        <p>Secured with Auth0 Authentication</p>
        <button 
          className="btn btn-primary"
          onClick={() => loginWithRedirect()}
        >
          Login to Continue
        </button>
      </div>
    </div>
  )
}

export default LandingPage
