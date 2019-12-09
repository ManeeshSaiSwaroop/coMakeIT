import { Priorities } from './priorities';
import { Login } from './login';
import { ServiceEngineerDetails } from './service-engineer-details';

export interface TicketDetails {
    details?:ServiceEngineerDetails;
    ticketID?: string;
    dateOfIssue?:Date;
    requestedEndDate?:string;
    problemDescription?:string;
    priorities?: Priorities;
    ticketStatus?:string;
    credentials?: Login;
    startTime?:string;
    completionTime?:string;
}

