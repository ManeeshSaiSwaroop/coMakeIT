# coMakeIT


#ServiceTicketResolutionSystem - Spring Boot Application
-> The Requirement given is as follows:
   1. Create a web application for service ticket resolution system at a customer care centre.
There are two users – service engineer and end user.
The end user raises a ticket(or lodges a complaint in the system) for a particular problem.

2. Create a method to create a service ticket that has
a. Ticket ID
b. Priority
c. Start date
d. Requested end date
e. Status
f. Service engineer
g. customer

3. Upon creation of the ticket, the system should automatically look for a service engineer who
is not assigned any tickets and assign the ticket to him/her. The service engineer table has
the below:
a. Id
b. Name
c. Area of expertise
d. Total tickets worked on
e. Current high priority ticket ID

4. If there are no service engineers available without tickets, the method should look for those
that have lower priority tickets and assign to the one who is working on the most recently
created low priority ticket

5. At this time, the status of the low priority ticket has to be changed to pending

6. Create a method to report completion of a ticket by the service engineer

7. Create a method to report statistics of avg time taken
f. Per severity
g. Per engineer
8. Create a method to report aging of open tickets. By aging we mean the number of days a
ticket is open.

-> Let me explain how it works :
   Please note that running the sql queries listed in RunThis.txt in Database folder which is necessary for the working of the Project, atleast addition of admin into LoginCredentials table is needed so that the admin can add the users and service engineers as he needs. And addition of the dummy users,  i.e., Deleted and DeletedServiceEngineer is also needed to ensure successful deletion of the user or serviceEngineer when the admin chooses to without any unprecedented behaviour.
   
-> User can submit a ticket and view his submitted tickets.
-> Service Engineer can work on the tickets assigned to him but he will only be able to work on one ticket at a time ordered    by Priority, requested end date and date of issue. He can also change the priority of any ticket if he thinks it is not      that high of a priority as mentioned and vice versa. He can view the statistics of the company per Severity, check the        statistics per service engineer and also view the aging of his open tickets.
-> Admin can register users and service engineers, delete users and service engineers and also can add department.

# Comments have been given to functions wherever necessary so that it will be easy for others to understand my application. Thank you!!!
