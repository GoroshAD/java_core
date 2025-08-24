import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is ready on port " + port);
            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                System.out.println("New connection accepted");
                out.println("Write your name");
                final String username = in.readLine();
                System.out.println("Client's name: " + username);
                out.println("Are you child? (yes/no)");
                final String answer = in.readLine();
                System.out.println("Client's answer: " + answer);
                String toSend;
                if (answer.equals("yes")) {
                    toSend = "Welcome to the kids area, %s! Let's play!%n";
                } else if (answer.equals("no")) {
                    toSend = "Welcome to the adult zone, %s! Have a good rest, or a good working day!%n";
                } else {
                    throw new IOException();
                }
                out.printf(toSend, username);
                System.out.printf("To send: " + toSend, username);
            }
        }
    }
}
