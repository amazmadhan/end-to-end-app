"# end-to-end-app" steps:

Create Spring boot application
Create database
Create JPA specify Entity class and connect with your database
Change the column name in the Entity class and verify the changes in the tables
Create a dummy hardcoded API (health-check) without using database and verify with the postman
Create REST API for the CRUD operations
  1, using id (primary key)
  2, using name (unique)
Use JPA specific methods for JpaRepository like save, findById, findAll, delete
Write your methods for JpaRepository like findByFirstName
Implement Optional
Implement lambok - @Data is not working so reverted the code in local and using getters and setters manually in the Entity class
Update "ResponseEntity" for all the REST API's for specifying our own http status codes and messages in body
For "get all" APIs, 
  1, returning an empty list is the most REST-compliant and user-friendly approach. It avoids unnecessary exceptions or error states and simplifies client-side handling.
  2, returning error message "No employees found" in the body with "404 NOT_FOUND"
Added dev-tools depedency for live reload functionality. It restarts automatically after making changes to our application.
