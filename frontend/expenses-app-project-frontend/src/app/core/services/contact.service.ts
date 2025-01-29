import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ContactDto } from '../models/contact-dto.model';
import { HttpClient } from '@angular/common/http';
import { ContactResponse } from '../models/contact-response.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ContactService {
  private http = inject(HttpClient);
  private readonly API_URL = environment.apiUrl;

  createMessage(message: ContactDto): Observable<any> {
    return this.http.post(`${this.API_URL}/api/contact`, message);
  }

  getUnresolvedMessages(): Observable<ContactResponse> {
    return this.http.get<ContactResponse>(`${this.API_URL}/api/contact/unresolved`);
  }

  getUserMessages(): Observable<ContactResponse> {
    return this.http.get<ContactResponse>(`${this.API_URL}/api/contact/user-messages`);
  }

  replyToMessage(messageId: string, replyContent: string): Observable<any> {
    return this.http.post(`${this.API_URL}/api/contact/${messageId}/reply`, replyContent);
  }
}
