import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AcademicPriestDto} from '../../service/rest/dto/academic-priest.dto';

@Component({
  selector: 'bda-admin-academic-priest',
  templateUrl: './academic-priest.component.html',
  styleUrls: ['./academic-priest.component.less']
})
export class AcademicPriestComponent implements OnInit {

  @Input() priest: AcademicPriestDto;
  @Output() moreClick = new EventEmitter<void>();
  @Output() editClick = new EventEmitter<void>();
  @Output() deleteClick = new EventEmitter<void>();


  constructor() {
  }

  ngOnInit() {
  }

}
