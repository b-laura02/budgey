import { Component } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { HlmButtonModule } from '@spartan-ng/ui-button-helm';
import { HlmCardModule } from '@spartan-ng/ui-card-helm';
import { HlmH4Directive } from '@spartan-ng/ui-typography-helm';

@Component({
  selector: 'app-advices',
  standalone: true,
  imports: [
    HlmH4Directive,
    HlmCardModule,
    HlmButtonModule,
    MatIcon,
  ],
  templateUrl: './advices.component.html',
  styleUrl: './advices.component.css'
})
export class AdvicesComponent {

}
