1.  The web service exposes the following endpoints:

	- http://localhost:8080/teams/list List the teams saved in the system.
	- http://localhost:8080/teams/new Create a new team.

	- http://localhost:8080/players/list List the players available in the system.
	- http://localhost:8080/competitions/list List the competitions available in the system.

2.  The web service uses basic authentication with username:teamadmin and password:GeoffHurst1966

3.  The new team creation will fail if any of the following conditions are not met: 
	-  A team with the same name exists already.
	-  The owner email does not correspond to a player in the system.
	-  The competition does not exist, or it has already happened.
	-  Any of the team emails do not correspond to a player in the system.
	
4.  See src/main/resources/data.sql for data that has been preloaded into the system.

5. To create a new team, the following JSON should be used with a POST operation specifying Content-Type header as application/json:

	{
	"name": "Aston Villa",
	"ownerEmail": "sam.barber@gmail.com",
	"competition": "FA Cup 2017",
	"city": "London",
	"teamEmails": ["michael.owen@gmail.com","alan.shearer@gmail.com","thierry.henry@gmail.com","ryan.giggs@gmail.com"]
 	}

6. The service compiles to a standalone jar with main class **com.sebarber.footballteams.FootballteamsApplication**.  Use mvn clean package to create the jar file.  It can also be run from maven using mvn spring-boot:run

7.  I have used Spring Boot and JPA to create this service as it is fairly simple to get it up and running in a short period of time for the functionality required.  Spring also supports security out of the box.  I used Mockito for the test mocking in order to realistically mock scenarios without needing a database.
