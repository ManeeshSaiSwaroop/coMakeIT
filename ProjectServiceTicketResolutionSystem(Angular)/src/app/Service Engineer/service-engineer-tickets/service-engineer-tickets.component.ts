import { Component, OnInit, OnChanges } from '@angular/core';
import { ServiceEngineerService } from '../../Service/service-engineer.service';
import { Router } from '@angular/router';
import { TicketDetails } from '../../Bean/ticket-details';
import { error } from 'util';

@Component({
  selector: 'app-service-engineer-tickets',
  templateUrl: './service-engineer-tickets.component.html',
  styleUrls: ['./service-engineer-tickets.component.css']
})
export class ServiceEngineerTicketsComponent {

  constructor(private restCall: ServiceEngineerService, private router: Router) { }

  serviceEngineerTickets: TicketDetails[];

  ngOnInit() {
    this.restCall.getServiceEngineerTickets(JSON.parse(sessionStorage.getItem("login"))).subscribe(
      (data) => {
        console.log(data)
        this.serviceEngineerTickets = data;
      }
    )
  }

  ngOnChanges() {
    this.restCall.getServiceEngineerTickets(JSON.parse(sessionStorage.getItem("login"))).subscribe(
      (data) => {
        this.serviceEngineerTickets = data;
      }
    )
  }

  resolveTicket() {
    this.restCall.resolveTicket(JSON.parse(sessionStorage.getItem("login"))).subscribe(
      (data) => {
        alert("Ticket has been closed")
        this.ngOnInit();
      },
      (error) => {
        alert("All the tickets assigned to you have been closed already")
      }
    )
  }

  changePriority() {
    this.router.navigate(["ServiceEngineer/updatePriority"]);
  }

}
