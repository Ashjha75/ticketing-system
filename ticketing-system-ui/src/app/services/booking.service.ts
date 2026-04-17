import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = 'http://localhost:8080/api/bookings';

  constructor(private http: HttpClient) { }

  createBooking(bookingData: { eventId: number, quantity: number }): Observable<any> {
    return this.http.post(this.apiUrl, bookingData);
  }

  getBookings(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  getBookingById(id: number | string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  cancelBooking(id: number | string): Observable<any> {
    return this.http.post(`${this.apiUrl}/${id}/cancel`, {});
  }
}
