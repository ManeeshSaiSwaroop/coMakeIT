import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { Departments } from '../../Bean/departments';
import { Priorities } from '../../Bean/priorities';
import { UserRestService } from '../../Service/user.service';
import { Router } from '@angular/router';
import { TicketDetails } from '../../Bean/ticket-details';

@Component({
  selector: 'app-raise-ticket',
  templateUrl: './raise-ticket.component.html',
  styleUrls: ['./raise-ticket.component.css']
})
export class RaiseTicketComponent implements OnInit {

  departments: Array<Departments> = [];
  priorities: Array<Priorities> = [];
  ticket: TicketDetails;
  @Output() submit = new EventEmitter<TicketDetails>();

  constructor(private form: FormBuilder, private restCall: UserRestService, private router: Router) { 
    this.restCall.getDepartments().subscribe(
      (dept) => {
        this.departments = dept;
        this.departments.pop();
      }
    ),
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
  

  ticketForm = this.form.group({
    department: ['', Validators.required],
    problemDescription: ['', Validators.required],
    ticketPriority: ['', Validators.required],
    requestedEndDate: ['']
  })

  ngOnInit() {
    
  }

  onSubmit() {
    this.ticket = {
      credentials: JSON.parse(sessionStorage.getItem("login")),
      priorities: {
        priorityID: this.ticketForm.value.ticketPriority,
        priorityName: ""
      },
      details: {
        departments: {
          departmentID: this.ticketForm.value.department,
          departmentName: ""
        }
      },
      requestedEndDate: this.ticketForm.value.requestedEndDate,
      problemDescription: this.ticketForm.value.problemDescription
    }
    console.log(this.ticket);

    this.restCall.submitTicket(this.ticket).subscribe(
      (message) => {
        console.log(message)
        this.router.navigate(["User/userTickets"])
      }
    )
  }

  get department() {
    return this.ticketForm.get('department');
  }

  get problemDescription() {
    return this.ticketForm.get('problemDescription');
  }

  get ticketPriority() {
    return this.ticketForm.get('ticketPriority');
  }

  get requestedEndDate() {
    return this.ticketForm.get('requestedEndDate');
  }

}
