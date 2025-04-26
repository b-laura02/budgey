import { Component } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { HlmButtonModule } from '@spartan-ng/ui-button-helm';
import { HlmMenuModule } from '@spartan-ng/ui-menu-helm';

@Component({
  selector: 'app-navrail',
  standalone: true,
  imports: [HlmButtonModule, HlmMenuModule, MatIcon, RouterLink, RouterLinkActive],
  templateUrl: './navrail.component.html',
  styleUrl: './navrail.component.css'
})
export class NavrailComponent {
}
