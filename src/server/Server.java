package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    private int port;
    private List<User> clients;
    private ServerSocket server;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
    }

    public void run() throws IOException {
        server = new ServerSocket(port) {
            protected void finalize() throws IOException {
                this.close();
            }
        };

        System.out.println("Port "+this.port+" is open!");

        while(true) {
            Socket client = server.accept();

            String name = (new Scanner(client.getInputStream())).nextLine();
            //Format the name
            name = name.replace(",","");
            name = name.replace(" ", "_");

            System.out.println("New Client: \"" + name + "\"\n\t    Host: "+client.getInetAddress().getHostAddress());

            User newUser = new User(client, name);
            this.clients.add(newUser);

            newUser.getOutStream().println("<b>Welcome</b> "+ newUser.toString());

            UserHandler userHandler = new UserHandler(this, newUser);
            Thread userHandlerThread = new Thread(userHandler);
            userHandlerThread.start();

        }
    }

    public void removeUser(User user) {
        this.clients.remove(user);
    }

    public void broadcastMessages(String message, User sender) {
        for(User client : this.clients) {
            client.getOutStream().println(sender.toString() + "<span>: "+ message + "</span>");
        }
    }

    public void broadcastAllUsers() {
        for(User client : this.clients) {
            client.getOutStream().println(this.clients);
        }
    }

    public void sendMessageToUser(String message, User sender, String receiver) {
        boolean find = false;
        for(User client : this.clients) {
            if(client.getNickName().equals(receiver) && client != sender) {
                find = true;
                sender.getOutStream().println(sender.toString() + " -> " + client.toString()+": "+message);
                client.getOutStream().println("(<b>Private</b>)" + sender.toString() + "<span> :"+ message +"</span>");
            }
        }

        if(!find) {
            sender.getOutStream().println(sender.toString() + " -> (<b> no one </b>)" + message);
        }
    }



    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
