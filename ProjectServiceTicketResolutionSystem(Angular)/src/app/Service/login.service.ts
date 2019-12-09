import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Login } from '../Bean/login';

@Injectable({
  providedIn: 'root'
})
export class CallRESTService {

  constructor(private http: HttpClient) { }

  getData(login: Login): Observable<Login> {
    return this.http.post<Login>("api/Login/validate", login)
  }
}
