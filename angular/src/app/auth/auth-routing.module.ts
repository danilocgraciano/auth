import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';

const routes: Routes = [

  { path: 'login', component: LoginComponent}
  // { path: 'new', component: SistemaFormComponent },
  // { path: ':id', component: SistemaFormComponent },

];

@NgModule({
  exports: [RouterModule],
  imports: [RouterModule.forChild(routes)]
})
export class AuthRoutingModule { }
