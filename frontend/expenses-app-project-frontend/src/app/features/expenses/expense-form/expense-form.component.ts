import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ExpenseService } from '../../../core/services/expense.service';
import { ExpenseType } from '../../../core/models/expense-type.model';
@Component({
  selector: 'app-expense-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './expense-form.component.html',
  styleUrls: ['./expense-form.component.scss']
})
export class ExpenseFormComponent implements OnInit {
  expenseForm: FormGroup;
  isEditMode = false;
  loading = false;
  error: string | null = null;
  private expense: any = null;
  expenseTypes = Object.values(ExpenseType);
  maxDate = new Date().toISOString().split('T')[0];

  constructor(
    private fb: FormBuilder,
    private expenseService: ExpenseService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.expenseForm = this.fb.group({
      type: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      date: ['', Validators.required],
      important: [false]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    
    if (id) {
        this.isEditMode = true;
        const state = history.state;
        
        if (state && state.expense) {
            this.expense = state.expense;
            this.expenseForm.patchValue({
                type: this.expense.type,
                description: this.expense.description,
                price: this.expense.price,
                date: this.expense.date,
                important: this.expense.important
            });
        } else {
            console.log('No expense data in state');
            this.router.navigate(['/expenses']);
        }
    }
}

  onSubmit(): void {
    if (this.expenseForm.valid) {
      this.loading = true;
      this.error = null;

      const expenseData = this.expenseForm.value;

      const request = this.isEditMode
        ? this.expenseService.updateExpense(this.expense.id, expenseData)
        : this.expenseService.addExpense(expenseData);

      request.subscribe({
        next: () => {
          this.loading = false;
          this.router.navigate(['/expenses']);
        },
        error: (error) => {
          console.error(error)
          this.loading = false;
          this.error = 'Failed to save expense';
        }
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/expenses']);
  }
}