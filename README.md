# Duplicate Customer Finder #

Extremely simple api to manipulate customers and then find duplicates.

## Setup ##
1. Clone this repository
2. Start included solr or start one of your own
3. Make sure application.properties has correct solr url
4. `gradle build`
5. `java -jar build/libs/dupcustomer-0.1.0.jar`

## Run Tests ##
`gradle test`

## Running Included Solr ##
There is a stripped down slightly modified version of the default solr download included with config
files that can be used for this program.
1. `cd solr`
2. `bin/solr start`

## Endpoints ##
* /customers (GET) - Lists the customers in the system paginated
* /customers (POST) - Creates a new customer
* /customers/{id} (PUT) - Replaces the customer object with given one
* /customers/{id} (DELETE) - Removes customer object with given id
* /duplicates/email (GET) - returns a list of accounts with duplicate email addresses
* /duplicates/phone (GET) - returns a list of accounts with duplicate phone numbers
* /duplicates/last-name-phone (GET) - returns a list of accounts with duplicate last name and phone numbers
* /solr-duplicates/email (GET) - returns a list of accounts with duplicate email addresses from solr
* /solr-duplicates/phone (GET) - returns a list of accounts with duplicate phone numbers from solr


