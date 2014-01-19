SWAzam
======

Software Architecture Vienna UT WS2013/14


Requirements
------------

* JDK 7
* Maven 3
* fingerprinting<br/>
    Run mvn install in the fingerprinting source directory.


Usage
-----

There are 2 ways to test our application. 

1. For Windows users we provided a PowerShell Script to start all components with predefined arguments.

	1. Start Windows PowerShell
	2. Run the command: .\Demo1.ps "dir\to\sample\music\files"
	3. The music files get copied to all peer storage folders
	4. Components get started: 1 Server, 3 Peers, 1 Client

	
2. You can start all components separately using Maven

	Execute "mvn compile exec:java" in the according component to start it.

	* Peer:		Please pass
				- the path to the dir that contains the MP3 files (storagePath)
				- the port for the web services
				- the address of the server
				- the path to the HSQLDB (dbPath)
				- the username of the peer owner
				- the password of the peer owner
				as argument.

				e.g.: mvn exec:java -Dexec.args="/tmp/mp3Storage 9000 http://localhost:9003 /tmp/dbPeer1/localdb username password"

				An example database can be found in the src/main/resources directory.

				Quit the peer by pressing <enter>.


	* Server: 	mvn compile exec:java -Dexec.args="9005"
				Default port provided=9003, if you'd like to choose another provide -Dexec.args

				WebApp available at: http://localhost:8080 
				(wait after server start till this line shows: Information: Starting ProtocolHandler ["http-bio-8080"])
				
				Available Users for Login (Username: John, Password: Doe and Username: Jane, Password: Doe)
				
	* Client:   Please pass
				- (the path to the HSQLDB) - this is optional. If you don't provide a path the default database from the 
				src/main/resources directory will be used
				
				Available Users for Login (Username: John, Password: Doe and Username: Jane, Password: Doe)
				
	
				