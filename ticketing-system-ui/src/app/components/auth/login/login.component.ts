import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private toastService: ToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Redirect if already logged in
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/events']);
    }

    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) return;

    this.loading = true;
    const { email, password } = this.loginForm.value;

    this.authService.login({ email, password }).subscribe({
      next: (res: any) => {
        this.loading = false;
        this.toastService.showSuccess(res.message || 'Login successful!');
        // Redirect admin to dashboard, regular user to events
        const user = this.authService.getCurrentUser();
        this.router.navigate([user?.role === 'ADMIN' ? '/admin/dashboard' : '/events']);
      },
      error: (err) => {
        this.loading = false;
        const msg = err.error?.message || 'Login failed. Please check your credentials.';
        this.toastService.showError(msg);
      }
    });
  }
}
