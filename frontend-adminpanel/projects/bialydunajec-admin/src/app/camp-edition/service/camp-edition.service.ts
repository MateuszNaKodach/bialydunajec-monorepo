import {Injectable} from '@angular/core';
import {CampEditionEndpoint} from './rest/camp-edition.endpoint';
import {CampEditionResponse} from './rest/response/camp-edition.response';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CampEditionService {

  private campEditions: CampEditionResponse[];
  private campEditionsChanges = new Subject<CampEditionResponse[]>();

  constructor(private campEditionEndpoint: CampEditionEndpoint) {
  }

  updateCampEditions() {
    this.campEditionEndpoint.getAllCampEditions()
      .subscribe(
        response => {
          this.campEditions = response;
          this.campEditionsChanges.next(this.getCampEditions);
        }
      )
    ;
  }

  get getCampEditions() {
    return this.campEditions.slice();
  }

  get getCampEditionsChanges() {
    return this.campEditionsChanges.asObservable();
  }
}
