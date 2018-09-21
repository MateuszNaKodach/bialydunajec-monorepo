import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'bda-mean-of-transport-header',
  templateUrl: './mean-of-transport-header.component.html',
  styleUrls: ['./mean-of-transport-header.component.scss']
})
export class MeanOfTransportHeaderComponent implements OnInit {

  @Input() title: string;
  @Input() description: string;

  constructor() {
  }

  ngOnInit() {
  }

}
