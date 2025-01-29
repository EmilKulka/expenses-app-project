import { Routes } from '@angular/router';
import { UserListComponent } from './user-list/user-list.component';
import { RoleGuard } from '../../core/guards/role.guard';
import { AdminMessagesComponent } from './admin-messages/admin-messages/admin-messages.component';

export const ADMIN_ROUTES: Routes = [
  {
    path: 'admin',
    canActivate: [RoleGuard],
    data: { role: 'ADMIN' },
    children: [
      {
        path: '',
        component: UserListComponent
      },
      {
        path: 'messages',
        component: AdminMessagesComponent
      }
    ]
  }
];