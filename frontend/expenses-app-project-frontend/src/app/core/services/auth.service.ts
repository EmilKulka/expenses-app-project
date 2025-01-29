import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { AuthResponse } from '../models/auth.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private readonly API_URL = environment.apiUrl;
  private userRoleSubject = new BehaviorSubject<string | null>(localStorage.getItem('userRole'));
  userRole$ = this.userRoleSubject.asObservable();  

  login(username: string, password: string): Observable<AuthResponse> {
    const body = new URLSearchParams();
    body.set('username', username);
    body.set('password', password);

    return this.http.post<AuthResponse>(`${this.API_URL}/login`, body.toString(), {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    }).pipe(
      tap(response => { 
        localStorage.setItem('userRole', response.data.role);
        this.userRoleSubject.next(response.data.role);
      })
    );
  }

  logout(): Observable<any> {
    return this.http.post(`${this.API_URL}/logout`, {}).pipe(
      tap(() => {
        localStorage.removeItem('userRole');
        this.userRoleSubject.next(null);
      })
    );
  }

  isLoggedIn(): boolean {
    return !!this.userRoleSubject.value;
  }

  getUserRole(): string | null {
    return this.userRoleSubject.value;
  }


register(userData: { userName: string; email: string; password: string }): Observable<any> {
  return this.http.post(`${this.API_URL}/api/app-users`, userData);
}

resetPassword(resetData: { email: string; oldPassword: string; newPassword: string }): Observable<any> {
  return this.http.patch(`${this.API_URL}/api/app-users/reset-password`, resetData);
}

}