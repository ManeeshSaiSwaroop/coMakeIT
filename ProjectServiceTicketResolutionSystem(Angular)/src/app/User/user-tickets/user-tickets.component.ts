import { Component, OnInit } from '@angular/core';
import { UserRestService } from '../../Service/user.service';
import { Router } from '@angular/router';
import { TicketDetails } from '../../Bean/ticket-details';

@Component({
  selector: 'app-user-tickets',
  templateUrl: './user-tickets.component.html',
  styleUrls: ['./user-tickets.component.css']
})
export class UserTicketsComponent implements OnInit {

  userTix: Array<TicketDetails> = [];
  holder: TicketDetails;

  constructor(private restCall: UserRestService, private router: Router) { }

  ngOnInit() {
    this.holder = {
      credentials: JSON.parse(sessionStorage.getItem("login"))
    }
    this.restCall.getTickets(this.holder).subscribe(
      (tix) => {
        this.userTix = tix;
        console.log();
        
      }
    )
  }

}
