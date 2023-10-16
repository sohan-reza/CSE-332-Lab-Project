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


        // Message display box
        messageBox.setBounds(25, 25, 490, 325);
        messageBox.setFont(new Font("Arial, sans-serif", Font.PLAIN, 15));
        messageBox.setMargin(new Insets(6,6,6,6));
        messageBox.setEditable(false);
        messageBox.setContentType("text/html");
        messageBox.putClientProperty(JTextPane.HONOR_DISPLAY_PROPERTIES, true);

        JScrollPane messageBoxScroller = new JScrollPane(messageBox);
        messageBoxScroller.setBounds(25, 25, 490, 325);


        // Active user box
        activeUserBox.setBounds(520, 25, 156, 325);
        activeUserBox.setEditable(false);
        activeUserBox.setFont(new Font("Arial, sans-serif", Font.PLAIN, 15));
        activeUserBox.setMargin(new Insets(6, 6, 6, 6));
        activeUserBox.setContentType("text/html");
        activeUserBox.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

        JScrollPane userBoxScroller = new JScrollPane(activeUserBox);
        userBoxScroller.setBounds(520, 25, 156, 325);

        // Message input box
        promptBox.setBounds(0, 350, 400, 50);
        promptBox.setFont(new Font("Arial, sans-serif", Font.PLAIN, 15));
        promptBox.setMargin(new Insets(6, 6, 6, 6));

        JScrollPane inputBoxScroller = new JScrollPane(promptBox);
        inputBoxScroller.setBounds(25, 350, 650, 50);

        // Send button
        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial, sans-serif", Font.PLAIN, 15));
        sendButton.setBounds(575, 410, 100, 35);


        // disconnect button
        JButton disconnectButton = new JButton("Disconnect");
        disconnectButton.setFont(new Font("Arial, sans-serif", Font.PLAIN, 15));
        disconnectButton.setBounds(25, 410, 130, 35);



        jFrame.add(messageBox);
        jFrame.add(activeUserBox);
        jFrame.add(promptBox);
        jFrame.add(sendButton);
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}

