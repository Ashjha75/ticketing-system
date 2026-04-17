import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
    // In real app, we should check a role from JWT or user profile.
    // For now, we assume if current user has role ADMIN it allows.
    let isAdmin = false;
    this.authService.currentUser$.subscribe(user => {
      if (user && user.role === 'ADMIN') {
        isAdmin = true;
      }
    }).unsubscribe();

    if (isAdmin) {
      return true;
    }

    this.router.navigate(['/events']);
    return false;
  }
}
