import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Expense } from '../models/expense';
import { ExpenseResponse } from '../models/expense-response.model';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private readonly API_URL = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getExpenses(): Observable<ExpenseResponse> {
    return this.http.get<ExpenseResponse>(`${this.API_URL}/api/app-user/expenses`);
  }

  addExpense(expense: Omit<Expense, 'id'>): Observable<ExpenseResponse> {
    return this.http.post<ExpenseResponse>(`${this.API_URL}/api/expense`, expense);
  }

  updateExpense(id: number, expense: Omit<Expense, 'id'>): Observable<ExpenseResponse> {
    return this.http.put<ExpenseResponse>(`${this.API_URL}/api/expense/${id}`, expense);
  }

  deleteExpense(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/api/expense/${id}`);
  }

  getExpenseById(id: number): Observable<ExpenseResponse> {
    return this.http.get<ExpenseResponse>(`${this.API_URL}/api/expense/${id}`);
  }
}