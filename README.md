#ChatApp
A simple online chat application sample implemented in Java using socket programming. It allows multiple users to connect to a central server and send messages and receive messages from other users.

## Features
- Connecting to the server by multiple clients.
- Assign a unique user ID to each client.
- Enable clients to send messages to the server, which in turn broadcasts messages to all connected clients.
- Simple text-based user interface for entering and viewing messages.

## requirements
- Java Development Kit (JDK) 8 or higher.
- IDE or script editor to edit and run the code (optional).

## start
Using these instructions you can get your chat app up and running on your local device.

### 1. Clone the repository
Copy the repository to your local machine using the following command:
``` bash
Clone portal <C:\Program Files\Java\jdk-21\bin\java.exe>

### Navigate to the Project Directory:
cd <C:\Program Files\Java\jdk-21\bin\java.exe>

### Compile the code
Compile the server and client code using the following commands:
javac ChatServer.java
javac ChatClient.java
javac ChatClient2.java

### Run the server
Start the server by running the following command:
Java chatserver

The server will start and wait for clients to connect.

## Customer operation
Open new terminal windows or command prompts and run the clients using the following commands:
Codeservice
Java chatclient

A welcome message will be displayed asking the customer to say hello

the use
After the server and clients are running, you can start writing messages in each client window.
Messages sent from one client will be broadcast to all other connected clients.
Each message will begin with the sender's User ID to identify them.

## Example
Here is an example of how the application might look in the terminal:

## Server Output
Welcome everyone in the chat application!
The server began on the port 460080
 Waiting for the rest of the customers successful!
Someone joined the chat now!: User1
Someone joined the chat now!: User2
You received a message from the customer User1: hay!
You received a message from the customer User2: hi!!

## Client Output
Welcome to the chat room! Greetings to your friends!:
hay!
hi!!

## Troubleshooting
find the mistakes and resolve it
Ensure that the server runs before the customers start.
Check if the server and customers use the correct and portable IP address.
Ensure that your protection wall settings allow the application to use the specific port.

