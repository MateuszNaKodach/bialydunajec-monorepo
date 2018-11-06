import {Component} from '@angular/core';
import {SuiLocalizationService} from 'ng2-semantic-ui';

@Component({
  selector: 'bda-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'bialydunajec-angular-frontend';



  constructor(public localizationService: SuiLocalizationService) {
  }

}
