import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../Service/admin.service';
import { Router } from '@angular/router';
import { Departments } from '../../Bean/departments';

@Component({
  selector: 'app-view-departments',
  templateUrl: './view-departments.component.html',
  styleUrls: ['./view-departments.component.css']
})
export class ViewDepartmentsComponent implements OnInit {

  dept : Departments[] = []

  constructor(private restCall: AdminService, private router: Router) { }

  ngOnInit() {
    this.restCall.getDepartments().subscribe(
      (data) => {
        for(var i=0; i<data.length; i++) {
          if(data[i].departmentName == "NoDepartment")
          {

          }
          else{
            this.dept.push({departmentID : data[i].departmentID, departmentName : data[i].departmentName})
          }
        }
      }
    )
  }

  addDepartment() {
    this.router.navigate(['Admin/addDepartment']);
  }

  deleteDepartment() {
    alert("Functionality to be added")
  }

}
