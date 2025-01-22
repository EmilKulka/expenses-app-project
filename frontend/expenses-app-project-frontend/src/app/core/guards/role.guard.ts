import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(route: any): boolean {
    const requiredRole = route.data['role'];
    const userRole = this.authService.getUserRole();

    if (!userRole) {
      this.router.navigate(['/login']);
      return false;
    }

    if (userRole !== requiredRole) {
      this.router.navigate([userRole === 'ADMIN' ? '/admin' : '/expenses']);
      return false;
    }

    return true;
  }
}