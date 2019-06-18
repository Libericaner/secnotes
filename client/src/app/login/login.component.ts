import { Component, OnInit } from '@angular/core';
import {UserService} from '../../services/UserService';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;

  constructor(private fb: FormBuilder,
              private readonly userService: UserService,
              private readonly router: Router) { }

  ngOnInit() {
    this.form = this.fb.group({
      'username': [''],
      'password': ['']
    });
  }

  login(event) {
    this.userService.login(this.form.value);
    this.router.navigateByUrl('start');
  }
}
