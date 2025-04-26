import { FormControl, FormGroup, Validators } from "@angular/forms";

export function getSettingsForm() {
  return new FormGroup({
    targetIncome: new FormControl('', [
      Validators.required,
    ]),
    targetExpense: new FormControl('', [
      Validators.required,
    ]),
    targetProfit: new FormControl('', [
      Validators.required,
    ]),
    allowAIProcessing: new FormControl(false, [
      Validators.required,
    ]),
  });
}