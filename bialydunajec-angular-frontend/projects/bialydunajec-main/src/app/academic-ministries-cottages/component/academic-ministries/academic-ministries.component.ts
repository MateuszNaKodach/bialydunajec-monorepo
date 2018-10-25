import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AcademicMinistryEndpoint} from '../../service/rest/academic-ministry.endpoint';
import {map} from 'rxjs/operators';
import {AcademicMinistryCardViewModel} from '../academic-ministry-card/academic-ministry-card.view-model';
import {Observable} from 'rxjs';

@Component({
  selector: 'bda-academic-ministries',
  templateUrl: './academic-ministries.component.html',
  styleUrls: ['./academic-ministries.component.scss']
})
export class AcademicMinistriesComponent implements OnInit {

  academicMinistries$: Observable<AcademicMinistryCardViewModel[]>;

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private academicMinistryEndpoint: AcademicMinistryEndpoint) {
  }

  ngOnInit() {
    this.academicMinistries$ = this.academicMinistryEndpoint.getAllAcademicMinistries()
      .pipe(
        map(academicMinistryList =>
          academicMinistryList.map(it => new AcademicMinistryCardViewModel(it.academicMinistryId, it.displayName, it.logoImageUrl)))
      );
  }

  onMinistrySelected(academicMinistry: { id: string, name: string }) {
    this.router.navigate([academicMinistry.id + '/chatka'], {relativeTo: this.activatedRoute});
  }

}
