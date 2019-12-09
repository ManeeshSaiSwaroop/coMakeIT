import { Component, OnInit, Input, SimpleChanges } from '@angular/core';
import { AdminService } from '../../Service/admin.service';
import { Router } from '@angular/router';
import { Login } from '../../Bean/login';
import { ServiceEngineerDetails } from '../../Bean/service-engineer-details';
import { LoginComponent } from '../../login/login.component';
import { error } from 'util';

@Component({
  selector: 'app-display-users',
  templateUrl: './display-users.component.html',
  styleUrls: ['./display-users.component.css']
})
export class DisplayUsersComponent implements OnInit {

@Input() roleID : number;
users = false;
engineers = false;
userList : Login[] = [];
serviceEngineerList : ServiceEngineerDetails[] = [];

  constructor(private restCall: AdminService, private router: Router) { }

  ngOnChanges() {
    if(this.roleID == 1) {
      this.users = true;
      this.engineers = false;
      this.restCall.getUsers().subscribe(
        (data) => {
          for(var i=0; i<data.length; i++) {
            if(data[i].username == "Deleted")
            {

            }
            else {
              this.userList.push({username : data[i].username, password: data[i].password});
            }
          }
        }
      )
      this.serviceEngineerList = [];
    }
    else if(this.roleID == 2) {
      this.users = false;
      this.engineers = true;
      this.restCall.getServiceEngineers().subscribe(
        (data) => {
          console.log(data);
          
          for(var i=0; i<data.length; i++) {
            if(data[i].credentials.username == "DeletedServiceEngineer") {
            }
            else {
              this.serviceEngineerList.push({
                id : data[i].id,
                credentials : {
                  username : data[i].credentials.username,
                  password : data[i].credentials.password
                },
                departments : {
                  departmentID : data[i].departments.departmentID,
                  departmentName : data[i].departments.departmentName
                },
                totalTicketsWorkedOn : data[i].totalTicketsWorkedOn,
                currentHighPriorityTicketID : data[i].currentHighPriorityTicketID,
                priorities : {
                  priorityID : data[i].priorities.priorityID,
                  priorityName : data[i].priorities.priorityName
                }
              })
            }
          }
        }
      )
      this.userList = [];
    }
  }

  ngOnInit() {
  }

  deleteUser(user : Login) {
    this.restCall.deleteUser(user).subscribe(
      (data) => {
        alert(user.username + " account has been deleted")
        this.userList = []
        this.ngOnChanges();
      },
      (error) => {
        alert("Some error has occurred, please try again after some time")
      }
    )
  }

  deleteServiceEngineer(engineer : ServiceEngineerDetails) {
    this.restCall.deleteServiceEngineer(engineer).subscribe(
      (data) => {
        alert(engineer.id + " has been deleted")
        this.serviceEngineerList = []
        this.ngOnChanges();
      },
      (error) => {
        alert("Service Engineer " + engineer.id + " account cannot be deleted either because he is working on a ticket or he is the only service engineer in that department")
      }
    )
  }

}
