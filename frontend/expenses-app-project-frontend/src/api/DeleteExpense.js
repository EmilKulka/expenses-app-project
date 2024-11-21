import axios from "axios";

const API_DELETE_EXPENSE_ENDPOINT = 'http://localhost:8080/api/expense/';

export const deleteExpense = async(id) => {
    try {
        const response = await axios.delete(API_DELETE_EXPENSE_ENDPOINT + id, {
            withCredentials: true,
        });
        return { success: true};
    } catch (error) {
        if (error.response && error.response.status === 400) {
            return { success: false, error: error.response.body.errorMessages};
        }
        return { success: false, error: "An unexpected server error occurred." };
    }
}