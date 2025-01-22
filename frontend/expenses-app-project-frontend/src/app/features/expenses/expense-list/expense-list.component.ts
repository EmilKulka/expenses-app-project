import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ExpenseService } from '../../../core/services/expense.service';
import { Expense } from '../../../core/models/expense';

@Component({
  selector: 'app-expense-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './expense-list.component.html',
  styleUrl: './expense-list.component.scss'
})
export class ExpenseListComponent implements OnInit {
  private expenseService = inject(ExpenseService);
  private router = inject(Router);

  expenses: Expense[] = [];
  loading = false;
  error: string | null = null;

  ngOnInit(): void {
    this.loadExpenses();
  }

  loadExpenses(): void {
    this.loading = true;
    this.expenseService.getExpenses().subscribe({
      next: (response) => {
        if (response.status === 'success' && response.data) {
          this.expenses = response.data;
        }
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Failed to load expenses';
        this.loading = false;
        console.error(error)
      }
    });
  }

  addNewExpense(): void {
    this.router.navigate(['/expenses/new']);
  }

  viewExpense(expense: Expense): void { 
    this.router.navigate(['/expenses', expense.id], {
      state: { expense: expense }
    });
  }
}