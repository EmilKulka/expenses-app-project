import axios from 'axios';

const API_LOGOUT_URL = 'http://localhost:8080/logout';

export const logout = async () => {
  try {
    await axios.post(API_LOGOUT_URL);
    localStorage.removeItem("role");
  } catch (error) {
    console.error("Error logging out:", error);
  }
};
