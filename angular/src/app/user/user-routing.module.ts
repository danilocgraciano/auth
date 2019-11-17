import { NgModule } from '@angular/core';
import { UserComponent } from './user.component';
import { Routes, RouterModule } from '@angular/router';
import { GuardService } from '../guard.service';

const routes: Routes = [

  { path: '', component: UserComponent, canActivate: [GuardService] }
  // { path: 'new', component: SistemaFormComponent },
  // { path: ':id', component: SistemaFormComponent },

];


@NgModule({
  exports: [RouterModule],
  imports: [RouterModule.forChild(routes)]
})
export class UserRoutingModule { }
