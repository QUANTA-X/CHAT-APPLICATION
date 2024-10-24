import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ChatServer {
    // Map for storing connected clients
    private static ConcurrentMap<String, PrintWriter> clients = new ConcurrentHashMap<>();
    private static int clientIdCounter = 1;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Welcome to the chat application!");
            System.out.println("The server started on port 12345.\nWaiting for clients to connect...");

            // Start a thread to handle server console input
            new Thread(new ServerConsoleHandler()).start();

            // Loop to accept connected clients
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;
        private String clientId;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                // Create a unique identifier for each client
                synchronized (ChatServer.class) {
                    clientId = "User" + clientIdCounter++;
                }

                clients.put(clientId, out);
                out.println("Your user ID is: " + clientId);
                System.out.println(clientId + " has joined the chat.");
                broadcastMessage("Server", clientId + " has joined the chat.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String input;
                // Loop to receive messages from the client
                while ((input = in.readLine()) != null) {
                    System.out.println(clientId + " says: " + input);
                    broadcastMessage(clientId, input);
                }
            } catch (IOException e) {
                System.out.println("Connection error: " + e.getMessage());
            } finally {
                // Remove the client from the list when the connection is over
                clients.remove(clientId);
                broadcastMessage("Server", clientId + " has left the chat.");
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + e.getMessage());
                }
            }
        }

        // Broadcast the message to all connected clients
        private void broadcastMessage(String senderId, String message) {
            String formattedMessage = senderId + ": " + message;
            for (PrintWriter writer : clients.values()) {
                writer.println(formattedMessage);
            }
        }
    }

    // Handler for server console input to send messages
    static class ServerConsoleHandler implements Runnable {
        private BufferedReader consoleInput;

        public ServerConsoleHandler() {
            consoleInput = new BufferedReader(new InputStreamReader(System.in));
        }

        @Override
        public void run() {
            try {
                String command;
                while ((command = consoleInput.readLine()) != null) {
                    // Broadcast the server's message to all clients
                    broadcastToAll("Server: " + command);
                }
            } catch (IOException e) {
                System.out.println("Error reading from console: " + e.getMessage());
            }
        }

        // Broadcast a message from the server to all clients
        private void broadcastToAll(String message) {
            for (PrintWriter writer : clients.values()) {
                writer.println(message);
            }
        }
    }
}
