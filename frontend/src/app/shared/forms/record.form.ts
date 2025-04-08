import { FormControl, FormGroup, Validators } from "@angular/forms";

export function getRecordForm() {
  return new FormGroup({
    mainGroup: new FormControl('', [
      Validators.required,
    ]),
    group: new FormControl('', [
      Validators.required,
    ]),
  });
}