import { Expense } from "./expense.model";

export interface ExpenseResponse {
    status: string;
    message: string;
    data: Expense[] | [];
  }
  