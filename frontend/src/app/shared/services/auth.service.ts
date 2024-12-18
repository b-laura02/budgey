import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:3333';
  private tokenKey = 'auth_token';

  constructor(private http: HttpClient) { }

  register(email: string, fullName: string, password: string): Observable<any> {
    const url = `${this.baseUrl}/auth/login`;
    return this.http.post(url, { email, fullName, password }).pipe(
      map((response: any) => {
        if (response && response.token) {
          localStorage.setItem(this.tokenKey, response.token);
        }
        return response;
      })
    );
  }

  login(email: string, password: string): Observable<any> {
    const url = `${this.baseUrl}/auth/login`;
    return this.http.post(url, { email, password }).pipe(
      map((response: any) => {
        if (response && response.token) {
          localStorage.setItem(this.tokenKey, response.token);

        }
        return response;
      })
    );
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  private getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }
}
