import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private baseUrl = 'http://localhost:8080';
  private tokenKey = 'auth_token';

  constructor(private http: HttpClient) { }

  create(type:string, group:string, amount:number, title:string): Observable<any> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/transaction`;
    return this.http.post(url,{type,group,amount,title}, { headers: {'Authorization': `Bearer ${token}`}}).pipe(
      map((response: any) => {
        if(response.status==200)
        return response;
      })
    );
  }

  getAll(): Observable<any> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/transaction`;
    return this.http.get(url, { headers: {'Authorization': `Bearer ${token}`}}).pipe(
      map((response: any) => {
        if(response.status==200)
        return response;
      })
    );
  }
  getWeekly(): Observable<any> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/transaction/weekly`;
    return this.http.get(url, { headers: {'Authorization': `Bearer ${token}`}}).pipe(
      map((response: any) => {
        if(response.status==200)
        return response;
      })
    );
  }
  getMonthly(): Observable<any> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/transaction/monthly`;
    return this.http.get(url, { headers: {'Authorization': `Bearer ${token}`}}).pipe(
      map((response: any) => {
        if(response.status==200)
        return response;
      })
    );
  }
  
}
