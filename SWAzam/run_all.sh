storagePath=/tmp/mp3Storage
timeout=20

echo "starting server..."
cd Server
mvn clean compile exec:java &
sleep $timeout

echo "starting peers..."
mkdir $storagePath
cd ../Peer
mvn clean compile exec:java -Dexec.args="$storagePath 9000 http://localhost:9003"
# mvn clean compile exec:java -Dexec.args="$storagePath 9001 http://localhost:9003"
sleep $timeout

echo "starting client..."
cd ../Client
mvn clean compile exec:java
