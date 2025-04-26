import { FormControl, FormGroup, Validators } from "@angular/forms";

export function getRecordForm() {
  return new FormGroup({
    name: new FormControl('', [
      Validators.required,
    ]),
    amount: new FormControl('', [
      Validators.required,
    ]),
    date: new FormControl('', [
      Validators.required,
    ]),
    category: new FormControl(null, [
      Validators.required,
    ]),
  });
}