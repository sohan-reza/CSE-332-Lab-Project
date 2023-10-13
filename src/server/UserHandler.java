package server;

import java.util.Scanner;

public class UserHandler implements Runnable {
    private Server server;
    private User user;

    public UserHandler(Server server, User user) {
        this.server = server;
        this.user = user;
        this.server.broadcastAllUsers();
    }

    @Override
    public void run() {
        String message;

        Scanner scanner = new Scanner(this.user.getInputStream());
        while(scanner.hasNextLine()) {
            message = scanner.nextLine();

            if(message.charAt(0) == '@') {
                if(message.contains(" ")) {
                    System.out.println("Private message: " + message);
                    int firstSpace = message.indexOf(" ");
                    String receiver = message.substring(1, firstSpace);
                    String extractMessage = message.substring(firstSpace+1);

                    server.sendMessageToUser(extractMessage, user, receiver);
                }
            }else {
                server.broadcastMessages(message, user);
            }
        }

        server.removeUser(user);
        this.server.broadcastAllUsers();
        scanner.close();
    }
}
