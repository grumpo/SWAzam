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

Execute "mvn compile exec:java" in the according component to start it.

* Peer:

            Please pass:
            - the path to the dir that contains the MP3 files (storagePath)
            - the port for the web services
            - the address of the server
            - the path to the HSQLDB (dbPath)
            as argument.

            e.g.: mvn exec:java -Dexec.args="/tmp/mp3Storage 9000 http://localhost:9003 /tmp/dbPeer1/localdb"

            An example database can be found in the src/main/resources directory.

            Quit the peer by pressing <enter>.


* Server: 	mvn compile exec:java
			Port provided=9003, if you'd like to choose another provide -Dexec.args
* Server: 	mvn compile exec:java -Dexec.args="9005"


			WebApp available at: http://localhost:8080 
			(wait after server start till this line shows: Information: Starting ProtocolHandler ["http-bio-8080"])
			
			Available Users for Login (Username: John, Password: Doe and Username: Jane, Password: Doe)