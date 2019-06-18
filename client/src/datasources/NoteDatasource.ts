import {DataSource, CollectionViewer} from '@angular/cdk/collections';
import {Note} from '../domain/domain';
import {NoteService} from '../services/NoteService';
import {Observable, Subject} from 'rxjs';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NoteDatasource implements DataSource<Note> {

  private notesSubject = new Subject<Note[]>();
  notes: Observable<Note[]> = this.notesSubject.asObservable();

  private noteSubject = new Subject<Note>();
  note: Observable<Note> = this.noteSubject.asObservable();

  constructor(private readonly noteService: NoteService) {
  }

  connect(collectionViewer: CollectionViewer): Observable<Note[]> {
    return this.notes;
  }

  disconnect(): void {
    this.notesSubject.complete();
    this.noteSubject.complete();
  }

  loadNotes() {
    this.noteService.getAllNotesByUser().subscribe(notes => {
      this.notesSubject.next(notes);
    });
  }

  loadNote(id: string) {
    this.noteService.getOneNote(id).subscribe(note => {
      this.noteSubject.next(note);
    });
  }
}
