package Frontend;

import Backend.LoginBackend;
import Backend.Magager;
import DataStructure.Book;
import DataStructure.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainGui {
    private JFrame mainGuiFrame = new JFrame();
    private JFrame deleteFrame = new JFrame();
    private JFrame accountFrame = new JFrame();
    private JButton actionButton;
    private ArrayList<Book> books;
    private Customer customer;

    public MainGui() {
        Magager manager = new Magager();
        try {
            manager.LoadBooks();
            createMainGuiFrame();
            createMainGuiLayout();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error starting the application: " + e.getMessage());
        }
    }

    private void createMainGuiFrame() {
        mainGuiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGuiFrame.setTitle("Welcome to our Online Library");
        mainGuiFrame.setSize(800, 600);
        mainGuiFrame.setLocationRelativeTo(null);
        mainGuiFrame.setLayout(new BorderLayout());
        mainGuiFrame.setVisible(true);
    }

    private void createMainGuiLayout() {
        JPanel flowPanel = new JPanel(new FlowLayout());
        JButton LogInButton = new JButton("Log In");
        JButton CreateAccountButton = new JButton("Create Account");
        JButton DeleteAccountButton = new JButton("Delete Account");

        flowPanel.add(LogInButton);
        flowPanel.add(CreateAccountButton);
        flowPanel.add(DeleteAccountButton);
        mainGuiFrame.add(flowPanel, BorderLayout.SOUTH);

        LogInButton.addActionListener((ActionEvent e) -> {
            createLoginFrame();
//            mainGuiFrame.dispose();
        });

        CreateAccountButton.addActionListener((ActionEvent e) -> {
            createAccountFrame();
//            mainGuiFrame.dispose();
        });

        DeleteAccountButton.addActionListener((ActionEvent e) -> {
            deleteAccountFrame();
//            mainGuiFrame.dispose();
        });
    }

    private void createLoginFrame() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 200);
        loginFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        loginFrame.add(panel);
        placeComponents(panel, "login");

        loginFrame.setVisible(true);
    }

    private void createAccountFrame() {
        accountFrame = new JFrame("Create Account");
        accountFrame.setSize(300, 200);
        accountFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        accountFrame.add(panel);
        placeComponents(panel, "create");

        accountFrame.setVisible(true);
    }

    private void placeComponents(JPanel panel, String action) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);


//        panel.add(actionButton);
        actionButton = new JButton(action);

        for (ActionListener al : actionButton.getActionListeners()) {
            actionButton.removeActionListener(al);
        }

        if (action.equals("create")) {
            JLabel nameLabel = new JLabel("Name");
            nameLabel.setBounds(10, 80, 80, 25);
            panel.add(nameLabel);

            JTextField nameText = new JTextField(20);
            nameText.setBounds(100, 80, 165, 25);
            panel.add(nameText);

            JLabel fnameLabel = new JLabel("First Name");
            fnameLabel.setBounds(10, 110, 80, 25);
            panel.add(fnameLabel);

            JTextField fnameText = new JTextField(20);
            fnameText.setBounds(100, 110, 165, 25);
            panel.add(fnameText);

            JLabel birthdayLabel = new JLabel("Birthday");
            birthdayLabel.setBounds(10, 140, 80, 25);
            panel.add(birthdayLabel);

            JTextField birthdayText = new JTextField(20);
            birthdayText.setBounds(100, 140, 165, 25);
            panel.add(birthdayText);

            JLabel addressLabel = new JLabel("Address");
            addressLabel.setBounds(10, 170, 80, 25);
            panel.add(addressLabel);

            JTextField addressText = new JTextField(20);
            addressText.setBounds(100, 170, 165, 25);
            panel.add(addressText);

            JLabel zipCodeLabel = new JLabel("Zip Code");
            zipCodeLabel.setBounds(10, 200, 80, 25);
            panel.add(zipCodeLabel);

            JTextField zipCodeText = new JTextField(20);
            zipCodeText.setBounds(100, 200, 165, 25);
            panel.add(zipCodeText);

            JLabel cityLabel = new JLabel("City");
            cityLabel.setBounds(10, 230, 80, 25);
            panel.add(cityLabel);

            JTextField cityText = new JTextField(20);
            cityText.setBounds(100, 230, 165, 25);
            panel.add(cityText);

            actionButton = new JButton(action);
            actionButton.setBounds(10, 260, 80, 25);


            actionButton.addActionListener(e -> {
                String email = userText.getText();
                String password = new String(passwordText.getPassword());
                String name = nameText.getText();
                String fname = fnameText.getText();
                String birthday = birthdayText.getText();
                String address = addressText.getText();
                String zipCode = zipCodeText.getText();
                String city = cityText.getText();

                switch (action) {
                    case "create":
                        LoginBackend.createAccount(customer, name, fname, email, password, birthday, address, zipCode, city);
                        accountFrame.dispose();
                        break;
                    // ...
                }
            });
            panel.add(actionButton);
        } else {
//            actionButton = new JButton(action);
            actionButton.setBounds(10, 80, 80, 25);
            actionButton.addActionListener(e -> {
                String email = userText.getText();
                String password = new String(passwordText.getPassword());

                switch (action) {
                    case "login":
                        if (LoginBackend.checkLogin(email, password)) {
                            new BookOverviewGui(books);
                        } else {
                            // Login failed
                            JOptionPane.showMessageDialog(null, "Login failed. Please check your credentials and try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "delete":
                        LoginBackend.deleteAccount(customer, email, password);
                        deleteFrame.dispose();
                        break;
                }
            });
            panel.add(actionButton);
        }
    }

    private void deleteAccountFrame() {
        deleteFrame = new JFrame("Delete Account");
        deleteFrame.setSize(300, 200);
        deleteFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        deleteFrame.add(panel);
        placeComponents(panel, "delete");

        deleteFrame.setVisible(true);
    }
}