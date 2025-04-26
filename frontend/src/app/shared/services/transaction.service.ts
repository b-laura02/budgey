import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Transaction, TransactionDto } from '../models/dtos/transaction.dto';
import { SummaryDto } from '../models/dtos/summary.dto';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private baseUrl = 'http://localhost:8080';
  private tokenKey = 'auth_token';

  constructor(private http: HttpClient) { }

  create(dto: TransactionDto): Observable<Transaction> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/transaction`;
    return this.http.post(url,dto, { headers: {'Authorization': `Bearer ${token}`}}).pipe(
      map((response: any) => {
        if (response) {
          return response;
        }
      })
    );
  }

  // Összes tranzakció lekérdezése
  getAll(page: number = 0, take: number = 5): Observable<Transaction[]> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/transaction?page=${page}&take=${take}`;
    return this.http.get(url, { headers: {'Authorization': `Bearer ${token}`}}).pipe(
      map((response: any) => {
        if (response) {
          return response;
        }
      })
    );
  }

  // Heti összesítések
  getWeeklySummaries(page: number = 0, take: number = 5): Observable<SummaryDto[]> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/transaction/weekly?page=${page}&take=${take}`;
    return this.http.get(url, { headers: {'Authorization': `Bearer ${token}`}}).pipe(
      map((response: any) => {
        if (response) {
          return response;
        }
      })
    );
  }

  // Havi összesítések
  getMonthlySummaries(page: number = 0, take: number = 5): Observable<SummaryDto[]> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/transaction/monthly?page=${page}&take=${take}`;
    return this.http.get(url, { headers: {'Authorization': `Bearer ${token}`}}).pipe(
      map((response: any) => {
        if (response) {
          return response;
        }
      })
    );
  }

  // Egy tranzakció lekérdezése ID alapján
  getById(id: number): Observable<Transaction> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/transaction/${id}`;
    return this.http.get(url, { headers: {'Authorization': `Bearer ${token}`}}).pipe(
      map((response: any) => {
        if (response) {
          return response;
        }
      })
    );
  }

  // Tranzakció frissítése
  update(id: number, dto: TransactionDto): Observable<Transaction> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/transaction/${id}`;
    return this.http.patch(url, dto, { headers: {'Authorization': `Bearer ${token}`}}).pipe(
      map((response: any) => {
        if (response) {
          return response;
        }
      })
    );
  }

  // Tranzakció törlése
  delete(id: number): Observable<Transaction> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/transaction/${id}`;
    return this.http.delete(url, { headers: {'Authorization': `Bearer ${token}`}}).pipe(
      map((response: any) => {
        if (response) {
          return response;
        }
      })
    );
  }

  // CSV importálás
  importTransactions(file: File): Observable<Transaction[]> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/transaction/import`;
    
    const formData = new FormData();
    formData.append('file', file, file.name);

    console.log("mivanitt?");

    return this.http.post(url, formData, { 
      headers: {
        'Authorization': `Bearer ${token}`,
        // 'Content-Type': 'multipart/form-data'
        // 'Content-Type' nem szükséges, a browser automatikusan beállítja multipart/form-data-ra
      }
    }).pipe(
      map((response: any) => {
        if (response) {
          return response;
        }
      })
    );
  }

  // CSV exportálás
  exportTransactions(from: string, to: string): Observable<any> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/transaction/export?from=${from}&to=${to}`;
    
    return this.http.get(url, { 
      headers: {
        'Authorization': `Bearer ${token}`,
        'Accept': 'text/csv'
      },
      responseType: 'blob' // Fontos, hogy blob-ként kezeljük a CSV fájlt
    }).pipe(
      map((response: any) => {
        if (response) {
          // Blob letöltése
          const blob = new Blob([response], { type: 'text/csv' });
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = `transactions_${from}_${to}.csv`;
          document.body.appendChild(a);
          a.click();
          document.body.removeChild(a);
          window.URL.revokeObjectURL(url);
        }
        return response;
      })
    );
  }
  
}
