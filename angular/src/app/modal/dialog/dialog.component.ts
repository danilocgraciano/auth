import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent {
  
  title: string;
  message: string;

  constructor(public activeModal: NgbActiveModal) { }

  close() {
    this.activeModal.close();
  }

}
