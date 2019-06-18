import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { StartComponent } from './start/start.component';
import {UserGuard} from './user-guard';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'start', component: StartComponent, canActivate: [UserGuard]},
  {path: '*', redirectTo: 'login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
