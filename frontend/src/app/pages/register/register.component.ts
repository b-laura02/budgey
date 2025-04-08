import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterLink } from '@angular/router';
import { getRegisterForm } from '../../shared/forms/register.form';
import { AuthService } from '../../shared/services/auth.service';
import { take } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HlmSpinnerComponent } from '@spartan-ng/ui-spinner-helm';
import { HlmButtonModule } from '@spartan-ng/ui-button-helm';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule, 
    MatIconModule, 
    ReactiveFormsModule, 
    RouterLink, 
    MatButtonModule, 
    MatCardModule,
    HlmSpinnerComponent,
    HlmButtonModule,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  registerForm: FormGroup = getRegisterForm();
  isLoading = false;

  constructor(private authService: AuthService, private router: Router, private snackBar: MatSnackBar) { }

  register() {
    console.log("register");
    const email = this.registerForm.get('email')?.value;
    const fullName = this.registerForm.get('fullName')?.value;
    const password = this.registerForm.get('password')?.value;
    const confirmPassword = this.registerForm.get('confirmPassword')?.value;

    if (this.registerForm.valid && password === confirmPassword) {
      this.isLoading = true;
      this.authService.register(email, fullName, password).pipe(take(1)).subscribe({
        next: (response) => {
          const msg = 'Sikeres regisztráció';
          console.log(msg, response);
          this.snackBar.open(msg);
          this.router.navigate(['/home']);
          this.isLoading = false;
        },
        error: (error) => {
          const errorMsg = 'Regisztrációs hiba';
          console.error(errorMsg, error);
          this.snackBar.open(errorMsg);
          this.isLoading = false;
        }
      });
    } else {
      const errorMsg = 'A form érvénytelen vagy a jelszavak nem egyeznek';
      console.error(errorMsg);
      this.snackBar.open(errorMsg);
    }
  }
}
