import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import { useAuth0 } from '@auth0/auth0-react'
import LandingPage from './pages/LandingPage'
import CitiesPage from './pages/CitiesPage'
import CityWeatherPage from './pages/CityWeatherPage'
import Loading from './components/Loading'
import './App.css'

function App() {
  const { isLoading } = useAuth0()

  if (isLoading) {
    return <Loading />
  }

  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/cities" element={<CitiesPage />} />
        <Route path="/city/:cityId" element={<CityWeatherPage />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </Router>
  )
}

export default App
