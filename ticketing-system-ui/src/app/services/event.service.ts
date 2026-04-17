import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private apiUrl = 'http://localhost:8080/api/events';
  private adminApiUrl = 'http://localhost:8080/api/admin/events';

  constructor(private http: HttpClient) { }

  // ── User Event Endpoints ──────────────────────────────────────────

  /** GET /api/events  (with optional filters: city, keyword, category, startDate, endDate, page, size, sort) */
  getEvents(filters: any = {}): Observable<any> {
    let params = new HttpParams();
    Object.keys(filters).forEach(key => {
      if (filters[key] !== null && filters[key] !== undefined && filters[key] !== '') {
        params = params.set(key, filters[key]);
      }
    });
    return this.http.get(this.apiUrl, { params });
  }

  /** GET /api/events/{id} */
  getEventById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  // ── Admin Event Endpoints ─────────────────────────────────────────

  /** POST /api/admin/events */
  createEvent(eventData: any): Observable<any> {
    return this.http.post(this.adminApiUrl, eventData);
  }

  /** PUT /api/admin/events/{id} */
  updateEvent(id: number, eventData: any): Observable<any> {
    return this.http.put(`${this.adminApiUrl}/${id}`, eventData);
  }

  /** PATCH /api/admin/events/{id}/publish */
  publishEvent(id: number): Observable<any> {
    return this.http.patch(`${this.adminApiUrl}/${id}/publish`, {});
  }

  /** PATCH /api/admin/events/{id}/cancel */
  cancelEvent(id: number): Observable<any> {
    return this.http.patch(`${this.adminApiUrl}/${id}/cancel`, {});
  }
}
