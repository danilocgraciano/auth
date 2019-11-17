import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  redirectUrl: string = '';
  keyToken: string = 'authToken';

  constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient) { }

  login(credentials) {

    this.route.queryParams.subscribe(params => this.redirectUrl = params['return'] || '');

    return this.http.post('/api/auth/login', JSON.stringify(credentials),
      { headers: { 'Content-Type': 'application/json' } })
      .pipe(
        tap(res => {
          this.setSession(res);
        })
      );

  }

  redirectAferLogin() {
    this.router.navigate([this.redirectUrl]);
  }

  logout(): void {
    localStorage.removeItem(this.keyToken);
    this.router.navigate(['login']);
  }

  private setSession(data) {
    if (data) {
      localStorage.setItem(this.keyToken, data.token);
    }
  }

  public getToken(): string {
    return localStorage.getItem(this.keyToken);
  }

  public isLoggedIn(): boolean {
    return this.getToken() != null;
  }

}
