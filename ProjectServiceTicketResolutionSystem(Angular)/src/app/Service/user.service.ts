import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TicketDetails } from '../Bean/ticket-details';
import { Departments } from '../Bean/departments';
import { Priorities } from '../Bean/priorities';
import { Login } from '../Bean/login';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserRestService {

  constructor(private http: HttpClient, private router: Router) { }

  getTickets(username: TicketDetails): Observable<Array<TicketDetails>> {
    return this.http.post<Array<TicketDetails>>("api/user/tickets", username);
  }

  getDepartments(): Observable<Array<Departments>> {
    return this.http.get<Array<Departments>>("api/user/departments");
  }

  getPriorities(): Observable<Priorities[]> {
    return this.http.get<Priorities[]>("api/user/priorities");
  }

  submitTicket(form: TicketDetails): Observable<Login> {
    return this.http.post<Login>("api/user/assignTicket", form);
  }

  canActivate() {
    if (JSON.parse(sessionStorage.getItem("login")).roles.roleName == "User") {
      return true;
    }
    else {
      this.router.navigate([JSON.parse(sessionStorage.getItem("login")).roles.roleName]);
    }
  }
}