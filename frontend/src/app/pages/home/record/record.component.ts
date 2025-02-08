import { Component } from '@angular/core';
import { BrnSelectImports } from '@spartan-ng/brain/select';
import { HlmButtonModule } from '@spartan-ng/ui-button-helm';
import { HlmCardModule } from '@spartan-ng/ui-card-helm';
import { HlmFormFieldModule } from '@spartan-ng/ui-formfield-helm';
import { HlmInputDirective, HlmInputModule } from '@spartan-ng/ui-input-helm';
import { HlmLabelModule } from '@spartan-ng/ui-label-helm';
import { HlmMenuSeparatorComponent } from '@spartan-ng/ui-menu-helm';
import { HlmSelectContentDirective, HlmSelectImports, HlmSelectLabelDirective, HlmSelectModule } from '@spartan-ng/ui-select-helm';
import { HlmH4Directive } from '@spartan-ng/ui-typography-helm';

@Component({
  selector: 'app-record',
  standalone: true,
  imports: [
    HlmH4Directive,
    HlmCardModule,
    HlmButtonModule,
    HlmFormFieldModule,
    HlmInputModule,
    HlmSelectModule,
    HlmInputDirective,
    HlmSelectImports,
    BrnSelectImports,
    HlmMenuSeparatorComponent,
    HlmLabelModule,
    HlmSelectContentDirective
  ],
  templateUrl: './record.component.html',
  styleUrl: './record.component.css'
})
export class RecordComponent {

}
