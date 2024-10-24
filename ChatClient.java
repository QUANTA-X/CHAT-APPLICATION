import java.io.*;
import java.net.*;

public class ChatClient {
    private BufferedReader in;
    private PrintWriter out;
    private String lastMessage = null;

    public ChatClient(String serverAddress, int serverPort) throws IOException {
        Socket socket = new Socket(serverAddress, serverPort);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Start a thread to listen for incoming messages
        new Thread(new ReceivedMessagesHandler()).start();
    }

    public void sendMessage(String message) {
        String normalizedMessage = message.trim().toLowerCase();
        if (normalizedMessage.equals(lastMessage)) {
            System.out.println("Sorry, messages cannot be repeated.");
            return;
        }
        out.println(message);  // Send the original message
        lastMessage = normalizedMessage; // Store the normalized message for comparison
    }

    class ReceivedMessagesHandler implements Runnable {
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message); // Print received messages
                }
            } catch (IOException e) {
                System.out.println("Connection lost: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Connect to the server
            ChatClient client = new ChatClient("localhost", 12345);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Type your messages below:");

            String message;
            // Read user input and send it to the server
            while ((message = userInput.readLine()) != null) {
                client.sendMessage(message);
            }
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}
