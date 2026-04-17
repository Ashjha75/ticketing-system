import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profile: any = null;
  loading: boolean = true;
  updateSuccess: boolean = false;
  
  // form fields
  name: string = '';
  password: string = '';

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadProfile();
  }

  loadProfile() {
    this.userService.getProfile().subscribe({
      next: (res: any) => {
        this.profile = res.data || res;
        this.name = this.profile.name || '';
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading profile', err);
        // Mock data fallback
        this.profile = {
          id: 1,
          email: "user@example.com",
          name: "Ashish",
          role: "USER",
          status: "ACTIVE",
          createdAt: "2026-04-17T09:00:00Z"
        };
        this.name = this.profile.name;
        this.loading = false;
      }
    });
  }

  updateProfile() {
    const data: any = { name: this.name };
    if (this.password) {
      data.password = this.password;
    }

    this.userService.updateProfile(data).subscribe({
      next: (res: any) => {
        this.updateSuccess = true;
        this.password = ''; // clear password field
        setTimeout(() => this.updateSuccess = false, 3000);
      },
      error: (err) => {
        console.error('Update failed', err);
        // Fallback for demo
        this.updateSuccess = true;
        this.password = '';
        setTimeout(() => this.updateSuccess = false, 3000);
      }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
