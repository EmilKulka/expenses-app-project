import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { ExpenseService } from '../../../core/services/expense.service';
import { Expense } from '../../../core/models/expense';

@Component({
  selector: 'app-expense-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './expense-details.component.html',
  styleUrls: ['./expense-details.component.scss']
})
export class ExpenseDetailsComponent implements OnInit {
  expense: Expense | null = null;
  loading = false;
  deleting = false;
  error: string | null = null;

  constructor(
    private expenseService: ExpenseService,
    private router: Router,
  ) {
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras.state) {
      this.expense = navigation.extras.state['expense'];
    }
  }

  ngOnInit(): void {
    if (!this.expense) {
      this.router.navigate(['/expenses']);
    }
  }

  onEdit(): void {
    if (this.expense) {
      this.router.navigate(['/expenses', this.expense.id, 'edit'], {
        state: { expense: this.expense }
      });
    }
  }

  onDelete(): void {
    if (this.expense && confirm('Are you sure you want to delete this expense?')) {
      this.deleting = true;
      this.expenseService.deleteExpense(this.expense.id).subscribe({
        next: () => {
          this.deleting = false;
          this.router.navigate(['/expenses']);
        },
        error: (error) => {
          console.error(error)
          this.deleting = false;
          this.error = 'Failed to delete expense';
        }
      });
    }
  }

  onBack(): void {
    this.router.navigate(['/expenses']);
  }
}