import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { ContactService } from '../../../../core/services/contact.service';
import { CommonModule } from '@angular/common';
import { ContactMessage } from '../../../../core/models/contact-message.model';
import { finalize, Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-user-messages',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-messages.component.html',
  styleUrl: './user-messages.component.scss'
})
export class UserMessagesComponent implements OnInit, OnDestroy {
  private contactService = inject(ContactService)
  private destroy$ = new Subject<void>();
  
  messages: ContactMessage[] = [];
  loading = false;
  error: string | null = null;

  ngOnInit(): void {
    this.loadMessages();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadMessages(): void {
      this.loading = true;
      this.contactService.getUserMessages()
        .pipe(
          takeUntil(this.destroy$),
          finalize(() => this.loading = false)
        )
        .subscribe({
          next: (response) => {
            if (response.status === 'success' && response.data) {
              this.messages = response.data;
            }
          },
          error: (error) => {
            this.error = 'Failed to load messages';
            console.error('Error loading messages:', error);
          }
        });
  }
}