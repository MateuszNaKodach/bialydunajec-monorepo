import {Component} from '@angular/core';
import {SuiLocalizationService} from 'ng2-semantic-ui';

@Component({
  selector: 'bda-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'bialydunajec-angular-frontend';

  private _opened = false;


  constructor(public localizationService: SuiLocalizationService) {
  }

  toggleOpened(): void {
    this._opened = !this._opened;
  }

  get opened(): boolean {
    return this._opened;
  }
}
