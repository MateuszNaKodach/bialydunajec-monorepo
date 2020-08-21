import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'bda-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @Output() toggleSidebar = new EventEmitter<void>();

  constructor() {
  }

  ngOnInit() {
  }

}
