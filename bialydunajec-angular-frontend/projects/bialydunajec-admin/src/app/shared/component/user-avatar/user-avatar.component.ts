import {Component, Input, OnInit} from '@angular/core';
import {UserViewModel} from '../../../core/component/panel-layout/user.view-model';

@Component({
  selector: 'bda-admin-user-avatar',
  templateUrl: './user-avatar.component.html',
  styleUrls: ['./user-avatar.component.less']
})
export class UserAvatarComponent implements OnInit {

  @Input() tooltipPlacement: 'bottom' | 'none' = 'bottom';
  @Input() showName = true;
  @Input() showAvatar = true;
  @Input() userViewModel: UserViewModel = new UserViewModel('123sda', 'Mateusz', 'Nowak', 'nowak@gmail.com');

  constructor() {
  }

  ngOnInit() {
  }

}
