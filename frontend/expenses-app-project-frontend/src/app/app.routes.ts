import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: '',
    loadChildren: () => import('./features/auth/auth.routes')
      .then(r => r.AUTH_ROUTES)
  },
  {
    path: '',
    loadChildren: () => import('./features/expenses/expense.routes')
      .then(r => r.EXPENSES_ROUTES)
  },
  {
    path: '',
    loadChildren: () => import('./features/admin/admin.routes')
      .then(r => r.ADMIN_ROUTES)
  },
  {
    path: '',
    loadChildren: () => import('./features/contact/contact.routes')
      .then(r => r.CONTACT_ROUTES)
  },
  {
    path: '**', 
    redirectTo: 'login'
  }
];