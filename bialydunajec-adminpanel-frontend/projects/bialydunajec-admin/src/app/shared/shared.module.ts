import {NgModule} from '@angular/core';
import {CommonModule, registerLocaleData} from '@angular/common';
import {NgZorroAntdModule, NZ_I18N, pl_PL} from 'ng-zorro-antd';
import pl from '@angular/common/locales/pl';
import {FormInputComponent} from './component/form-input/form-input.component';

registerLocaleData(pl);

@NgModule({
  imports: [
    CommonModule,
    NgZorroAntdModule
  ],
  declarations: [
    FormInputComponent
  ],
  exports: [
    NgZorroAntdModule,
    FormInputComponent
  ],
  providers: [{provide: NZ_I18N, useValue: pl_PL}]
})
export class SharedModule {
}
