import { FormControl, FormGroup, Validators } from '@angular/forms';

export function getRegisterForm() {
  return new FormGroup({
    email: new FormControl('', [
      Validators.required,
      Validators.email,
    ]),
    fullName: new FormControl('', [
      Validators.required,
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(6),
    ]),
    confirmPassword: new FormControl('', [
      Validators.required,
      Validators.minLength(6),
    ]),
  });
}