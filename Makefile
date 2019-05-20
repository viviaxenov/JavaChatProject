
all : Server Client
	
Server : Message
	javac source/Server/*.java -cp compiled/ -d compiled/
Client : Message
	javac source/Client/*.java -cp compiled/ -d compiled/
Message : 
	if  [ ! -d compiled ]; then mkdir compiled; fi
	javac source/Message.java  -d compiled/
	
