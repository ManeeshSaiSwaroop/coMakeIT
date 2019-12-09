import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Login } from '../Bean/login';
import { Observable } from 'rxjs';
import { Departments } from '../Bean/departments';
import { ServiceEngineerDetails } from '../Bean/service-engineer-details';
import { Role } from '../Bean/role';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient, private router: Router) { }

  addUser(login : Login) {
    return this.http.post("api/admin/registerUser", login, {responseType:'text'});
  }

  getDepartments(): Observable<Array<Departments>> {
    return this.http.get<Array<Departments>>("api/user/departments");
  }

  registerServiceEngineer(login : Login) {
    return this.http.post("api/admin/registerServiceEngineer1", login, {responseType : 'text'});
  }

  addServiceEnineer(engineer : ServiceEngineerDetails) {
    return this.http.post("api/admin/registerServiceEngineer2", engineer, {responseType: 'text'});
  }

  getRoles() : Observable<Array<Role>> {
    return this.http.get<Array<Role>>("api/admin/roles");
  }

  getUsers() : Observable<Array<Login>> {
    return this.http.get<Array<Login>>("api/admin/users");
  }

  getServiceEngineers() : Observable<Array<ServiceEngineerDetails>> {
    return this.http.get<Array<ServiceEngineerDetails>>("api/admin/serviceEngineers");
  }

  deleteUser(user : Login) {
    return this.http.delete("api/admin/deleteUser/credentials/" + user.username, {responseType : 'text'});
  }

  deleteServiceEngineer(serviceEngineer : ServiceEngineerDetails) {
    return this.http.delete("api/admin/deleteServiceEngineer/serviceEngineerDetails/" + serviceEngineer.id, {responseType : 'text'});
  }

  addDepartment(department : Departments) {
    return this.http.post("api/admin/addDepartment", department, {responseType : 'text'});
  }

  canActivate() {
    if(JSON.parse(sessionStorage.getItem("login")).roles.roleName == "Admin") {
    return true;
    }
    else {
      this.router.navigate([JSON.parse(sessionStorage.getItem("login")).roles.roleName]);
    }
  }
}
