import { Routes } from "@angular/router";
import { RoleGuard } from "../../core";
import { ContactFormComponent } from "./contact-form/contact-form.component";
import { UserMessagesComponent } from "./user-messages/user-messages/user-messages.component";

export const CONTACT_ROUTES: Routes = [
  {
    path: 'contact',
    children: [
      {
        path: '',
        component: ContactFormComponent,
        canActivate: [RoleGuard],
        data: { role: 'USER' }
      },
      {
        path: 'my-messages',
        component: UserMessagesComponent,
        canActivate: [RoleGuard],
        data: { role: 'USER' }
      }
    ]
  }
];