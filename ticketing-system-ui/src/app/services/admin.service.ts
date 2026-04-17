import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = 'http://localhost:8080/api/admin';

  constructor(private http: HttpClient) { }

  getDashboard(): Observable<any> {
    return this.http.get(`${this.apiUrl}/dashboard`);
  }

  getEventStats(): Observable<any> {
    return this.http.get(`${this.apiUrl}/events/stats`);
  }

  getEventStatsById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/events/${id}/stats`);
  }

  getBookingStats(): Observable<any> {
    return this.http.get(`${this.apiUrl}/bookings/stats`);
  }

  getUser(id: number): Observable<any> {
    return this.http.get(`/api/users/${id}`); // Assuming it's the correct path
  }

  updateUserStatus(id: number, status: string): Observable<any> {
    return this.http.patch(`/api/users/${id}/status?status=${status}`, {});
  }
}
