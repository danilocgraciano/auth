import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AppRoutingModule } from './app-routing.module';
import { UserModule } from './user/user.module';
import { NavbarComponent } from './navbar/navbar.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from './auth.interceptor';
import { AuthModule } from './auth/auth.module';
import { DialogComponent } from './modal/dialog/dialog.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmDialogComponent } from './modal/confirm-dialog/confirm-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavbarComponent,
    DialogComponent,
    ConfirmDialogComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    UserModule,
    AuthModule,
    NgbModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }],
  entryComponents: [DialogComponent, ConfirmDialogComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
