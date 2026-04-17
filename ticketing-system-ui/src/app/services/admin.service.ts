import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private baseUrl = 'http://localhost:8080/api/admin';
  private usersUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

  // ── Analytics ────────────────────────────────────────────────────

  /** GET /api/admin/dashboard */
  getDashboard(): Observable<any> {
    return this.http.get(`${this.baseUrl}/dashboard`);
  }

  /** GET /api/admin/events/stats */
  getEventStats(): Observable<any> {
    return this.http.get(`${this.baseUrl}/events/stats`);
  }

  /** GET /api/admin/events/{id}/stats */
  getEventStatsById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/events/${id}/stats`);
  }

  /** GET /api/admin/bookings/stats */
  getBookingStats(): Observable<any> {
    return this.http.get(`${this.baseUrl}/bookings/stats`);
  }

  // ── User Management ──────────────────────────────────────────────

  /** GET /api/users/{id}  (Admin only) */
  getUser(id: number): Observable<any> {
    return this.http.get(`${this.usersUrl}/${id}`);
  }

  /** PATCH /api/users/{id}/status?status=ACTIVE|INACTIVE|BLOCKED  (Admin only) */
  updateUserStatus(id: number, status: 'ACTIVE' | 'INACTIVE' | 'BLOCKED'): Observable<any> {
    const params = new HttpParams().set('status', status);
    return this.http.patch(`${this.usersUrl}/${id}/status`, {}, { params });
  }
}
