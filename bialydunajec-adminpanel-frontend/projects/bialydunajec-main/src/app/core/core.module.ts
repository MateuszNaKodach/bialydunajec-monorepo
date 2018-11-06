import {NgModule} from '@angular/core';
import {HeaderComponent} from './component/header/header.component';
import {FooterComponent} from './component/footer/footer.component';
import {SharedModule} from '../shared/shared.module';
import {AppRoutingModule} from '../app-routing.module';
import { NotFoundComponent } from './component/not-found/not-found.component';
import {SidebarModule} from 'ng-sidebar';
import {SuiSidebarModule} from 'ng2-semantic-ui';

@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    NotFoundComponent
  ],
  imports: [
    SharedModule,
    AppRoutingModule,
    SidebarModule.forRoot(),
    SuiSidebarModule
  ],
  exports: [
    HeaderComponent,
    FooterComponent,
    NotFoundComponent,
    SidebarModule,
    SuiSidebarModule
  ]
})
export class CoreModule {
}
