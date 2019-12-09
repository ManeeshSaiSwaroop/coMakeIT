import { Component, OnInit } from '@angular/core';
import { ServiceEngineerService } from '../../Service/service-engineer.service';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { TicketDetails } from '../../Bean/ticket-details';
import { Priorities } from '../../Bean/priorities';
import { error } from 'util';

@Component({
  selector: 'app-update-priority',
  templateUrl: './update-priority.component.html',
  styleUrls: ['./update-priority.component.css']
})
export class UpdatePriorityComponent implements OnInit {

  constructor(private form: FormBuilder, private restCall: ServiceEngineerService, private router: Router) { }

  ticket : TicketDetails;
  priorities: Array<Priorities> = [];

  ngOnInit() {
    this.restCall.getPriorities().subscribe(
      (pr) => {
        for (var i: number = 0, j: number = 0; i < pr.length; i++) {
          if (pr[i].priorityID != 0) {
            this.priorities.push({ 'priorityID': pr[i].priorityID, 'priorityName': pr[i].priorityName })
          }
        }
      }
    )
  }

  updatePriorityForm = this.form.group({
    ticketID : ['', Validators.required],
    priority : ['', Validators.required]
  })

  onSubmit() {
    this.ticket = {
      ticketID : this.updatePriorityForm.value.ticketID,
      priorities : {
        priorityID : this.updatePriorityForm.value.priority
      }
    }
    console.log(this.ticket);

    this.restCall.changePriority(this.ticket).subscribe(
      (message) => {
        alert("Priority of the mentioned ticket has been updated")
        this.router.navigate(["serviceEngineerTickets"]);
      },
      (error) => {
        alert("Please enter a valid Ticket ID")
      }
    )
  }

  get ticketID() {
    return this.updatePriorityForm.get("ticketID");
  }

  get priority() {
    return this.updatePriorityForm.get("priority");
  }

}
