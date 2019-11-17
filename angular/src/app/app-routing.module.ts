import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AuthGuardService } from './auth.guard.service';
import { LoginComponent } from './auth/login/login.component';

const routes: Routes = [
  {
    path: '', component: HomeComponent, canActivate: [AuthGuardService],
    children: [
      {
        path: 'users',
        loadChildren: () => import('./user/user.module').then(m => m.UserModule)
      }
    ]
    
  },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  exports: [RouterModule],
  imports: [RouterModule.forRoot(routes)]
})
export class AppRoutingModule { }

