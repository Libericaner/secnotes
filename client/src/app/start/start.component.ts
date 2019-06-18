import { Component, OnInit } from '@angular/core';
import {NoteService} from '../../services/NoteService';
import {Note} from '../../domain/domain';
import {MatDialog} from '@angular/material';
import {NoteDialogComponent} from '../note-dialog/note-dialog.component';
import {take} from 'rxjs/operators';

@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.css']
})
export class StartComponent implements OnInit {

  notes: Note[];

  constructor(private readonly noteService: NoteService,
              private dialog: MatDialog) { }

  ngOnInit() {
    this.refresh();
  }

  refresh() {
    this.noteService.getAllNotesByUser().subscribe(notes => this.notes = notes);
  }

  delete(note: Note) {
    this.noteService.deleteNote(note.id).subscribe(res => this.refresh());
  }

  openDialog(note: Note) {
    const dialogRef = this.dialog.open(NoteDialogComponent, {data: {note: note, mode: 'edit'}});
    dialogRef.afterClosed().pipe(take(1)).subscribe(res => this.refresh());
  }

  createDialog() {
    const dialogRef = this.dialog.open(NoteDialogComponent, {data: {mode: 'create'}});
    dialogRef.afterClosed().pipe(take(1)).subscribe(res => this.refresh());
  }
}
