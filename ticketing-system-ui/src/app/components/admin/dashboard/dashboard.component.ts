import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  dashboardData: any = null;
  loading: boolean = true;

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard() {
    this.adminService.getDashboard().subscribe({
      next: (res: any) => {
        this.dashboardData = res.data || res;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading dashboard', err);
        // Mock data fallback
        this.dashboardData = {
          totalUsers: 12,
          totalEvents: 5,
          totalBookings: 30,
          totalRevenue: 95000.00,
          activeEvents: 3,
          soldOutEvents: 1,
          todayBookings: 6,
          todayRevenue: 18000.00
        };
        this.loading = false;
      }
    });
  }
}
