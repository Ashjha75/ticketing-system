import { Component, OnInit } from '@angular/core';
import { ToastService, ToastMessage } from './services/toast.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'ticketing-system-ui';
  toast: ToastMessage | null = null;

  constructor(private toastService: ToastService) {}

  ngOnInit() {
    this.toastService.toastState$.subscribe(toast => {
      this.toast = toast;
      setTimeout(() => this.toast = null, 3000); // auto clear
    });
  }

  closeToast() {
    this.toast = null;
  }
}
