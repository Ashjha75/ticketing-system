import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { EventListComponent } from './components/events/event-list/event-list.component';
import { EventDetailComponent } from './components/events/event-detail/event-detail.component';
import { BookingListComponent } from './components/bookings/booking-list/booking-list.component';
import { ProfileComponent } from './components/profile/profile.component';
import { DashboardComponent } from './components/admin/dashboard/dashboard.component';
import { EventManagementComponent } from './components/admin/event-management/event-management.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'events', component: EventListComponent },
  { path: 'events/:id', component: EventDetailComponent },
  { path: 'bookings', component: BookingListComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'admin/dashboard', component: DashboardComponent },
  { path: 'admin/events', component: EventManagementComponent },
  { path: '', redirectTo: '/events', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
