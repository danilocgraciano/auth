import { NgModule } from '@angular/core';
import { UserComponent } from './user.component';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [

  { path: '', component: UserComponent }
  // { path: 'new', component: UserFormComponent },
  // { path: ':id', component: UserFormComponent },

];


@NgModule({
  exports: [RouterModule],
  imports: [RouterModule.forChild(routes)]
})
export class UserRoutingModule { }
