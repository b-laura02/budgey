import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { PreferencesDto } from '../models/dtos/preferences.dto';

@Injectable({
  providedIn: 'root'
})
export class PreferencesService {
  private baseUrl = 'http://localhost:8080';
  private tokenKey = 'auth_token';

  constructor(private http: HttpClient) { }

  getPreferences(): Observable<any> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/preferences`;

    return this.http.get(url, { headers: { 'Authorization': `Bearer ${token}` } }).pipe(
      map((response: any) => {
        if (response) {
          return response;
        }
      })
    );
  }

  update(preferences: PreferencesDto): Observable<any> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/preferences`;

    return this.http.patch(url, preferences, { headers: { 'Authorization': `Bearer ${token}` } }).pipe(
      map((response: any) => {
        if (response) {          
          return response;
        }
      })
    );
  }
  
}
