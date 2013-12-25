SWAzam
======

Software Architecture Vienna UT WS2013/14


Requirements
------------

* JDK 7
* Maven 3
* fingerprinting<br/>
    Run mvn install in the fingerprinting source directory.


Run
---

Execute "mvn compile exec:java" in the according component.

* Peer: mvn exec:java -Dexec.args="/tmp/mp3Storage 9000 http://localhost:9003"


* Server: 	mvn compile exec:java
			Port provided=9003, if you'd like to choose another provide -Dexec.args
* Server: 	mvn compile exec:java -Dexec.args="9005"


			WebApp available at: http://localhost:8080 
			(wait after server start till this line shows: Information: Starting ProtocolHandler ["http-bio-8080"])
			
			Available Users for Login
			Username	Password
			
			Johannes	Johannes
			Markus		Markus
			Manu		Manu
			Gernot		Gernot