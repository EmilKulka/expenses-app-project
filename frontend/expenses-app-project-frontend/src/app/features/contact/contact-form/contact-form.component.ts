import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ContactService } from '../../../core/services/contact.service';
import { Router } from '@angular/router';
import { ContactDto } from '../../../core/models/contact-dto.model';

@Component({
  selector: 'app-contact-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './contact-form.component.html',
  styleUrl: './contact-form.component.scss'
})
export class ContactFormComponent {
  private contactService = inject(ContactService);
  private router = inject(Router);
  
  loading = false;
  
  contactForm = new FormGroup({
    type: new FormControl('', Validators.required),
    subject: new FormControl('', Validators.required),
    content: new FormControl('', Validators.required)
  });

  onSubmit(): void {
    if (this.contactForm.valid) {
      this.loading = true;
      this.contactService.createMessage(this.contactForm.value as ContactDto).subscribe({
        next: () => {
          this.loading = false;
          this.router.navigate(['/contact/my-messages']);
        },
        error: (error) => {
          console.error('Failed to send message:', error);
          this.loading = false;
        }
      });
    }
  }
}
