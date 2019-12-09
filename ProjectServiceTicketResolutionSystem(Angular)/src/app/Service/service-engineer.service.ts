import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Priorities } from '../Bean/priorities';
import { ServiceEngineerDetails } from '../Bean/service-engineer-details';
import { Login } from '../Bean/login';
import { TicketDetails } from '../Bean/ticket-details';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ServiceEngineerService {

  constructor(private http: HttpClient, private router : Router) { }

  getPriorities(): Observable<Priorities[]> {
    return this.http.get<Priorities[]>("api/user/priorities");
  }

  getAverageSeverity() : Observable<String[]> {
    return this.http.get<String[]>("api/serviceEngineer/averageSeverity");
  }

  getReportStatistics() : Observable<string[]> {
    return this.http.get<string[]>("api/serviceEngineer/reportStatistics");
  }

  getServiceEngineers() : Observable<ServiceEngineerDetails[]> {
    return this.http.get<ServiceEngineerDetails[]>("api/serviceEngineer/serviceEngineers");
  }

  getAgeOfOpenTickets(user : Login) : Observable<Array<Object[]>> {
    return this.http.post<Array<Object[]>>("api/serviceEngineer/aging", user);
  }

  getServiceEngineerTickets(user : Login) : Observable<TicketDetails[]> {
    return this.http.post<TicketDetails[]>("api/serviceEngineer/tickets", user);
  } 

  resolveTicket(user : Login) {
    return this.http.put("api/serviceEngineer/resolveTicket", user, {responseType:'text'});
  } 

  changePriority(ticket : TicketDetails) {
    return this.http.put("api/serviceEngineer/updatePriority", ticket, {responseType : 'text'})
  }

  canActivate() {
    if(JSON.parse(sessionStorage.getItem("login")).roles.roleName == "ServiceEngineer") {
      return true;
    }
    else {
      this.router.navigate([JSON.parse(sessionStorage.getItem("login")).roles.roleName]);
    }
  }
}
