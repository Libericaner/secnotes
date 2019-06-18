import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../services/UserService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private readonly router: Router,
              private readonly userService: UserService) {}

  gotoStart() {
    this.router.navigateByUrl('start');
  }

  logout() {
    this.userService.logout();
  }

  gotoLogin() {
    this.logout();
  }
}
