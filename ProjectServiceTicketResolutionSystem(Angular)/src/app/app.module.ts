import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ServiceEngineerComponent } from './Service Engineer/service-engineer/service-engineer.component';
import { UserComponent } from './User/user/user.component';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { AdminComponent } from './Administrator/admin/admin.component';
import { RaiseTicketComponent } from './User/raise-ticket/raise-ticket.component';
import { UserTicketsComponent } from './User/user-tickets/user-tickets.component';
import { ServiceEngineerTicketsComponent } from './Service Engineer/service-engineer-tickets/service-engineer-tickets.component';
import { ReportStatisticsComponent } from './Service Engineer/report-statistics/report-statistics.component';
import { AverageSeverityComponent } from './Service Engineer/average-severity/average-severity.component';
import { AgingComponent } from './Service Engineer/aging/aging.component';
import { UpdatePriorityComponent } from './Service Engineer/update-priority/update-priority.component';
import { AddUserComponent } from './Administrator/add-user/add-user.component';
import { AddServiceEngineerComponent } from './Administrator/add-service-engineer/add-service-engineer.component';
import { ViewUsersComponent } from './Administrator/view-users/view-users.component';
import { ViewDepartmentsComponent } from './Administrator/view-departments/view-departments.component';
import { DisplayUsersComponent } from './Administrator/display-users/display-users.component';
import { UserModule } from './user.module';
import { PageNotFoundComponent } from './Error/page-not-found/page-not-found.component';
import { AddDepartmentComponent } from './Administrator/add-department/add-department.component';

@NgModule({
  declarations: [
    AppComponent,
    ServiceEngineerComponent,
    UserComponent,
    LoginComponent,
    AdminComponent,
    RaiseTicketComponent,
    UserTicketsComponent,
    ServiceEngineerTicketsComponent,
    ReportStatisticsComponent,
    AverageSeverityComponent,
    AgingComponent,
    UpdatePriorityComponent,
    AddUserComponent,
    AddServiceEngineerComponent,
    ViewUsersComponent,
    ViewDepartmentsComponent,
    DisplayUsersComponent,
    PageNotFoundComponent,
    AddDepartmentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    UserModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
