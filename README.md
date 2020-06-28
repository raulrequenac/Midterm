# MIDTERM PROJECT BY RAUL REQUENA CAYUSO
## Introduction 
This project consists of a banking system. 
  
## Reasoning from requirements
### The system must have 4 types of accounts: StudentChecking, Checking, Savings, and CreditCard
All the accounts inherit from Checking but CreditCard, which inherits from Savings. This is because all of the children share the same attributes as their parents, but some specific ones, which are removed from database with the tag @Transient.

### The system must have 3 types of Users: Admins and AccountHolders
All of the users inherit from an abstract class User, which has the name, username, password and the relation with the class Role.
All type of users are able to login and logout. Admins are the only ones who are able to create other users.

### Admins can create new accounts. When creating a new account they can create Checking, Savings, or CreditCard Accounts
As the requirement says, Admins are the only ones who can create any type of bank account. 
Each requirement for the creation of the bank accounts are managed.

### Interest and Fees should be applied appropriately
#### Penalty Fee
Once the balance has surpassed the minimum balance, if it drops below the minimum balance, a penalty fee will be deducted, which means that a balance can reach a negative number with a minimum of -40. Penalty fee is manages in the debit method, as it's the one who retrieves money from the account.

#### Interest Rates
Everytime an account balance is accessed, the system will check the last time the interest rate was applied. If this time is longer than 1 year (on savings) or than 1 month (on credit cards) it will be applied properly for every year/month missed.

### Account Access
All the users are able to credit/debit accounts. Account holders are limited to their own accounts and third parties need to provide their hashed key.
Also, admins and account holders are able to check accounts balance. Account holders are limited to their own accounts.
To conclude, account holders are also able to access their own accounts data, and also transfering money to other accounts.

### Fraud Detection
Everytime an account is credited or debited, a Transaction is sabed into database, with the accountID, userID and the moment in which the transaction was realized. For detecting posible cases of fraud, two patterns are checked:

- User has made a number of transaction today greater than the number of transactions made in 1 day on the account from the same user.
- User has made a transaction less than 1 second after he made the last one on the same account.

### Logging
Every method in the application has a Log when entering and when leaving the method. Also, when any exception is thrown, a log is recorded too.
Logs are saved in a mongo database and in a file.

### Good practices
#### You must include thorough unit and integration tests
The project has an unit and integration tests coverage of 88% of all the lines.

#### You must include robust error handling
A total of 14 Exceptions were created for covering every possible error that could be thrown in the project.
A global handler manages these exceptions to return the appropiated Http status for each one of them.

#### You must use the Money class for all currency and BigDecimal for any other decimal or large number math
The Money class is implemented and embedded in the Checking class for the account balance. All of the numbers which include decimals are instantiated with BigDecimal.

#### You must provide robust logs
As said before, any access to a method will have the appropiated logs.

## Extras
### Database
For this proyect I have used H2 database. If you wish to access the data you can do so by following these instructions:
1) Run proyect with mvn spring-boot:run
2) Insert the following http://localhost:8080/h2-console into your browser
3) There you can login by inserting:

    -JDBC URL: jdbc:h2:mem:midterm
    -user: root
    -password: root

The database is pre-instantiated to be ready for testing.

### Postman Collection
Get the Postman Collection in the following url: https://web.postman.co/collections/10126620-17a1cc20-fe8d-453c-bad7-49ee964e55ac/publish?version=latest&workspace=c08aa7ac-1575-48fe-8d16-539c891af347
Don't forget to log in with any of the following users to access the api' methods:

- username: admin,          password: admin
- username: account-holder, password: account-holder
- username: third-party,    password: third-party

## Conclusion
This was a challenging project, mostly because the requirements were quite ambiguous and it took me time to decide how I wanted to resolve every one of the requirements.





