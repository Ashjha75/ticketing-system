import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

  /** GET /api/users/me */
  getProfile(): Observable<any> {
    return this.http.get(`${this.apiUrl}/me`);
  }

  /** PUT /api/users/me  — accepts partial { name?, password? } */
  updateProfile(data: { name?: string; password?: string }): Observable<any> {
    return this.http.put(`${this.apiUrl}/me`, data);
  }
}
