import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HeaderComponent} from './component/header/header.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {FooterComponent} from './component/footer/footer.component';
import {SharedModule} from '../shared/shared.module';
import {AppRoutingModule} from '../app-routing.module';

@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent
  ],
  imports: [
    SharedModule,
    AppRoutingModule,
    FlexLayoutModule
  ],
  exports: [
    HeaderComponent,
    FooterComponent
  ]
})
export class CoreModule {
}
