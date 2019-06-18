import {environment} from '../environments/environment';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private env = environment;

  private currentUser: LoggedInUser;
  private localStorageCurrentUserKey = 'access_token';
  private authorizationHeader = 'Authorization';

  constructor(private readonly http: HttpClient,
              private readonly router: Router) {}

  login(login: LoginUser) {
    const loginApi = this.env.SERVER_HOST + this.env.LOGIN_API;
    const post = this.http.post(loginApi, login, {observe: 'response'}).subscribe((response: HttpResponse<any>) => {
      const token = response.headers.get(this.authorizationHeader);
      const u = this.getUserFromToken(token);
      if (u === null || u === undefined) {
        console.log('invalid jwt... logging out');
        this.logout();
      } else {
        localStorage.setItem(this.localStorageCurrentUserKey, u.token);
        this.router.navigateByUrl('start');
      }
    });
  }

  logout() {
    localStorage.removeItem(this.localStorageCurrentUserKey);
    this.currentUser = null;
    this.router.navigateByUrl('login');
  }

  getCurrentUser(): LoggedInUser {
    if (this.currentUser) {
      return this.currentUser;
    }

    const localToken = localStorage.getItem(this.localStorageCurrentUserKey);
    if (localToken) {
      this.currentUser = this.getUserFromToken(localToken);
      return this.currentUser;
    }

    return null;
  }

  userIsLoggedIn(): boolean {
    return this.getCurrentUser() != null;
  }

  private getUserFromToken(t: string): LoggedInUser {

    const jwtHelper = new JwtHelperService();

    let decoded;
    decoded = jwtHelper.decodeToken(t);

    console.log(decoded);

    if (decoded != null && decoded.sub) {
      return new LoggedInUser(t, decoded.sub);
    }
    return null;
  }


}

export class LoggedInUser {
  token: string;
  username: string;

  constructor(token: string, username: string) {

    this.token = token;
    this.username = username;
  }
}

export class LoginUser {
  username: string;
  password: string;

  constructor(username: string, password: string) {
    this.password = password;
    this.username = username;
  }
}

