import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../service/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'bda-admin-current-user',
  templateUrl: './current-user.component.html',
  styleUrls: ['./current-user.component.less']
})
export class CurrentUserComponent implements OnInit {

  currentUser: { userId: string; username: string; emailAddress: string };

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
    this.currentUser = this.authService.currentUser;
  }

  onClickLogout() {
    this.authService.logoutCurrentUser();
    this.router.navigate(['/']);
  }

}
