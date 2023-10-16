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



        final JFrame jFrame = new JFrame();
        jFrame.setTitle("Wii");
        jFrame.setLayout(null);
        jFrame.setSize(700, 500);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        messageBox.setBounds(25, 25, 490, 325);
        messageBox.setFont(new Font("Arial, sans-serif", Font.PLAIN, 15));
        messageBox.setMargin(new Insets(6,6,6,6));
        messageBox.setEditable(false);
        messageBox.setContentType("text/html");
        messageBox.putClientProperty(JTextPane.HONOR_DISPLAY_PROPERTIES, true);

        JScrollPane messageBoxScroller = new JScrollPane(messageBox);
        messageBoxScroller.setBounds(25, 25, 490, 325);



        jFrame.add(messageBox);
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
