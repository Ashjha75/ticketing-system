import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { EventListComponent } from './components/events/event-list/event-list.component';
import { EventDetailComponent } from './components/events/event-detail/event-detail.component';
import { BookingListComponent } from './components/bookings/booking-list/booking-list.component';
import { ProfileComponent } from './components/profile/profile.component';
import { DashboardComponent } from './components/admin/dashboard/dashboard.component';
import { EventManagementComponent } from './components/admin/event-management/event-management.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    EventListComponent,
    EventDetailComponent,
    BookingListComponent,
    ProfileComponent,
    DashboardComponent,
    EventManagementComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
