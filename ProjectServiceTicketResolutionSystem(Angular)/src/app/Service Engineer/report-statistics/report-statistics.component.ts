import { Component, OnInit } from '@angular/core';
import { ServiceEngineerDetails } from '../../Bean/service-engineer-details';
import { ServiceEngineerService } from '../../Service/service-engineer.service';
import { Router } from '@angular/router';
import { Login } from '../../Bean/login';

@Component({
  selector: 'app-report-statistics',
  templateUrl: './report-statistics.component.html',
  styleUrls: ['./report-statistics.component.css']
})
export class ReportStatisticsComponent implements OnInit {

  reportStatistics : Login[] = [];
  serviceEngineers : Login[] = [];
  statsPerServiceEngineer : Login[] = [];
  k : number;

  constructor(private restCall: ServiceEngineerService, private router: Router) { }

  ngOnInit() {
    this.restCall.getServiceEngineers().subscribe(
      (data) => {
        for (var i: number = 0, j: number = 0; i < data.length; i++) {
          if (data[i].credentials.username!= "DeletedServiceEngineer") {
            this.serviceEngineers.push({'username' : data[i].credentials.username, 'password' : ''})
          }
          else {
            this.k = i;
          }
        }
      }
    )
    this.restCall.getReportStatistics().subscribe(
      (stats) => {
        for (var i: number = 0, j: number = 0; i < stats.length; i++) {
          if (this.k!=i) {
            this.reportStatistics.push({'username' : stats[i], 'password' : ''})
          }
        }
        for(var i=0; i<stats.length-1; i++) {
          this.statsPerServiceEngineer.push({'username' : this.serviceEngineers[i].username, 'password' : this.reportStatistics[i].username})
          if(this.statsPerServiceEngineer[i].password == null) {
            this.statsPerServiceEngineer[i].password = '------';
          }
        }
      }
    )
  }

}
