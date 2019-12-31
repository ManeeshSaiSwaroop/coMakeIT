import { Component, OnInit } from '@angular/core';
import { ServiceEngineerService } from '../../Service/service-engineer.service';
import { Router } from '@angular/router';
import { Priorities } from '../../Bean/priorities';

@Component({
  selector: 'app-average-severity',
  templateUrl: './average-severity.component.html',
  styleUrls: ['./average-severity.component.css']
})
export class AverageSeverityComponent implements OnInit {

  averageSeverity: String[];
  priorities: Array<Priorities> = [];

  constructor(private restCall: ServiceEngineerService, private router: Router) { }

  ngOnInit() {
    this.restCall.getAverageSeverity().subscribe(
      (data) => {
        this.averageSeverity = data;
      }
    )
    this.restCall.getPriorities().subscribe(
      (pr) => {
        for (var i: number = 0, j: number = 0; i < pr.length; i++) {
          if (pr[i].priorityID != 0) {
            this.priorities.push({ 'priorityID': pr[i].priorityID, 'priorityName': pr[i].priorityName })
          }
        }
      }
    )
  }

}
