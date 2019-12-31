import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { CallRESTService } from '../Service/login.service';
import { Router } from '@angular/router';
import { Login } from '../Bean/login';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @Output() getType = new EventEmitter<Login>();

  
  wrongCredentials: boolean = false
  constructor(private form: FormBuilder, private restCall: CallRESTService, private router: Router) { }

  loginForm = this.form.group({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  });

  onSubmit() {
    this.restCall.getData(this.loginForm.value).subscribe(
      (Login) => {
        this.wrongCredentials = false
        this.getType.emit(Login)
        this.router.navigate([Login.roles.roleName])
      },
      (error) => {
        this.wrongCredentials = true
      }
    )
  }

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

  ngOnInit() {
  }

}
