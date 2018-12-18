import {Component, OnInit} from '@angular/core';
import {Observable, Observer, pipe} from 'rxjs';
import {CampEditionResponse} from '../../../camp-edition/service/rest/response/camp-edition.response';
import {CampRegistrationsEditionResponse} from '../../service/rest/response/camp-registrations-edition.response';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';
import {campRegistrationsRoutingPaths} from '../../camp-registrations-routing.paths';
import {coreRoutingPaths} from '../../../core/core-routing.paths';
import {CottageResponse} from '../../service/rest/response/cottage.response';
import {tap} from 'rxjs/operators';
import {AlertViewModel} from '../../../shared/view-model/ng-zorro/alert.view-model';
import {HttpResponseHelper} from '../../../shared/helper/HttpResponseHelper';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'bda-admin-cottage-list',
  templateUrl: './cottage-list.component.html',
  styleUrls: ['./cottage-list.component.less']
})
export class CottageListComponent implements OnInit {

  coreRoutingPaths = coreRoutingPaths;
  campRegistrationsRoutingPaths = campRegistrationsRoutingPaths;
  availableCampEditions: Observable<CampEditionResponse[]>;
  selectedCampRegistrations: Observable<CampRegistrationsEditionResponse>;
  cottagesObservable: Observable<CottageResponse[]>;
  private campRegistrationsId: number;

  lastAlert: AlertViewModel;

  constructor(
    private campRegistrationsEndpoint: CampRegistrationsEndpoint,
    private router: Router,
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.availableCampEditions = this.campRegistrationsEndpoint.getAllCampEditions();
  }

  onCampEditionIdSelected(selectedCampEditionId: number) {
    /* FIXME: Add campEditionid as parameter! Or read about queryParamsHandling - nice for different camp-registrations tabs
    https://angular.io/guide/router
     */
    this.router.navigate(
      ['/panel/camp-registrations/cottages'],
      {
        relativeTo: this.activatedRoute,
        queryParams: {
          'camp-edition': selectedCampEditionId
        }
      }
    );

    this.selectedCampRegistrations = this.campRegistrationsEndpoint.getCampRegistrationsEditionById(selectedCampEditionId)
      .pipe(
        tap(response => this.campRegistrationsId = response.campRegistrationsEditionId)
      );
    this.updateCottages(selectedCampEditionId);
  }

  updateCottages(selectedCampEditionId: number) {
    this.cottagesObservable = this.campRegistrationsEndpoint.getAllCottagesByCampRegistrationsEditionId(selectedCampEditionId);
  }

  private cottageCreationObserver: Observer<any> = {
    next: response => {
      this.lastAlert = {
        type: 'success',
        message: 'Nowa Chatka Obozowa',
        description: 'Nowa Chatka została utworzona!!'
      };
      this.updateCottages(this.campRegistrationsId);
    },
    error: response => {
      console.log(response);
      const error = response.error;
      const restErrors = response.error.restErrors;
      if (HttpResponseHelper.isStatus4xx(response) && restErrors) {
        this.lastAlert = {
          type: 'error',
          message: 'Nowa Chatka Obozowa',
          description: 'Chatka nie została utworzona, z powodu złamania reguł:' +
            restErrors.map((e: string) => ` ${e}`)
        };
      } else if (response.status === 0) {
        this.lastAlert = {
          type: 'error',
          message: 'Nowa Chatka Obozowa',
          description: 'Chatka nie została utworzona, z powodu braku odpowiedzi serwera.'
        };
      } else {
        this.lastAlert = {
          type: 'error',
          message: 'Nowa Chatka Obozowa',
          description: `Chatka nie została utworzona, z powodu błędu 
                  (jeśli nie wiesz co zrobić, to skontaktuj się z administratorem): \n ${error.message}`
        };
      }
    },
    complete: () => {
    }
  };

  private cottageDeletionObserver: Observer<any> = {
    next: response => {
      this.lastAlert = {
        type: 'success',
        message: 'Chatka została usunięta',
        description: 'Chatka została nieodwracalnie usunięta!'
      };
      this.updateCottages(this.campRegistrationsId);
    },
    error: response => {
      console.log(response);
      const error = response.error;
      const restErrors = response.error.restErrors;
      if (HttpResponseHelper.isStatus4xx(response) && restErrors) {
        this.lastAlert = {
          type: 'error',
          message: 'Chatka nie może być usunięta',
          description: 'Próba usunięcia chatki nie powiodła się z powodu złamania reguł: ' +
            restErrors.map((e: string) => ` ${e}`)
        };
      } else if (response.status === 0) {
        this.lastAlert = {
          type: 'error',
          message: 'Chatka nie może być usunięta',
          description: 'Próba usunięcia chatki nie powiodła się z powodu braku odpowiedzi serwera. '
        };
      } else {
        this.lastAlert = {
          type: 'error',
          message: 'Chatka nie może być usunięta',
          description: `Próba usunięcia chatki nie powiodła się z powodu błędu 
                  (jeśli nie wiesz co zrobić, to skontaktuj się z administratorem): \n ${error.message}`
        };
      }
    },
    complete: () => {
    }
  };

  onSubmittedNewAcademicMinistryCottage(event: { selectedAcademicMinistryId: string }) {
    this.lastAlert = null;
    this.campRegistrationsEndpoint.createAcademicMinistryCottage(this.campRegistrationsId, event.selectedAcademicMinistryId)
      .subscribe(this.cottageCreationObserver);
  }

  onSubmittedNewStandaloneCottage(event: { cottageName: string }) {
    this.lastAlert = null;
    this.campRegistrationsEndpoint.createStandaloneCottage(this.campRegistrationsId, event.cottageName)
      .subscribe(this.cottageCreationObserver);
  }

  onDeleteConfirm(cottage: CottageResponse) {
    this.lastAlert = null;
    this.campRegistrationsEndpoint.deleteCottage(cottage.cottageId)
      .pipe(
        tap(_ => this.updateCottages(this.campRegistrationsId))
      )
      .subscribe(this.cottageDeletionObserver);
  }
}
