import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'bda-academic-ministries',
  templateUrl: './academic-ministries.component.html',
  styleUrls: ['./academic-ministries.component.scss']
})
export class AcademicMinistriesComponent implements OnInit {

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
  }

  onMinistrySelected(academicMinistry: { id: string, name: string }) {
    this.router.navigate([academicMinistry.id + '/chatka'], {relativeTo: this.activatedRoute});
  }

}
