import {Observable} from 'rxjs';
import {Note} from '../domain/domain';
import {HttpClient} from '@angular/common/http';
import {environment} from 'src/environments/environment';
import {take} from 'rxjs/operators';

export class NoteService {

  private env = environment;

  constructor(private readonly http: HttpClient) {
  }

  public getAllNotesByUser(): Observable<Note[]> {

    const url = this.env.SERVER_HOST + this.env.NOTES_API;
    return this.http.get<Note[]>(url).pipe(take(1));
  }

  public getOneNote(id: string): Observable<Note> {

    const url = this.env.SERVER_HOST + this.env.NOTE_API + '/' + id;
    return this.http.get<Note>(url).pipe(take(1));
  }

  public createNote(note: Note): Observable<Note> {

    const url = this.env.SERVER_HOST + this.env.NOTE_API;
    return this.http.post<Note>(url, note).pipe(take(1));
  }

  public editNote(note: Note): Observable<any> {

    const url = this.env.SERVER_HOST + this.env.NOTE_API;
    return this.http.put(url, note).pipe(take(1));
  }

  public deleteNote(id: string): Observable<any> {

    const url = this.env.SERVER_HOST + this.env.NOTE_API + '/' + id;
    return this.http.delete(url).pipe(take(1));
  }
}
