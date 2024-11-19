import axios from 'axios';

const API_REGISTER_URL = 'http://localhost:8080/api/app-user/register';


export const registerNewAppuser = async (body) => {
    try {
      const response = await axios.post(
        API_REGISTER_URL,
        body,
        { headers: { 'Content-Type': 'application/json' },
          withCredentials: true
       }
      );

      return { success: true, data: response.data };
    } catch (error) {
        if (error.response) {
            if (error.response.status === 400) {
              return { success: false, error: error.response.data.errorMessages };
            } else {
              return { success: false, error: 'An unexpected server error occurred.' };
            }
        } else if (error.request) {
            return { success: false, error: 'No response from server. Check your network connection.' };
        } else {
            return { success: false, error: 'An unexpected error occurred.' };
        }
    }
};
