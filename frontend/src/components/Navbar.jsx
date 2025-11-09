import React from 'react'
import { useAuth0 } from '@auth0/auth0-react'

const Navbar = () => {
  const { user, isAuthenticated, logout } = useAuth0()

  if (!isAuthenticated) {
    return null
  }

  return (
    <nav className="navbar">
      <div className="navbar-brand">
        🌤️ Weather App
      </div>
      <div className="navbar-user">
        <div className="user-info">
          <div>{user.name}</div>
          <div className="user-email">{user.email}</div>
        </div>
        <button 
          className="btn btn-secondary"
          onClick={() => logout({ logoutParams: { returnTo: window.location.origin } })}
        >
          Logout
        </button>
      </div>
    </nav>
  )
}

export default Navbar
