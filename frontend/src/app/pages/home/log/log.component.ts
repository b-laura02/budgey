import { AsyncPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { HlmButtonModule } from '@spartan-ng/ui-button-helm';
import { HlmTableModule } from '@spartan-ng/ui-table-helm';
import { HlmTabsModule } from '@spartan-ng/ui-tabs-helm';
import { HlmH4Directive } from '@spartan-ng/ui-typography-helm';
import { TransactionService } from '../../../shared/services/transaction.service';
import { map, Observable, take, tap } from 'rxjs';
import { SummaryDto } from '../../../shared/models/dtos/summary.dto';
import { MatIcon } from '@angular/material/icon';
import { Transaction } from '../../../shared/models/dtos/transaction.dto';
import { HlmSkeletonModule } from '@spartan-ng/ui-skeleton-helm';

@Component({
  selector: 'app-log',
  standalone: true,
  imports: [
    HlmH4Directive, 
    HlmTableModule, 
    HlmTabsModule, 
    HlmButtonModule, 
    AsyncPipe, 
    MatIcon,
    HlmSkeletonModule,
  ],
  templateUrl: './log.component.html',
  styleUrl: './log.component.css'
})
export class LogComponent implements OnInit {
  currentTab = 0;
  transactionData$: Observable<Transaction[]> | null = null;
  summaryData$: Observable<SummaryDto[]> | null = null;
  currentPage = 1;
  itemsPerPage = 5;

  constructor(private transactionService: TransactionService) {}

  ngOnInit(): void {
    this.loadData();
  }

  onTabsChange(index: number) {
    this.currentTab = index;
    this.currentPage = 1;
    this.loadData();
  }

  onPageChange(page: number) {
    this.currentPage = page;
    this.loadData();
  }

  loadData() {
    const offset = (this.currentPage - 1) * this.itemsPerPage;
    
    switch (this.currentTab) {
      case 0: // Tételes tranzakciók
        this.transactionData$ = this.transactionService.getAll(offset, this.itemsPerPage);
        break;
      case 1: // Heti kimutatások
        this.summaryData$ = this.transactionService.getWeeklySummaries(offset, this.itemsPerPage);
        break;
      case 2: // Havi kimutatások
        this.summaryData$ = this.transactionService.getMonthlySummaries(offset, this.itemsPerPage);
        break;
    }
  }

  download(item: SummaryDto) {
    this.transactionService.exportTransactions(item.fromDate, item.toDate)
      .pipe(take(1))
      .subscribe(result => {
        console.log(result);
      }
    );
  }
}
