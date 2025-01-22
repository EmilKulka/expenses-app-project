import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { AuthResponse } from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = 'http://localhost:8080';
  private userRoleSubject = new BehaviorSubject<string | null>(localStorage.getItem('userRole'));
  userRole$ = this.userRoleSubject.asObservable();

  constructor(private http: HttpClient) {}
  

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
  return this.http.post(`${this.API_URL}/api/app-user/register`, userData);
}

resetPassword(resetData: { email: string; oldPassword: string; newPassword: string }): Observable<any> {
  return this.http.put(`${this.API_URL}/api/app-user/reset-password`, resetData);
}

}