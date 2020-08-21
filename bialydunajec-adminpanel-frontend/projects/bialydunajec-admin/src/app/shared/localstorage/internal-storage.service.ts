import {Injectable} from '@angular/core';

const SELECTED_CAMP_EDITION_ID = 'org.bialydunajec.selected_camp_edition_id';

@Injectable({
  providedIn: 'root'
})
export class InternalStorage {

  storeSelectedCampEditionId(campEditionId: number) {
    localStorage.setItem(SELECTED_CAMP_EDITION_ID, campEditionId.toString());
  }

  get selectedCampEditionId(): number {
    const storedId = localStorage.getItem(SELECTED_CAMP_EDITION_ID);
    return storedId ? parseInt(storedId, null) : null;
  }

}
