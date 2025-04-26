import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatIcon } from '@angular/material/icon';
import { HlmButtonModule } from '@spartan-ng/ui-button-helm';
import { HlmCardModule } from '@spartan-ng/ui-card-helm';
import { HlmFormFieldModule } from '@spartan-ng/ui-formfield-helm';
import { HlmInputModule, HlmInputDirective } from '@spartan-ng/ui-input-helm';
import { HlmH4Directive } from '@spartan-ng/ui-typography-helm';
import { PreferencesService } from '../../../shared/services/preferences.service';
import { Observable, take } from 'rxjs';
import { PreferencesDto } from '../../../shared/models/dtos/preferences.dto';
import { AsyncPipe } from '@angular/common';
import { getSettingsForm } from '../../../shared/forms/settings.form';
import { HlmCheckboxModule } from '@spartan-ng/ui-checkbox-helm';
import { HlmSkeletonComponent } from "../../../shared/components/ui-skeleton-helm/src/lib/hlm-skeleton.component";

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [
    HlmH4Directive,
    HlmCardModule,
    HlmButtonModule,
    HlmFormFieldModule,
    HlmInputModule,
    HlmInputDirective,
    HlmCheckboxModule,
    ReactiveFormsModule,
    MatIcon,
    AsyncPipe,
    HlmSkeletonComponent
  ],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent implements OnInit {
  preferences$: Observable<PreferencesDto> | null = null;
  settingsForm = getSettingsForm();

  constructor(private preferencesService: PreferencesService) {}

  ngOnInit(): void {
    this.loadData();
  }

  async loadData(): Promise<void> {
    this.preferences$ = this.preferencesService.getPreferences();

    this.preferences$
      .pipe(take(1))
      .subscribe(prefs => {
        this.settingsForm.patchValue({
          targetIncome: prefs.targetIncome.toString(),
          targetExpense: prefs.targetExpense.toString(),
          targetProfit: prefs.targetProfit.toString(),
          allowAIProcessing: prefs.allowAIProcessing,
        });
      }
    );
  }

  onSubmit(): void {
    if (this.settingsForm.valid) {
      const data = this.settingsForm.value;

      const prefDto = {
        targetIncome: data.targetIncome ?? 0,
        targetExpense: data.targetExpense ?? 0,
        targetProfit: data.targetProfit ?? 0,
        allowAIProcessing: data.allowAIProcessing,
      } as PreferencesDto;

      console.log(prefDto);

      this.preferencesService.update(prefDto)
        .pipe(take(1))
        .subscribe(result => console.log(result)
      );
    }
  }

}
