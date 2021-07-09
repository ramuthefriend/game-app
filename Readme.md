Five in a row program is a console based application using socket programming in java

execute the below maven commands to run the application 

maven and java versions should be compatible to run the application

mvn clean install

Strat Server 
	
	java -cp target/game-app-0.0.1-SNAPSHOT.jar com.game.server.Server 

Run the two clients

client1
======
	open command prompt on project root directory 	
	java -cp target/game-app-0.0.1-SNAPSHOT.jar com.game.client.ClientApp

client2
======
	open command prompt project root directory 	
	java -cp target/game-app-0.0.1-SNAPSHOT.jar com.game.client.ClientApp		


Note - Apologies due to time constraints unable to accomplish below approches .

Should have done better
=======================
1)From the bottom to top we can choose the index (i.e 0-8), should have implement specific row and column i.e bi directional approach.
2)Initially thought to implement Sringboot along with database and client connecting from browsers .It’s easy to maintain game session game info to store in the data base.
3)Should have implement the junit testcases
