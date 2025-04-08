import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { BalanceGroup } from '../models/enums/balance-group.enum';
import { BalanceGroupDto } from '../models/dtos/balance-group.dto';
import { BalanceMainGroup } from '../models/enums/balance-main-group.enum';

@Injectable({
  providedIn: 'root'
})
export class ClassificationService {
  private baseUrl = 'http://localhost:8080';
  private tokenKey = 'auth_token';

  constructor(private http: HttpClient) { }

  getBalanceItemsByGroups(balanceMainGroup: BalanceMainGroup, balanceGroup: BalanceGroup): Observable<any> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/classification/items`;
    const params = {
      balanceMainGroup,
      balanceGroup
    };

    return this.http.get(url, { headers: { 'Authorization': `Bearer ${token}` }, params }).pipe(
      map((response: any) => {
        if (response) {
          return response;
        }
      })
    );
  }

  getBalanceGroupsByBalanceMainGroup(balanceMainGroup: BalanceMainGroup): Observable<any> {
    const token = localStorage.getItem(this.tokenKey);
    const url = `${this.baseUrl}/classification/groups`;
    const params = { balanceMainGroup };
    return this.http.get(url, { headers: { 'Authorization': `Bearer ${token}` }, params }).pipe(
      map((response: any) => {
        if (response) {          
          return response;
        }
      })
    );
  }
  
}
