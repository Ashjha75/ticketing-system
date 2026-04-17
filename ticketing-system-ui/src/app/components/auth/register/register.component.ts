import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { ToastService } from 'src/app/services/toast.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private toastService: ToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', Validators.required]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(g: FormGroup) {
    return g.get('password')?.value === g.get('confirmPassword')?.value
      ? null : { mismatch: true };
  }

  onSubmit() {
    if (this.registerForm.invalid) {
      this.toastService.showError('Please check your form inputs.');
      return;
    }

    const { email, password } = this.registerForm.value;
    
    this.authService.register({ email, password }).subscribe({
      next: (res: any) => {
        this.toastService.showSuccess(res.message || 'Registration successful!');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        const errorMsg = err.error?.message || 'Registration failed.';
        this.toastService.showError(errorMsg);
      }
    });
  }
}
