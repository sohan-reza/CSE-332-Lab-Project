package client;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {

    final JTextPane messageBox =  new JTextPane();
    final JTextPane activeUserBox = new JTextPane();
    final JTextField promptBox = new JTextField();

    PrintWriter output;
    BufferedReader input;
    Socket server;

    Thread read;

    String oldMessage;

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
        messageBox.setBounds(25, 25, 490, 320);
        messageBox.setFont(new Font("Arial, sans-serif", Font.PLAIN, 15));
        messageBox.setMargin(new Insets(6,6,6,6));
        messageBox.setEditable(false);
        messageBox.setContentType("text/html");
        messageBox.putClientProperty(JTextPane.HONOR_DISPLAY_PROPERTIES, true);

        JScrollPane messageBoxScroller = new JScrollPane(messageBox);
        messageBoxScroller.setBounds(25, 25, 490, 320);


        // Active user box
        activeUserBox.setBounds(520, 25, 156, 320);
        activeUserBox.setEditable(false);
        activeUserBox.setFont(new Font("Arial, sans-serif", Font.PLAIN, 15));
        activeUserBox.setMargin(new Insets(6, 6, 6, 6));
        activeUserBox.setContentType("text/html");
        activeUserBox.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

        JScrollPane userBoxScroller = new JScrollPane(activeUserBox);
        userBoxScroller.setBounds(520, 25, 156, 320);

        // Message input box
        promptBox.setBounds(25, 350, 650, 50);
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

        // Key Listner
        promptBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                // send message on enter key pressed
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    //send message
                }

                //
                if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    String currentMessage = promptBox.getText().trim();
                    promptBox.setText(oldMessage);
                    oldMessage = currentMessage;
                }

            }
        });

        // click on send button

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //send
            }
        });

        // connect info
        JTextField serverIpAddress = new JTextField(this.serverName);
        JTextField serverPort = new JTextField(String.valueOf(this.PORT));
        JTextField userName = new JTextField(this.name);
        JButton connectButton = new JButton("Connect");


        serverIpAddress.getDocument().addDocumentListener(new TextListener(serverIpAddress, serverPort, userName, connectButton));
        serverPort.getDocument().addDocumentListener(new TextListener(serverIpAddress, serverPort, userName, connectButton));
        userName.getDocument().addDocumentListener(new TextListener(serverIpAddress, serverPort, userName, connectButton));

        connectButton.setFont(new Font("Arial, sans-serif", Font.PLAIN, 15));
        serverIpAddress.setBounds(25, 380, 135, 40);
        serverPort.setBounds(200, 380, 135, 40);
        userName.setBounds(375, 380, 135, 40);
        connectButton.setBounds(575, 380, 100, 40);

        messageBox.setBackground(Color.GRAY);
        activeUserBox.setBackground(Color.GRAY);

        // Add components to the main frame
        jFrame.add(messageBox);
        jFrame.add(activeUserBox);

        jFrame.add(serverIpAddress);
        jFrame.add(serverPort);
        jFrame.add(userName);
        jFrame.add(connectButton);
        jFrame.setVisible(true);

        appendTextToPane(messageBox, "<h1>Java Lab Project</h1>");

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    serverName = serverIpAddress.getText();
                    PORT = Integer.parseInt(serverPort.getText());
                    name = userName.getName();

                    appendTextToPane(messageBox, "<span>Connecting to </span>"+serverName+" <span>port "+PORT+"</span>");
                    server = new Socket(serverName, PORT);

                    appendTextToPane(messageBox, "<span>Connected to </span>"+server.getRemoteSocketAddress()+"</span>");
                    input = new BufferedReader(new InputStreamReader(server.getInputStream()));
                    output = new PrintWriter(server.getOutputStream());

                    //send username to server
                    output.println(name);

                    //Thread for read
                    read = new Read();
                    read.start();

                    //remove connect interface from the window
                    jFrame.remove(serverIpAddress);
                    jFrame.remove(serverPort);
                    jFrame.remove(userName);
                    jFrame.remove(connectButton);

                    //add message send interface to the window
                    jFrame.add(promptBox);
                    jFrame.add(sendButton);
                    jFrame.add(disconnectButton);

                    jFrame.revalidate();
                    jFrame.repaint();

                    messageBox.setBackground(Color.WHITE);
                    activeUserBox.setBackground(Color.WHITE);


                } catch(Exception e) {
                    appendTextToPane(messageBox, "<span>Faild to connect with server!</span>");
                    JOptionPane.showMessageDialog(jFrame, e.getMessage());
                }
            }
        });

        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //remove message send interface from the window
                jFrame.remove(promptBox);
                jFrame.remove(sendButton);
                jFrame.remove(disconnectButton);

                //add connect interface to the window
                jFrame.add(serverIpAddress);
                jFrame.add(serverPort);
                jFrame.add(userName);
                jFrame.add(connectButton);


                jFrame.revalidate();
                jFrame.repaint();

                read.interrupt();

                activeUserBox.setText(null);

                messageBox.setBackground(Color.LIGHT_GRAY);
                activeUserBox.setBackground(Color.LIGHT_GRAY);

                appendTextToPane(messageBox, "<span>Connection closed.</span>");
                output.close();

            }
        });


    }

    private void appendTextToPane(JTextPane textPane, String message) {
        HTMLDocument document = (HTMLDocument) textPane.getDocument();
        HTMLEditorKit editor = (HTMLEditorKit) textPane.getEditorKit();

        try{
          editor.insertHTML(document, document.getLength(), message, 0, 0, null);
          textPane.setCaretPosition(document.getLength());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
    }

    public  class Read extends Thread {
        @Override
        public void run() {
            String message;
            while(Thread.currentThread().isInterrupted() == false) {
                try{
                    message = input.readLine();
                    if(message != null) {
                        if(message.charAt(0) == '[') {
                            message = message.substring(1, message.length()-1);
                            List<String> userList = new ArrayList<>(Arrays.asList(message.split(", ")));
                            activeUserBox.setText(null);

                            for(String user : userList) {
                                appendTextToPane(activeUserBox, "@" + user);
                            }

                        }else {
                            appendTextToPane(messageBox, message);
                        }
                    }
                }catch(IOException e) {
                    System.err.println("Failed to parse message!");
                }
            }
        }
    }
}

