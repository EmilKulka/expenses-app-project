import { Routes } from '@angular/router';
import { ExpenseListComponent } from './expense-list/expense-list.component';
import { ExpenseFormComponent } from './expense-form/expense-form.component';
import { ExpenseDetailsComponent } from './expense-details/expense-details.component';
import { AuthGuard } from '../../core/guards/auth.guard';
import { RoleGuard } from '../../core/guards/role.guard';

export const EXPENSES_ROUTES: Routes = [
  {
    path: 'expenses',
    children: [
      {
        path: '',
        component: ExpenseListComponent,
        canActivate: [RoleGuard],
        data: { role: 'USER' }
      },
      {
        path: 'new',
        component: ExpenseFormComponent,
        canActivate: [RoleGuard],
        data: { role: 'USER' }
      },
      {
        path: ':id',
        component: ExpenseDetailsComponent,
        canActivate: [RoleGuard],
        data: { role: 'USER' }
      },
      {
        path: ':id/edit',
        component: ExpenseFormComponent,
        canActivate: [RoleGuard],
        data: { role: 'USER' }
      }
    ]
  }
];
