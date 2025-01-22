import { Routes } from '@angular/router';
import { UserListComponent } from './user-list/user-list.component';
import { RoleGuard } from '../../core/guards/role.guard';

export const ADMIN_ROUTES: Routes = [
    {
      path: 'admin',
      component: UserListComponent,
        canActivate: [RoleGuard],
          data: { role: 'ADMIN' }
    }
  ];