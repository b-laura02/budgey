import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HlmButtonModule } from '@spartan-ng/ui-button-helm';

@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [
    HlmButtonModule,
    RouterLink
  ],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.css'
})
export class WelcomeComponent {

}
