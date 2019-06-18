import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {LoggedInUser, UserService} from '../services/UserService';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationInterceptor implements HttpInterceptor {

  constructor(private readonly userService: UserService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    let currentUser: LoggedInUser;
    currentUser = this.userService.getCurrentUser();

    if (currentUser && currentUser.token) {
      req = req.clone({
        setHeaders: {
          Authorization: currentUser.token
        }
      });
    }

    return next.handle(req);
  }
}
