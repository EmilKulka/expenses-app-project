import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private http = inject(HttpClient);
  private readonly API_URL = environment.apiUrl;

  getUsers(): Observable<any> {
    return this.http.get(`${this.API_URL}/api/app-users`);
  }

  deleteUser(userId: number): Observable<any> {
    return this.http.delete(`${this.API_URL}/api/app-users/${userId}`);
  }
}