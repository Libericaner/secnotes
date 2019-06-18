import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { StartComponent } from './start/start.component';
import { NoteDialogComponent } from './note-dialog/note-dialog.component';
import { NoteCreateDialogComponent } from './note-create-dialog/note-create-dialog.component';
import {CdkTableModule} from '@angular/cdk/table';
import { NoteDatasource } from 'src/datasources/NoteDatasource';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    StartComponent,
    NoteDialogComponent,
    NoteCreateDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CdkTableModule
  ],
  providers: [NoteDatasource],
  bootstrap: [AppComponent]
})
export class AppModule { }
