import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'bda-section-header',
  templateUrl: './section-header.component.html',
  styleUrls: ['./section-header.component.scss']
})
export class SectionHeaderComponent implements OnInit {
  @Input() title: string;
  @Input() description: string;
  @Input() divider = true;

  constructor() {
  }

  ngOnInit() {
  }

}
