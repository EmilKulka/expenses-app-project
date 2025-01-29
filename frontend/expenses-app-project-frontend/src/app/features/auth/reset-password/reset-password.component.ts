import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { ErrorResponse } from '../../../core/models/error.model';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.scss'
})
export class ResetPasswordComponent {
  resetForm: FormGroup;
  error: string = '';
  successMessage: string = '';
  loading: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.resetForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      oldPassword: ['', [Validators.required]],
      newPassword: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(): void {
    if (this.resetForm.valid) {
      this.loading = true;
      this.error = '';
      this.successMessage = '';
      
      this.authService.resetPassword(this.resetForm.value).subscribe({
        next: (response) => {
          this.successMessage = response.message;
          this.loading = false;
        },
        error: (error: { error: ErrorResponse }) => {
          this.loading = false;
          this.error = error.error.errorMessages[0];
          console.error('Reset password error:', error);
        }
      });
    }
  }

  closeAlert(): void {
    this.successMessage = '';
    this.router.navigate(['/login']);
  }
}