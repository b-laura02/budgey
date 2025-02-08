import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavrailComponent } from './navrail.component';

describe('NavrailComponent', () => {
  let component: NavrailComponent;
  let fixture: ComponentFixture<NavrailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavrailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavrailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
