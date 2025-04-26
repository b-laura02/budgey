import { Component, OnInit, ViewChild } from '@angular/core';
import { BrnSelectComponent, BrnSelectContentComponent, BrnSelectImports } from '@spartan-ng/brain/select';
import { HlmButtonModule } from '@spartan-ng/ui-button-helm';
import { HlmCardModule } from '@spartan-ng/ui-card-helm';
import { HlmFormFieldModule } from '@spartan-ng/ui-formfield-helm';
import { HlmInputDirective, HlmInputModule } from '@spartan-ng/ui-input-helm';
import { HlmLabelModule } from '@spartan-ng/ui-label-helm';
import { HlmSelectContentDirective, HlmSelectImports, HlmSelectModule } from '@spartan-ng/ui-select-helm';
import { HlmH4Directive } from '@spartan-ng/ui-typography-helm';
import { TransactionService } from '../../../shared/services/transaction.service';
import { getRecordForm } from '../../../shared/forms/record.form';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HlmTabsModule } from '@spartan-ng/ui-tabs-helm';
import { TransactionCategoryPipe } from '../../../shared/pipes/transaction-category.pipe';
import { ExpenseCategory, IncomeCategory } from '../../../shared/models/enums/transaction-category.enum';
import { MatIcon } from '@angular/material/icon';
import { SnackbarService } from '../../../shared/services/snackbar.service';
import { TransactionDto } from '../../../shared/models/dtos/transaction.dto';
import { take } from 'rxjs';

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
    HlmLabelModule,
    HlmSelectContentDirective,
    ReactiveFormsModule,
    HlmTabsModule,
    TransactionCategoryPipe,
    MatIcon,
  ],
  templateUrl: './record.component.html',
  styleUrl: './record.component.css'
})
export class RecordComponent implements OnInit {
  @ViewChild('select') select?: HTMLElement;
  recordForm: FormGroup = getRecordForm();
  isExpense: boolean = false;
  incomeCategories = IncomeCategory;
  expenseCategories = ExpenseCategory;
  filteredCategories: any[] = this.incomeCategories;

  constructor(
    private transactionService: TransactionService,
    private snackBar: SnackbarService
  ) { }

  ngOnInit(): void {
    
  }

  onTabsChange(value: boolean) {
    console.log(value);
    this.isExpense = value;
    if (this.isExpense) {
      this.filteredCategories = this.expenseCategories;
    } else {
      this.filteredCategories = this.incomeCategories;
    }
    this.recordForm.reset({
      category: null,
    });
  }

  onSubmit() {
    if (this.recordForm.valid) {
      const data = this.recordForm.value;
      
      const payload = {
        title: data.name?.trim(),
        amount: data.amount,
        date: new Date(data.date).toISOString(),
        category: data.category,
      } as TransactionDto;

      console.log(payload);

      this.transactionService.create(payload)
        .pipe(take(1))
        .subscribe(result => {
          console.log('result: ', result);
        }
      );
    }
  }

  // handleOCR(event: Event) {
  //   const input = event.target as HTMLInputElement;
  //   if (input.files && input.files.length > 0) {
  //     const file = input.files[0];
  //     if (!file.type.match('image.*')) {
  //       this.snackBar.open('Csak képek tölthetők fel!');
  //       return;
  //     }
  //     // Process the image file here
  //     console.log('Image selected:', file);
  //   }
  // }
  
  handleCSV(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      const validExtensions = ['.csv', '.docx'];
      const fileName = file.name.toLowerCase();
      const isValid = validExtensions.some(ext => fileName.endsWith(ext));
      
      if (!isValid) {
        this.snackBar.open('Csak CSV vagy DOCX fájlok tölthetők fel!');
        return;
      }
      // Process the CSV/DOCX file here
      console.log('File selected:', file);
      this.transactionService.importTransactions(file)
        .pipe(take(1))
        .subscribe(result => {
          console.log(result);
        }
      );
    }
  }

}
