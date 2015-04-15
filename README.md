# Duplicate Customer Finder #

Extremely simple api to manipulate customers and then find duplicates.

## Setup ##
1. Clone the repository
2. `gradle build`
3. `java -jar build/libs/dupcustomer-0.1.0.jar`

## Run Tests ##
`gradle test`

## Endpoints ##
* /customers (GET) - Lists the customers in the system paginated
* /customers (POST) - Creates a new customer
* /customers/{id} (PUT) - Replaces the customer object with given one
* /customers/{id} (DELETE) - Removes customer object with given id
* /duplicates/email (GET) - returns a list of accounts with duplicate email addresses
* /duplicates/phone (GET) - returns a list of accounts with duplicate phone numbers
* /duplicates/last-name-phone (GET) - returns a list of accounts with duplicate last name and phone numbers

