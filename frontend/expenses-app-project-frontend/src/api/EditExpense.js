// HANDLE ERROR FROM BACKEND PROPERLY

import axios from "axios";

const API_ADD_EXPENSE_URL = "http://localhost:8080/api/expense/";

export const editExpense = async (id, updatedExpense) => {
    try {
        const response = await axios.put(
            API_ADD_EXPENSE_URL + id,
            updatedExpense,
            {
                headers: { "Content-Type": "application/json" },
                withCredentials: true,
            }
        );
        return { success: true, data: response.data };
    } catch (error) {
        if (error.response) {
            if (error.response.status === 400) {
                const { errorMessages } = error.response.data;
                return { success: false, errors: errorMessages };
            } else {
                return {
                    success: false,
                    errors: ["An unexpected server error occurred."],
                };
            }
        } else if (error.request) {
            return {
                success: false,
                errors: ["No response from server. Check your network connection."],
            };
        } else {
            return { success: false, errors: ["An unexpected error occurred."] };
        }
    }
};
