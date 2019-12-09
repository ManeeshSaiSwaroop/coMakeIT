import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AdminService } from '../../Service/admin.service';
import { Router } from '@angular/router';
import { Role } from '../../Bean/role';

@Component({
  selector: 'app-view-users',
  templateUrl: './view-users.component.html',
  styleUrls: ['./view-users.component.css']
})
export class ViewUsersComponent implements OnInit {

  constructor(private form: FormBuilder, private restCall: AdminService, private router: Router) { }

  roles : Array<Role> = []

  userTypeGroup = this.form.group({
    userType : ['', Validators.required]
  })

  ngOnInit() {
    this.restCall.getRoles().subscribe(
      (data) => {
        for(var i=0; i<data.length; i++) {
          if(data[i].roleName == "Admin")
          {

          }
          else {
            this.roles.push({roleID : data[i].roleID, roleName : data[i].roleName})
          }
        }
      }
    )
  }

  get userType() {
    return this.userTypeGroup.get("userType");
  }

}
