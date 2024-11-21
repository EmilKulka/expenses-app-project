import axios from "axios";

const API_APP_USER_EXPENSES_ENDPOINT = 'http://localhost:8080/api/app-user/expenses';

export const getAppUserExpenses = async () => {
    try {
        const response = await axios.get(API_APP_USER_EXPENSES_ENDPOINT, {
            withCredentials: true,
        });
        return { success: true, data: response.data};
    } catch (error) {
        console.error("Error fetching user expenses:", error);
        if (error.response && error.response.status === 403) {
            return { success: false, error: "Unauthorized access (403)." };
        }
        return { success: false, error: "An unexpected server error occurred." };
    }
};
