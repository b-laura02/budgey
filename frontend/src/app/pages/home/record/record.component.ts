import { Component, OnInit } from '@angular/core';
import { BrnSelectImports } from '@spartan-ng/brain/select';
import { HlmButtonModule } from '@spartan-ng/ui-button-helm';
import { HlmCardModule } from '@spartan-ng/ui-card-helm';
import { HlmFormFieldModule } from '@spartan-ng/ui-formfield-helm';
import { HlmInputDirective, HlmInputModule } from '@spartan-ng/ui-input-helm';
import { HlmLabelModule } from '@spartan-ng/ui-label-helm';
import { HlmMenuSeparatorComponent } from '@spartan-ng/ui-menu-helm';
import { HlmSelectContentDirective, HlmSelectImports, HlmSelectLabelDirective, HlmSelectModule } from '@spartan-ng/ui-select-helm';
import { HlmH4Directive } from '@spartan-ng/ui-typography-helm';
import { TransactionService } from '../../../shared/services/transaction.service';
import { ClassificationService } from '../../../shared/services/classification.service';
import { BalanceMainGroup } from '../../../shared/models/enums/balance-main-group.enum';
import { getRecordForm } from '../../../shared/forms/record.form';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { map, Observable, of, tap } from 'rxjs';
import { BalanceGroup } from '../../../shared/models/enums/balance-group.enum';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-record',
  standalone: true,
  imports: [
    HlmH4Directive,
    HlmCardModule,
    HlmButtonModule,
    HlmFormFieldModule,
    HlmInputModule,
    HlmSelectModule,
    HlmInputDirective,
    HlmSelectImports,
    BrnSelectImports,
    HlmMenuSeparatorComponent,
    HlmLabelModule,
    HlmSelectContentDirective,
    ReactiveFormsModule,
    AsyncPipe
  ],
  templateUrl: './record.component.html',
  styleUrl: './record.component.css'
})
export class RecordComponent implements OnInit {
  recordForm: FormGroup = getRecordForm();
  balanceMainGroups = Object.entries(BalanceMainGroup);
  balanceGroups$: Observable<any[]> | null = null;

  constructor(
    private transactionService: TransactionService,
    private classificationService: ClassificationService,
  ) { }

  ngOnInit(): void {
    this.recordForm.get('mainGroup')?.valueChanges.subscribe((value: BalanceMainGroup) => {
      this.onMainGroupChange(value);
    });
  }

  onMainGroupChange(selectedKey: BalanceMainGroup): void {
    console.log('Selected Key:', selectedKey);
    const selectedGroup = this.balanceMainGroups.find(group => group[0] === selectedKey);
    if (selectedGroup) {
      console.log('Selected Value:', selectedGroup[1]);
      this.fetchBalanceGroups(selectedKey);
    }
  }

  fetchBalanceGroups(balanceMainGroup: BalanceMainGroup) {
    this.balanceGroups$ = this.classificationService.getBalanceGroupsByBalanceMainGroup(balanceMainGroup).pipe(
      map((response: BalanceGroup[]) => {
        const balanceGroups = Object.entries(BalanceGroup);
        return balanceGroups.filter((group) => response.includes(group[0] as BalanceGroup));
      })
    );
  }

}
