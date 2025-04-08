import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import { Router, RouterLink } from '@angular/router';
import { getLogInForm } from '../../shared/forms/login.form';
import { AuthService } from '../../shared/services/auth.service';
import { take } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HlmSpinnerComponent } from '@spartan-ng/ui-spinner-helm';
import { HlmButtonModule } from '@spartan-ng/ui-button-helm';
import { HlmSkeletonModule } from '@spartan-ng/ui-skeleton-helm';


@Component({
  selector: 'app-login',
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
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup = getLogInForm();
  isLoading = false;

  constructor(private authService: AuthService, private router: Router, private snackBar: MatSnackBar) {}

  login() {
    console.log("login");
    const email = this.loginForm.get('email')?.value;
    const password = this.loginForm.get('password')?.value;
  
    if (this.loginForm.valid) {
      this.isLoading = true;
      this.authService.login(email, password).pipe(take(1)).subscribe({
        next: (response) => {
          const msg = 'Sikeres bejelentkezés'
          console.log(msg, response);
          this.snackBar.open(msg);
          this.router.navigate(['/home']);
          this.isLoading = false;
        },
        error: (error) => {
          const errorMsg = 'Bejelentkezési hiba'
          console.error(errorMsg, error);
          this.snackBar.open(errorMsg);
          this.isLoading = false;
        },
      });
    } else {
      const errorMsg = 'A form érvénytelen'
      console.error(errorMsg);
      this.snackBar.open(errorMsg);
    }
  }
}
