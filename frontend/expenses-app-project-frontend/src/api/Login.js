import axios from 'axios';

const API_LOGIN_URL = 'http://localhost:8080/login';

export const login = async (params) => {
    try {
      const response = await axios.post(
        `${API_LOGIN_URL}`,
         params,
          { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } },
          axios.defaults.withCredentials = true
        );

        const userRole = response.data.role;
        localStorage.setItem("role", userRole);

      return {success: true, data: response.data}
    } catch (error) {
        if (error.response) {
            // If the error has a response from the server, handle the error
            if (error.response.status === 401) {
              return { success: false, error: error.response.data.error }; // Unauthorized
            } else {
              return { success: false, error: 'An unexpected server error occurred.' };
            }
          } else if (error.request) {
            // If no response from the server
            return { success: false, error: 'No response from server. Check your network connection.' };
          } else {
            // For other types of errors
            return { success: false, error: 'An unexpected error occurred.' };
          }
    }
  };