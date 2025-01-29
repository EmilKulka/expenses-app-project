import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ExpenseService } from '../../../core/services/expense.service';
import { Expense } from '../../../core/models/expense.model';
import { finalize, Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-expense-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './expense-list.component.html',
  styleUrl: './expense-list.component.scss'
})
export class ExpenseListComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  private expenseService = inject(ExpenseService);
  private router = inject(Router);

  expenses: Expense[] = [];
  loading = false;
  error: string | null = null;

  ngOnInit(): void {
    this.loadExpenses();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadExpenses(): void {
    this.loading = true;
    this.expenseService.getExpenses()
      .pipe(
        takeUntil(this.destroy$),
        finalize(() => this.loading = false)
      )
      .subscribe({
        next: (response) => {
          if (response.status === 'success' && response.data) {
            this.expenses = response.data;
          }
        },
        error: (error) => {
          this.error = 'Failed to load expenses';
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