import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { AdminService } from '../../Service/admin.service';
import { Router } from '@angular/router';
import { Departments } from '../../Bean/departments';
import { Login } from '../../Bean/login';
import { ServiceEngineerDetails } from '../../Bean/service-engineer-details';

@Component({
  selector: 'app-add-service-engineer',
  templateUrl: './add-service-engineer.component.html',
  styleUrls: ['./add-service-engineer.component.css']
})
export class AddServiceEngineerComponent implements OnInit {
  
  departments : Departments[];
  login : Login;
  exists : boolean = true;
  engineer : ServiceEngineerDetails;

  constructor(private form: FormBuilder, private restCall: AdminService, private router: Router) { }

  checkPasswords(group: FormGroup) { // here we have the 'passwords' group
    let pass = group.get('password').value;
    let confirmPass = group.get('confirmPassword').value;

    return pass === confirmPass ? null : { notSame: true } 
}

  serviceEngineerRegistrationForm = this.form.group({
    username : ['', Validators.required],
    password : ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&]).{8,}')]],
    confirmPassword : [''],
    department : ['', Validators.required]
  }, {validator : this.checkPasswords })

  ngOnInit() {
    this.restCall.getDepartments().subscribe(
      (dept) => {
        this.departments = dept;
        this.departments.pop();
      }
    )
  }

  onSubmit() {

    this.login = {
      username : this.serviceEngineerRegistrationForm.value.username,
      password : this.serviceEngineerRegistrationForm.value.password
    }

    this.restCall.registerServiceEngineer(this.login).subscribe(
      (data) => {
        if(data=="Username already exists, try a different one") {
          this.exists = true;
          alert(data);
        }
        else {
          this.exists = false;
        }
        if(!this.exists) {
          this.engineer = {
            credentials : this.login,
            departments : {
              departmentID : this.serviceEngineerRegistrationForm.value.department
            }
          }
          this.restCall.addServiceEnineer(this.engineer).subscribe(
            (data) => {
              alert("service engineer " + this.serviceEngineerRegistrationForm.value.username + " of departmentID " + this.serviceEngineerRegistrationForm.value.department + " has been created successfully");
            }
          )
        }
        this.serviceEngineerRegistrationForm.reset
      }
    )
  }

  get username() {
    return this.serviceEngineerRegistrationForm.get("username");
  }

  get password() {
    return this.serviceEngineerRegistrationForm.get("password");
  }

  get confirmPassword() {
    return this.serviceEngineerRegistrationForm.get("confirmPassword");
  }

  get department() {
    return this.serviceEngineerRegistrationForm.get("department");
  }
}
