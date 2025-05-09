import { Component } from '@angular/core';
import { HlmButtonModule } from '@spartan-ng/ui-button-helm';
import { HlmTabsModule } from '@spartan-ng/ui-tabs-helm';
import { HlmH4Directive } from '@spartan-ng/ui-typography-helm';
import { finalize } from 'rxjs';
import { SummaryDto } from '../../../shared/models/dtos/summary.dto';
import { TransactionService } from '../../../shared/services/transaction.service';
import { BaseChartDirective } from 'ng2-charts';
import { HlmSpinnerModule } from '@spartan-ng/ui-spinner-helm';

@Component({
  selector: 'app-statements',
  standalone: true,
  imports: [
    HlmH4Directive,
    HlmTabsModule,
    BaseChartDirective,
    HlmSpinnerModule,
    HlmButtonModule
  ],
  templateUrl: './statements.component.html',
  styleUrl: './statements.component.css'
})
export class StatementsComponent {
  activeTab: 'income' | 'expense' | 'profit' = 'profit';
  loading = true;
  selectedPeriod: 'monthly' | 'weekly' = 'monthly';
  periodCount = 6;

  chartData = {
    labels: [] as string[],
    income: [] as number[],
    expense: [] as number[],
    profit: [] as number[]
  };

  constructor(private transactionService: TransactionService) {}

  ngOnInit(): void {
    this.loadChartData();
  }

  loadChartData(): void {
    this.loading = true;
    
    const serviceCall = this.selectedPeriod === 'monthly'
      ? this.transactionService.getMonthlySummaries(0, this.periodCount)
      : this.transactionService.getWeeklySummaries(0, this.periodCount);

    serviceCall
      .pipe(finalize(() => this.loading = false))
      .subscribe(data => this.processData(data));
  }

  private processData(data: SummaryDto[]): void {
    const reversedData = [...data].reverse();
    
    this.chartData = {
      labels: reversedData.map(item => {
        if (this.selectedPeriod === 'monthly') {
          return new Date(item.fromDate).toLocaleDateString('hu-HU', { month: 'long', year: 'numeric' });
        }
        return `${new Date(item.fromDate).toLocaleDateString('hu-HU', { month: 'short', day: 'numeric' })} - ${new Date(item.toDate).toLocaleDateString('hu-HU', { month: 'short', day: 'numeric' })}`;
      }),
      income: reversedData.map(item => item.income),
      expense: reversedData.map(item => Math.abs(item.expense)),
      profit: reversedData.map(item => item.profit)
    };
  }

  changePeriod(period: 'monthly' | 'weekly'): void {
    this.selectedPeriod = period;
    this.loadChartData();
  }
}
