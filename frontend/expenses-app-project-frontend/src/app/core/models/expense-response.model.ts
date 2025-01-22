import { Expense } from "./expense";

export interface ExpenseResponse {
    status: string;
    message: string;
    data: Expense[] | null;
  }
  