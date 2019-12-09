import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Login } from './Bean/login';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ServiceTicketResolutionSystem';
  ServiceEngineer: boolean = false;
  User: boolean = false;
  Admin: boolean = false;
  login: boolean = true;
  user: Login;

  constructor(private router: Router) {
  }

  ngOnInit() {
    this.user = JSON.parse(sessionStorage.getItem("login"))
    if(this.user==null){
      this.login = true;
      this.router.navigate([''])
    }
    else {
      this.getRole(this.user);
    }
  }

  getRole(type: Login) {
    if (type.roles.roleName == "User") {
      this.User = true
      this.ServiceEngineer = false;
      this.Admin = false;
      this.login = false;
    }
    else if (type.roles.roleName == "ServiceEngineer") {
      this.User = false;
      this.ServiceEngineer = true;
      this.Admin = false;
      this.login = false;
    }
    else if (type.roles.roleName == "Admin") {
      this.User = false;
      this.ServiceEngineer = false;
      this.Admin = true;
      this.login = false;
    }
    sessionStorage.setItem("login", JSON.stringify(type))
  }

  logOut() {
    this.login = true
    this.user = null
    this.Admin = false;
    this.ServiceEngineer = false;
    this.User = false;
    sessionStorage.removeItem("login")
    this.router.navigate([''])
  }
}
