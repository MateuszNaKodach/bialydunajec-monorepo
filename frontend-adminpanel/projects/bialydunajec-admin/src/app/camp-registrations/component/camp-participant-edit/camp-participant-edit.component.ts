import {Component, OnInit} from '@angular/core';
import {EditFormMode} from '../../../academic-ministry/component/academic-ministry-edit/edit-form.mode';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Observable, Observer} from 'rxjs';
import {ActivatedRoute, Params} from '@angular/router';
import {finalize, tap} from 'rxjs/operators';
import {
  CreateAcademicMinistryRequest,
  UpdateAcademicMinistryRequest
} from '../../../academic-ministry/service/rest/request/create-academic-ministry.request';
import {AcademicMinistryResponse} from '../../../academic-ministry/service/rest/response/academic-ministry.response';
import {AcademicMinistryEditFormModel} from '../../../academic-ministry/component/academic-ministry-edit/academic-ministry-edit.form-model';
import {AlertViewModel} from '../../../shared/view-model/ng-zorro/alert.view-model';
import {CampRegistrationsEndpoint} from '../../service/rest/camp-registrations.endpoint';

@Component({
  selector: 'bda-admin-camp-participant-edit',
  templateUrl: './camp-participant-edit.component.html',
  styleUrls: ['./camp-participant-edit.component.less']
})
export class CampParticipantEditComponent implements OnInit {

  // FORM UI
  private formMode: EditFormMode = EditFormMode.CREATE;
  campParticipantForm: FormGroup;
  private formSubmitFn;
  private formSubmitObservers;
  private determineModeFn;
  lastAlert: AlertViewModel;
  submittingInProgress = false;


  constructor(private route: ActivatedRoute, private formBuilder: FormBuilder, private campRegistrationsEndpoint: CampRegistrationsEndpoint) {
  }

  ngOnInit() {
    this.initDetermineModeFunction();
    const submitPipeOperators = [finalize(() => this.submittingInProgress = false)];
    /*
    this.initFormSubmitFunctions(
      (createAcademicMinistryRequest: CreateAcademicMinistryRequest) =>
        this.academicMinistryEndpoint.createAcademicMinistry(createAcademicMinistryRequest)
          .pipe(...submitPipeOperators, tap(() => this.academicMinistryForm.reset())),
      (academicMinistryId: string, updateAcademicMinistryRequest: UpdateAcademicMinistryRequest) =>
        this.academicMinistryEndpoint.updateAcademicMinistryById(academicMinistryId, updateAcademicMinistryRequest)
          .pipe(...submitPipeOperators)
    );
    */
    this.initForm();
    this.loadInitFormValues();
  }

  initFormSubmitFunctions(createSubmitFn: (...any) => Observable<any>, editSubmitFn: (...any) => Observable<any>) {
    this.formSubmitFn = {};
    this.formSubmitFn[EditFormMode.CREATE] = createSubmitFn;
    this.formSubmitFn[EditFormMode.EDIT] = editSubmitFn;
  }

  initFormSubmitObservers(createSubmitObserver: Observer<any>, editSubmitObserver: Observer<any>) {
    this.formSubmitObservers[EditFormMode.CREATE] = createSubmitObserver;
    this.formSubmitObservers[EditFormMode.EDIT] = editSubmitObserver;
  }

  initDetermineModeFunction() {
    this.determineModeFn = (activatedRouteParams: Params) => {
      if (activatedRouteParams['campParticipantId'] != null) {
        return EditFormMode.EDIT;
      } else {
        return EditFormMode.CREATE;
      }
    };
  }

  loadInitFormValues() {
    /*this.route.params
      .pipe(
        tap(params => this.updateFormMode(params)),
        flatMap(params => this.academicMinistryEndpoint.getAcademicMinistryById(params['academicMinistryId']))
      )
      .subscribe(
        (response: AcademicMinistryResponse) => {
          console.log('Academic ministry:', response);
          this.currentAcademicMinistryId = response.academicMinistryId;
          this.academicMinistryForm.patchValue(AcademicMinistryEditFormModel.fromDto(response));
          this.academicMinistryForm.updateValueAndValidity();
          this.loadPriestsList();
        }
      );*/
  }

  private updateFormMode(params) {
    return this.formMode = this.determineModeFn(params);
  }

  initForm() {

  }

  onSubmit() {

  }
}
