import {Role} from 'src/app/Bean/role';
export interface Login {
    username: string;
    password: string;
    roles?: Role;
}
