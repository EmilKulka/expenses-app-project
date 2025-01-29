import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Expense } from '../models/expense.model';
import { ExpenseResponse } from '../models/expense-response.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private http = inject(HttpClient);
  private readonly API_URL = environment.apiUrl;

  getExpenses(): Observable<ExpenseResponse> {
    return this.http.get<ExpenseResponse>(`${this.API_URL}/api/app-users/expenses`);
  }

  addExpense(expense: Omit<Expense, 'id'>): Observable<ExpenseResponse> {
    return this.http.post<ExpenseResponse>(`${this.API_URL}/api/expenses`, expense);
  }

  updateExpense(id: number, expense: Omit<Expense, 'id'>): Observable<ExpenseResponse> {
    return this.http.put<ExpenseResponse>(`${this.API_URL}/api/expenses/${id}`, expense);
  }

  deleteExpense(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/api/expenses/${id}`);
  }

  getExpenseById(id: number): Observable<ExpenseResponse> {
    return this.http.get<ExpenseResponse>(`${this.API_URL}/api/expenses/${id}`);
  }
}