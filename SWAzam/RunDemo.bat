@echo

echo "Starting Server..."
cd server
START mvn clean compile exec:java -Dexec.args="9005"
cd ..

cd peer
START mvn clean compile
echo "Press any key when peer is compiled and server is running!"
PAUSE
echo "Starting Peer1..."
START mvn exec:java -Dexec.args="C:\\temp\\mp3lib1 9000 http://localhost:9005"
echo "Starting Peer2..."
START mvn exec:java -Dexec.args="C:\\temp\\mp3lib2 9001 http://localhost:9005"
echo "Starting Peer3..."
START mvn exec:java -Dexec.args="C:\\temp\\mp3lib3 9002 http://localhost:9005"
cd ..
echo "Press any key when peers are running!"
PAUSE

cd client
echo "Starting Client..."
echo "Valid Demo Login is User:John Password:Doe"
START mvn clean compile exec:java
cd ..