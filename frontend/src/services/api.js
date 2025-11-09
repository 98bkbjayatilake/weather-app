import axios from 'axios'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

const apiClient = axios.create({
  baseUrl: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

export const setAuthToken = (token) => {
  if (token) {
    apiClient.defaults.headers.common['Authorization'] = `Bearer ${token}`
  } else {
    delete apiClient.defaults.headers.common['Authorization']
  }
}

export const getAllCities = async (token) => {
  setAuthToken(token)
  const response = await apiClient.get(`${API_BASE_URL}/api/cities`)
  return response.data
}

export const getCityById = async (cityId, token) => {
  setAuthToken(token)
  const response = await apiClient.get(`${API_BASE_URL}/api/cities/${cityId}`)
  return response.data
}

export const getWeatherByCityId = async (cityId, token) => {
  setAuthToken(token)
  const response = await apiClient.get(`${API_BASE_URL}/api/weather/city/${cityId}`)
  return response.data
}

export default apiClient
