import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String host = "netology.homework";
        int port = 8080;

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            Scanner scanner = new Scanner(System.in);
            String question = in.readLine();
            serverPrinter(question);
            System.out.println("Type your name:");
            String name = scanner.nextLine();
            out.println(name);
            question = in.readLine();
            serverPrinter(question);
            String answer = "";
            while (!answer.equals("yes") && !answer.equals("no")) {
                answer = scanner.nextLine();
                if (!answer.equals("yes") && !answer.equals("no")) {
                    System.out.println("Please answer yes or no.");
                }
            }
            out.println(answer);
            final String greetingsPhrase = in.readLine();
            serverPrinter(greetingsPhrase);
        }
    }

    public static void serverPrinter(String message) {
        System.out.println("Server response: " + message);
    }
}