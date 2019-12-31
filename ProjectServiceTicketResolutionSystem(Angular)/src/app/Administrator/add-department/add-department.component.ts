import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AdminService } from 'src/app/Service/admin.service';
import { Router } from '@angular/router';
import { Departments } from 'src/app/Bean/departments';

@Component({
  selector: 'app-add-department',
  templateUrl: './add-department.component.html',
  styleUrls: ['./add-department.component.css']
})
export class AddDepartmentComponent implements OnInit {

  constructor(private form: FormBuilder, private restCall: AdminService, private router: Router) { }

  department : Departments

  departmentCreationForm = this.form.group({
    departmentName: ['', Validators.required]
  })

  ngOnInit() {
  }

  onSubmit() {
    this.department = {
      departmentName : this.departmentCreationForm.value.departmentName
    }
    this.restCall.addDepartment(this.department).subscribe(
      (data) => {
        alert(data)
        this.router.navigate(['Admin/viewDepartments'])
      }
      )
  }

  get departmentName() {
    return this.departmentCreationForm.get("departmentName")
  }

}
