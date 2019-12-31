import { Component, OnInit } from '@angular/core';
import { ServiceEngineerService } from '../../Service/service-engineer.service';
import { Router } from '@angular/router';
import { Login } from '../../Bean/login';

@Component({
  selector: 'app-aging',
  templateUrl: './aging.component.html',
  styleUrls: ['./aging.component.css']
})
export class AgingComponent implements OnInit {

  constructor(private restCall: ServiceEngineerService, private router: Router) { }

  ageListOfOpenTickets : Array<Object[]> = [];
  user : Login;

  ngOnInit() {
    this.user = JSON.parse(sessionStorage.getItem("login"))
    
    this.restCall.getAgeOfOpenTickets(this.user).subscribe(
      (data) => {
        this.ageListOfOpenTickets = data;
      }
    )
  }

}
