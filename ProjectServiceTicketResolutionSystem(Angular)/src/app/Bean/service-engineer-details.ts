import { Login } from './login';
import { Departments } from './departments';
import { Priorities } from './priorities';

export interface ServiceEngineerDetails {
    id?: number;
    credentials?: Login;
    departments?: Departments;
    totalTicketsWorkedOn?: number;
    currentHighPriorityTicketID?: string;
    priorities?: Priorities;

}
