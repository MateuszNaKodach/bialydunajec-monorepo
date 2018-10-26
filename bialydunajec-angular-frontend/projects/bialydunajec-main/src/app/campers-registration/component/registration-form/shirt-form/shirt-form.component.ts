import {Component} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {RegistrationFormStepAbstractComponent} from '../registration-form-step.abstract-component';
import {CamperRegistrationFormStateService} from '../../../service/camper-registration-form-state.service';
import {StepId} from '../registration-form.config';
import {Gender} from '../../../../shared/model/gender.enum';
import {CamperRegistrationFormNavigator} from '../../../service/camper-registration-form.navigator';
import {ActivatedRoute} from '@angular/router';
import {CampRegistrationsEndpoint} from '../../../service/rest/camp-registrations-endpoint.service';
import {tap} from 'rxjs/operators';
import {
  ShirtColorOptionDto,
  ShirtSizeOptionDto
} from '../../../../../../../bialydunajec-admin/src/app/camp-registrations/service/rest/dto/camp-edition-shirt.dto';

@Component({
  selector: 'bda-shirt-form',
  templateUrl: './shirt-form.component.html',
  styleUrls: ['./shirt-form.component.scss']
})
export class ShirtFormComponent extends RegistrationFormStepAbstractComponent {

  private availableShirtColors: ShirtColorOptionDto[] = [];
  private availableMaleShirtSizes: ShirtSizeOptionDto[] = [];
  private availableFemaleShirtSizes: ShirtSizeOptionDto[] = [];
  private availableShirtSizes: ShirtSizeOptionDto[] = [];


  constructor(
    mainFormState: CamperRegistrationFormStateService,
    formNavigator: CamperRegistrationFormNavigator,
    stepRoute: ActivatedRoute,
    private campRegistrationsEndpoint: CampRegistrationsEndpoint,
    private formBuilder: FormBuilder) {
    super(StepId.SHIRT, mainFormState, formNavigator, stepRoute);
  }

  startFormStepInit() {
    this.campRegistrationsEndpoint.getCampEditionShirtByInProgressCampRegistrations()
      .pipe(
        tap(shirt => {
          this.availableShirtColors = shirt.colorOptions;
          this.availableFemaleShirtSizes = shirt.sizeOptions.filter(it => it.size.type === 'FEMALE')
            .sort((n1, n2) => n1.size.height - n2.size.height);
          this.availableMaleShirtSizes = shirt.sizeOptions.filter(it => it.size.type === 'MALE')
            .sort((n1, n2) => n1.size.height - n2.size.height);
          this.availableShirtSizes = shirt.sizeOptions
            .sort((n1, n2) => n1.size.height - n2.size.height);
        })
      ).subscribe();
  }

  protected initStepFormControls() {
    this.stepForm = this.formBuilder.group({
        color: [null, [Validators.required]],
        size: [null, Validators.required],
        clothType: [null, Validators.required]
      }
    );
  }


  get color() {
    return this.stepForm.get('color');
  }

  get size() {
    return this.stepForm.get('size');
  }

  get clothType() {
    return this.stepForm.get('clothType');
  }


  // TODO: On backend option names have to be unique!
  getShirtColorOptions() {
    return this.availableShirtColors.map(c => c.color.name);
  }

  getShirtSizeOptions() {
    return [
      'S',
      'M',
      'L',
      'XL',
      'XXL'
    ];
  }

  getClothTypeOptions() {
    return [
      {id: Gender.FEMALE, name: 'Damska'},
      {id: Gender.MALE, name: 'Męska'}
    ];
  }

}
