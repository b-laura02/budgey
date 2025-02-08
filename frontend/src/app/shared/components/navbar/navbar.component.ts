import { Component } from '@angular/core';
import { HlmButtonModule } from '@spartan-ng/ui-button-helm';
import { HlmMenuModule } from '@spartan-ng/ui-menu-helm';
import { BrnMenuTriggerDirective } from '@spartan-ng/brain/menu';
import { MatIcon } from '@angular/material/icon';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [HlmButtonModule, HlmMenuModule, BrnMenuTriggerDirective, MatIcon, RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

}
