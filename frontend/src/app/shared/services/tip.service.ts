import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TipService {
  private baseUrl = 'http://localhost:8080';
  private tokenKey = 'auth_token';

  constructor(private http: HttpClient) { }

  generate(): Observable<any> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/tip`;
    return this.http.post(url, null, { headers: {'Authorization': `Bearer ${token}`}}).pipe(
      map((response: any) => {
        if (response) {
          return response;
        }
      })
    );
  }

  getAll(): Observable<any> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/tip`;
    return this.http.get(url, { headers: {'Authorization': `Bearer ${token}`}}).pipe(
      map((response: any) => {
        if (response) {
          return response;
        }
      })
    );
  }
}
