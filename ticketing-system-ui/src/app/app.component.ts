import { Component, OnInit } from "@angular/core";
import { ToastService, ToastMessage } from "./services/toast.service";
import { AuthService } from "./services/auth.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent implements OnInit {
  title = "ticketing-system-ui";
  toast: ToastMessage | null = null;
  isLoggedIn = false;
  isAdmin = false;

  constructor(
    private toastService: ToastService,
    public authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.authService.currentUser$.subscribe((user) => {
      this.isLoggedIn = !!user;
      this.isAdmin = user?.role === "ADMIN";
    });

    this.toastService.toastState$.subscribe((toast) => {
      this.toast = toast;
      setTimeout(() => (this.toast = null), 3000); // auto clear
    });
  }

  closeToast() {
    this.toast = null;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(["/login"]);
  }
}
