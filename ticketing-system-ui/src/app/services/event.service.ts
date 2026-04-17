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

  getEvents(filters: any = {}): Observable<any> {
    let params = new HttpParams();
    Object.keys(filters).forEach(key => {
      if (filters[key]) {
        params = params.set(key, filters[key]);
      }
    });
    return this.http.get(this.apiUrl, { params });
  }

  getEventById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  // Admin endpoints
  createEvent(eventData: any): Observable<any> {
    return this.http.post(this.adminApiUrl, eventData);
  }

  publishEvent(id: number): Observable<any> {
    return this.http.patch(`${this.adminApiUrl}/${id}/publish`, {});
  }
}
