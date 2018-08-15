import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CoreModule} from './core/core.module';
import {SemanticUiModule} from './semantic-ui/semantic-ui.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {AboutCampModule} from './about-camp/about-camp.module';
import {CottagesModule} from './cottages/cottages.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule,
    SemanticUiModule,
    FlexLayoutModule,
    AboutCampModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
