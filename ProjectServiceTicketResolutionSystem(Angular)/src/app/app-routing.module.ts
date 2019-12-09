import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserComponent } from './User/user/user.component';
import { AppComponent } from './app.component';
import { ServiceEngineerComponent } from './Service Engineer/service-engineer/service-engineer.component';
import { RaiseTicketComponent } from './User/raise-ticket/raise-ticket.component';
import { UserTicketsComponent } from './User/user-tickets/user-tickets.component';
import { ServiceEngineerTicketsComponent } from './Service Engineer/service-engineer-tickets/service-engineer-tickets.component';
import { AverageSeverityComponent } from './Service Engineer/average-severity/average-severity.component';
import { AgingComponent } from './Service Engineer/aging/aging.component';
import { ReportStatisticsComponent } from './Service Engineer/report-statistics/report-statistics.component';
import { UpdatePriorityComponent } from './Service Engineer/update-priority/update-priority.component';
import { AddUserComponent } from './Administrator/add-user/add-user.component';
import { AddServiceEngineerComponent } from './Administrator/add-service-engineer/add-service-engineer.component';
import { ViewUsersComponent } from './Administrator/view-users/view-users.component';
import { ViewDepartmentsComponent } from './Administrator/view-departments/view-departments.component';
import { AdminComponent } from './Administrator/admin/admin.component';
import { LoginComponent } from './login/login.component';
import { ServiceEngineerService } from './Service/service-engineer.service';
import { AdminService } from './Service/admin.service';
import { UserRestService } from './Service/user.service';
import { PageNotFoundComponent } from './Error/page-not-found/page-not-found.component';
import { AddDepartmentComponent } from './Administrator/add-department/add-department.component';


const routes: Routes = [
  {
    path: "User", component: UserComponent, canActivate: [UserRestService], children: [
      {
        path: "userTickets", component: UserTicketsComponent
      },
      {
        path: "", component: RaiseTicketComponent
      },
      {
        path: "raiseTicket", component: RaiseTicketComponent
      }
    ]
  },
  {
    path: "ServiceEngineer", component: ServiceEngineerComponent, canActivate: [ServiceEngineerService], children: [
      {
        path: "serviceEngineerTickets", component: ServiceEngineerTicketsComponent
      },
      { path: "", component: ServiceEngineerTicketsComponent },
      {
        path: "averageSeverity", component: AverageSeverityComponent
      },
      {
        path: "reportStatistics", component: ReportStatisticsComponent
      },
      {
        path: "aging", component: AgingComponent
      },
      {
        path: "updatePriority", component: UpdatePriorityComponent
      },
    ]
  },
  {
    path: "Admin", component: AdminComponent, canActivate: [AdminService], children: [
      {
        path: "addUser", component: AddUserComponent
      },
      { path: "", component: AddUserComponent },
      {
        path: "addServiceEngineer", component: AddServiceEngineerComponent
      },
      {
        path: "viewUsers", component: ViewUsersComponent
      },
      {
        path: "viewDepartments", component: ViewDepartmentsComponent
      },
      {
        path: "addDepartment", component: AddDepartmentComponent
      },
    ]
  },
  {
    path: "**", component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
