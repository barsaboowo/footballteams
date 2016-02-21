1.  The web service exposes the following endpoints:

http://localhost:8080/teams/list - List the teams saved in the system.
http://localhost:8080/teams/new - Create a new team.

http://localhost:8080/players/list - List the players available in the system.
http://localhost:8080/competitions/list - List the competitions available in the system.

2.  The web service uses basic authentication with username:teamadmin and password:GeoffHurst1966

3.  To create a new team, the following JSON should be used with a POST operation specifying Content-Type header as application/json:

{
	"name": "Aston Villa",
	"ownerEmail": "sam.barber@gmail.com",
	"competition": "FA Cup 2017",
	"city": "London",
	"teamEmails": ["michael.owen@gmail.com","alan.shearer@gmail.com","thierry.henry@gmail.com","ryan.giggs@gmail.com"]
} 

4.  The new team creation will fail if any of the following conditions are not met: 
	a.  A team with the same name exists already.
	b.  The owner email does not correspond to a player in the system.
	c.  The competition does not exist, or it has already happened.
	d.  Any of the team emails do not correspond to a player in the system.
	
5.  See src/main/resources/data.sql for data that has been preloaded into the system.