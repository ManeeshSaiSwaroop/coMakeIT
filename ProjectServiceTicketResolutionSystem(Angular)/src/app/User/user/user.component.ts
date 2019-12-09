import { Component, OnInit, Input } from '@angular/core';
import { UserRestService } from '../../Service/user.service';
import { Router } from '@angular/router';
import { Login } from '../../Bean/login';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  constructor(private restCall: UserRestService, private router : Router) { }

  ngOnInit() {
  }

}
