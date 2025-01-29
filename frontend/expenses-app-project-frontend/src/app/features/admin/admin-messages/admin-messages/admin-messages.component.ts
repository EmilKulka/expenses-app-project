import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { ContactService } from '../../../../core/services/contact.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ContactMessage } from '../../../../core/models/contact-message.model';
import { Subject, takeUntil, finalize } from 'rxjs';

@Component({
  selector: 'app-admin-messages',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-messages.component.html',
  styleUrl: './admin-messages.component.scss'
})
export class AdminMessagesComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  private contactService = inject(ContactService);

  messages: ContactMessage[] = [];
  replyContent: { [key: string]: string } = {};
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
    this.contactService.getUnresolvedMessages()
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

  onReply(messageId: string): void {
    const reply = this.replyContent[messageId];
    if (reply) {
      this.contactService.replyToMessage(messageId, reply)
        .pipe(
          takeUntil(this.destroy$),
          finalize(() => {
            this.replyContent[messageId] = '';
          })
        )
        .subscribe({
          next: () => {
            this.loadMessages();
          },
          error: (error) => {
            console.error('Error sending reply:', error);
            this.error = 'Failed to send reply';
          }
        });
    }
  }
}
