import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'bda-mean-of-transport',
  templateUrl: './mean-of-transport.component.html',
  styleUrls: ['./mean-of-transport.component.scss']
})
export class MeanOfTransportComponent implements OnInit {

  @Input() mapType: 'none' | 'square' | 'rectangle' = 'none';

  constructor() {
  }

  ngOnInit() {
  }

}
