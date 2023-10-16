package client;

import javax.swing.*;
import java.awt.*;

public class Client {

    final JTextPane messageBox =  new JTextPane();
    final JTextPane activeUserBox = new JTextPane();
    final JTextField promptBox = new JTextField();

    private String serverName;
    private int PORT;
    private String name;
    public Client() {
        //initial default value
        this.serverName = "localhost";
        this.PORT = 5050;
        this.name = "nickname";

        Font font = new Font("Arial, sans-serif", Font.PLAIN, 15);

        final JFrame jFrame = new JFrame();
        jFrame.setTitle("Wii");
        jFrame.setLayout(null);
        jFrame.setSize(700, 500);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
