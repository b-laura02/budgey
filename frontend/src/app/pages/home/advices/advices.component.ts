import { Component, OnInit } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { HlmButtonModule } from '@spartan-ng/ui-button-helm';
import { HlmCardModule } from '@spartan-ng/ui-card-helm';
import { HlmH4Directive } from '@spartan-ng/ui-typography-helm';
import { TipService } from '../../../shared/services/tip.service';
import { Observable, take } from 'rxjs';
import { TipDto } from '../../../shared/models/dtos/tip.dto';
import { AsyncPipe } from '@angular/common';
import { HlmToggleModule } from '@spartan-ng/ui-toggle-helm';
import { BrnToggleModule } from '@spartan-ng/brain/toggle';

@Component({
  selector: 'app-advices',
  standalone: true,
  imports: [
    HlmH4Directive,
    HlmCardModule,
    HlmButtonModule,
    MatIcon,
    AsyncPipe,
    HlmToggleModule,
    BrnToggleModule
  ],
  templateUrl: './advices.component.html',
  styleUrl: './advices.component.css'
})
export class AdvicesComponent implements OnInit {
  tips$: Observable<TipDto[]> | null = null;
  showAi = true;
  showNormal = true;

  constructor(private tipService: TipService) {}

  ngOnInit(): void {
    this.tips$ = this.tipService.getAll();
  }

  generate() {
    this.tipService.generate()
      .pipe(take(1))
      .subscribe(result => {
        console.log(result);
        this.tips$ = this.tipService.getAll();
      }
    )
  }
}
