## Cat exhibition

This is Spring boot application using h2 database. 
Project includes import.sql file which fills up database with sample data.
When project runs it uses port 8080 on localhost and can be reached on http://localhost:8080/api/cat

In this project there are only two controllers and some necessary services to run it.
There are some tests covering only controllers and the data which is loaded during start up of application

#### Available endpoints

- POST http://localhost:8080/api/cat

- GET http://localhost:8080/api/cat/all

- PATCH http://localhost:8080/api/cat/{catId}/{judgeId}/vote/{points}

- PATCH http://localhost:8080/api/cat/{catId}/{ticketId}/vote

- Get http://localhost:8080/api/results
