import {NgModule} from '@angular/core';
import {CommonModule, registerLocaleData} from '@angular/common';
import {NgZorroAntdModule, NZ_I18N, pl_PL} from 'ng-zorro-antd';
import pl from '@angular/common/locales/pl';
import {FormInputComponent} from './component/form-input/form-input.component';
import {BialyDunajecCommonsModule} from 'bialydunajec-commons';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

registerLocaleData(pl);

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgZorroAntdModule,
    BialyDunajecCommonsModule
  ],
  declarations: [
    FormInputComponent
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgZorroAntdModule,
    FormInputComponent,
    BialyDunajecCommonsModule
  ],
  providers: [{provide: NZ_I18N, useValue: pl_PL}]
})
export class SharedModule {
}
