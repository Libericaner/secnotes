import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Note} from '../../domain/domain';
import {NoteService} from '../../services/NoteService';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-note-dialog',
  templateUrl: './note-dialog.component.html',
  styleUrls: ['./note-dialog.component.css']
})
export class NoteDialogComponent implements OnInit {

  note: Note;
  form: FormGroup;

  constructor(private dialogRef: MatDialogRef<NoteDialogComponent>,
              private noteService: NoteService,
              private fb: FormBuilder,
              @Inject(MAT_DIALOG_DATA) private data: any) { }

  ngOnInit() {
    this.form = this.fb.group({'title': [''], 'note': ['']});
    if (this.isCreateMode()) {
      this.note = {note: '', title: ''};
    } else {
      this.noteService.getOneNote(this.data.note.id).subscribe(n => { this.note = n; this.form.patchValue(n); });
    }
  }

  isCreateMode(): boolean {
    return this.data.mode === 'create';
  }

  save() {

    this.note.title = this.form.value.title;
    this.note.note = this.form.value.note;

    if (this.isCreateMode()) {

      this.noteService.createNote(this.note)
        .subscribe(n => this.note = n,
            err => console.log('could not create new note: ' + err));
      this.dialogRef.close();
    } else {

      this.noteService.editNote(this.note).subscribe();
      this.dialogRef.close();
    }
  }
}
