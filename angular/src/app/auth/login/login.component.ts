import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from 'src/app/auth.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DialogComponent } from 'src/app/modal/dialog/dialog.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private modalService: NgbModal) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      email: ['', [
        Validators.email, Validators.required
      ]],
      password: ['', [
        Validators.required
      ]]
    })
  }

  onSubmit() {
    if (this.form.valid) {
      this.authService.login(this.form.value).subscribe(resp => {
        if (resp) {
          this.authService.redirectAferLogin();
        } else {
          const modalRef = this.modalService.open(DialogComponent);
          modalRef.componentInstance.title = 'Attention';
          modalRef.componentInstance.message = 'Invalid data';
        }
      });
    }
  }

}
