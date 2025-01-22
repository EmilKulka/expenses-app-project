import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const isLoggedIn = this.authService.isLoggedIn();
    const requiresAuth = route.data['requiresAuth'] ?? true;

    if (isLoggedIn) {
      const role = this.authService.getUserRole();

      if (!role) {
        console.error('AuthGuard: userRole is not set. Redirecting to login.');
        this.router.navigate(['/login']);
        return false;
      }

      if (!requiresAuth) {
        this.router.navigate([role === 'ADMIN' ? '/admin' : '/expenses']);
        return false;
      }
    } else if (requiresAuth) {
      this.router.navigate(['/login']);
      return false;
    }

    return true;
  }
}
