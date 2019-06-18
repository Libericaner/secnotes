import {Observable, of} from 'rxjs';
import {environment} from '../environments/environment';
import {HttpClient} from '@angular/common/http';

export class UserService {

  private env = environment;

  constructor(private readonly http: HttpClient) {
  }

  public login(username: string, password: string): Observable<any> {

    return of({username: username, password: password});
  }
}
