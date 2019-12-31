import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup, AbstractControl } from '@angular/forms';
import { AdminService } from '../../Service/admin.service';
import { Router } from '@angular/router';
import { Login } from '../../Bean/login';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  constructor(private form: FormBuilder, private restCall: AdminService, private router: Router) { }

  login : Login;

  userRegistrationForm = this.form.group({
    username : ['', Validators.required],
    password : ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}')]],
    confirmPassword : ['']
  }, {validator : this.checkPasswords })

  checkPasswords(group: FormGroup) { // here we have the 'passwords' group
  let pass = group.get('password').value;
  let confirmPass = group.get('confirmPassword').value;

  return pass === confirmPass ? null : { notSame: true }     
}

  ngOnInit() {
  }

  onSubmit() {

    this.login = {
      username : this.userRegistrationForm.value.username,
      password : this.userRegistrationForm.value.password
    }

    this.restCall.addUser(this.login).subscribe(
      (data) => {
        alert(data)
      }
    )
  }

  get username() {
    return this.userRegistrationForm.get("username");
  }

  get password() {
    return this.userRegistrationForm.get("password");
  }

  get confirmPassword() {
    return this.userRegistrationForm.get("confirmPassword");
  }

}
