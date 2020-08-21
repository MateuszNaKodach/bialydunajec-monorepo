import {Component} from '@angular/core';
import {RegistrationFormStepAbstractComponent} from '../registration-form-step.abstract-component';
import {CamperRegistrationFormStateService} from '../../../service/camper-registration-form-state.service';
import {CamperRegistrationFormNavigator} from '../../../service/camper-registration-form.navigator';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, Validators} from '@angular/forms';
import {StepId} from '../registration-form.config';
import {CampRegistrationsEndpoint} from '../../../service/rest/camp-registrations-endpoint.service';
import {CottageCardViewModel} from './cottage-card/cottage-card.view-model';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {CampRegistrationsCottageResponse} from '../../../service/rest/response/camp-registrations-cottage.response';
import {campersRegistrationRoutingPaths} from '../../../campers-registration-routing.paths';
import {environment} from '../../../../../environments/environment';

@Component({
  selector: 'bda-cottage-form',
  templateUrl: './cottage-form.component.html',
  styleUrls: ['./cottage-form.component.scss']
})
export class CottageFormComponent extends RegistrationFormStepAbstractComponent {

  environment = environment;
  cottages: Observable<CottageCardViewModel[]>;

  constructor(
    mainFormState: CamperRegistrationFormStateService,
    formNavigator: CamperRegistrationFormNavigator,
    stepRoute: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    private inProgressCampRegistrationsEndpoint: CampRegistrationsEndpoint) {
    super(StepId.COTTAGE, mainFormState, formNavigator, stepRoute);
  }

  protected initStepFormControls() {
    this.stepForm = this.formBuilder.group({
        cottageId: [null, [Validators.required]],
        reCaptcha: [null, environment.google.reCaptcha.enabled ? [Validators.required] : []]
      }
    );
  }

  startFormStepInit() {
    const camperGender = this.mainFormState.getPersonalFormDataSnapshot().personalData.gender;
    if (camperGender) {
      this.cottages = this.inProgressCampRegistrationsEndpoint.getAllCottagesByInProgressCampRegistrations(camperGender)
        .pipe(
          map(
            (response: CampRegistrationsCottageResponse[]) =>
              response.map(cottage => new CottageCardViewModel(
                cottage.cottageId, cottage.name, cottage.logoImageUrl, cottage.hasSpace
              ))
          )
        );
    }
  }

  get camperGender() {
    return this.mainFormState.getPersonalFormDataSnapshot().personalData.gender;
  }

  get cottageId() {
    return this.stepForm.get('cottageId');
  }


  get reCaptcha() {
    return this.stepForm.get('reCaptcha');
  }

  onClickCampSignUp() {
    this.onSubmitStepForm();
    if (this.mainFormState.isFormValid()) {
      this.navigateToFormSummary();
    } else {
      console.log('FORM INVALID!');
    }
  }

  private navigateToFormSummary() {
    this.router.navigate(['../' + campersRegistrationRoutingPaths.summary], {relativeTo: this.stepRoute});
  }

  onReCaptchaResolved(captchaResponse: string) {
    console.log(`Resolved captcha with response ${captchaResponse}:`);
  }
}
