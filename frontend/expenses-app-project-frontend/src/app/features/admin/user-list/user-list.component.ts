import { finalize, Subject, takeUntil } from "rxjs";
import { UserService } from "../../../core/services/user.service";
import { Component } from "@angular/core";
import { User2 } from "../../../core/models/user2";
import { CommonModule } from "@angular/common";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
  imports: [CommonModule],
  standalone: true
})
export class UserListComponent {
  users: User2[] = [];
  loading = false;
  error: string | null = null;
  private destroy$ = new Subject<void>();

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadUsers(): void {
    this.loading = true;
    this.error = null;

    this.userService.getUsers()
      .pipe(
        takeUntil(this.destroy$),
        finalize(() => this.loading = false)
      )
      .subscribe({
        next: (response) => {
          this.users = response.data;
        },
        error: (error) => {
          this.error = 'Failed to load users';
          console.error('Error loading users:', error);
        }
      });
  }

  onDelete(id: number): void {
    this.loading = true;
    this.error = null;
    if(confirm("Are you sure you want to delete this user?")){
      this.userService.deleteUser(id).subscribe({
        next: () => {
          this.loading = false;
          this.loadUsers();
        },
        error: (error) => {
          this.loading = false;
          console.error(error);
        }
      })
    }
  }
}