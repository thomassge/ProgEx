package Frontend;

import Backend.LoginBackend;
import Backend.Manager;
import DataStructure.Book;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainGui {
    private final JFrame mainGuiFrame = new JFrame();
    private final JFrame deleteFrame = new JFrame();
    private JFrame frame;
    private ArrayList<Book> books;

    public MainGui() {
        books = Manager.getBooks();
        createMainGuiFrame();
        createMainGuiLayout();
    }

    private void createMainGuiFrame() {
        mainGuiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGuiFrame.setTitle("Welcome to our Online Library");
        mainGuiFrame.setSize(800, 600);
        mainGuiFrame.setLocationRelativeTo(null);
        mainGuiFrame.setLayout(new BorderLayout());

        try {
            BufferedImage img = ImageIO.read(getClass().getResource("/FrontEnd/background.png"));
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(img, 0, 0, null);
                }
            };
            panel.setLayout(new BorderLayout());
            mainGuiFrame.setContentPane(panel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainGuiFrame.setVisible(true);
    }

    private void createMainGuiLayout() {
        JPanel MainGuiPanel = new JPanel(new FlowLayout());
        addButtonToPanel(MainGuiPanel, "Log In", this::LoginFrame);
        addButtonToPanel(MainGuiPanel, "Create Account", this::createAccountFrame);
        addButtonToPanel(MainGuiPanel, "Delete Account", this::deleteAccountFrame);
        mainGuiFrame.add(MainGuiPanel, BorderLayout.SOUTH);
    }

    private void addButtonToPanel(JPanel panel, String buttonText, Runnable action) {
        JButton MainGuiButtons = new JButton(buttonText);
        MainGuiButtons.addActionListener(e -> action.run());
        panel.add(MainGuiButtons);
    }

    private void LoginFrame() {
        createFrame("Login", "login");
    }

    private void createAccountFrame() {
        createFrame("Create Account", "create");
    }

    private void deleteAccountFrame() {
        createFrame("Delete Account", "delete");
    }

    private void createFrame(String frameTitle, String action) {
        frame = new JFrame(frameTitle);
        frame.setSize(300, 200);

        if(frameTitle.equals("Create Account")) {
            frame.setSize(300, 375);
        }

        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, action);
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel, String action) {
        panel.setLayout(null);

        JTextField emailTextField = createLabeledTextField(panel, "Email", 20);
        JPasswordField passwordTextField = createLabeledPasswordField(panel);

        JButton actionButton = new JButton(action);
        actionButton.setBounds(10, 80, 80, 25);
        actionButton.addActionListener(e -> performAction(action, emailTextField, passwordTextField));
        panel.add(actionButton);

        if (action.equals("create")) {
            JTextField nameTextField = createLabeledTextField(panel, "Name", 80);
            JTextField fnameTextField = createLabeledTextField(panel, "First Name", 110);
            JTextField birthdayTextField = createLabeledTextField(panel, "Birthday", 140);
            JTextField addressTextField = createLabeledTextField(panel, "Address", 170);
            JTextField zipCodeTextField = createLabeledTextField(panel, "Zip Code", 200);
            JTextField cityTextField = createLabeledTextField(panel, "City", 230);

            actionButton.setBounds(10, 265, 80, 25);
            addActionToCreateButton(actionButton, emailTextField, passwordTextField, nameTextField, fnameTextField, birthdayTextField,
                                addressTextField, zipCodeTextField, cityTextField);
        }
    }

    private JPasswordField createLabeledPasswordField(JPanel panel) {
        JLabel passwordlabel = createLabel("Password", 50);
        panel.add(passwordlabel);

        JPasswordField passwordField = createPasswordField();
        panel.add(passwordField);

        return passwordField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        return passwordField;
    }

    private JTextField createLabeledTextField(JPanel panel, String labelText, int y) {
        JLabel createAccountLabel = createLabel(labelText, y);
        panel.add(createAccountLabel);

        JTextField textField = createTextField(y);
        panel.add(textField);

        return textField;
    }

    private JLabel createLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(10, y, 80, 25);
        return label;
    }

    private JTextField createTextField(int y) {
        JTextField createAccountTextField = new JTextField(20);
        createAccountTextField.setBounds(100, y, 165, 25);
        return createAccountTextField;
    }

    private void addActionToCreateButton(JButton actionButton, JTextField emailTextField, JPasswordField passwordTextField,
                                         JTextField nameTextField, JTextField fnameTextField, JTextField birthdayTextField,
                                         JTextField addressTextField, JTextField zipCodeTextField, JTextField cityTextField){
        actionButton.addActionListener(e -> {
            if(validateInputFields(emailTextField, passwordTextField, nameTextField, fnameTextField,
                                    birthdayTextField, addressTextField, zipCodeTextField, cityTextField)) {
                String email = emailTextField.getText();
                String password = new String(passwordTextField.getPassword());
                String name = nameTextField.getText();
                String fname = fnameTextField.getText();
                String birthday = birthdayTextField.getText();
                String address = addressTextField.getText();
                String zipCode = zipCodeTextField.getText();
                String city = cityTextField.getText();
                LoginBackend.createAccount( name, fname, email, password, birthday, address, zipCode, city);
                frame.dispose();
            }
        });
    }

    private boolean validateInputFields(JTextField emailTextField, JPasswordField passwordTextField, JTextField nameTextField,
                                        JTextField fnameTextField, JTextField birthdayTextField, JTextField addressTextField, JTextField zipCodeTextField, JTextField cityTextField) {
        if (emailTextField.getText().isEmpty() || passwordTextField.getPassword().length == 0 || nameTextField.getText().isEmpty() ||
                fnameTextField.getText().isEmpty() || birthdayTextField.getText().isEmpty() || addressTextField.getText().isEmpty() ||
                zipCodeTextField.getText().isEmpty() || cityTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(emailTextField.getText());
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(null, "Please enter a valid email.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String birthdayRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        pattern = Pattern.compile(birthdayRegex);
        matcher = pattern.matcher(birthdayTextField.getText());
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(null, "Please enter a valid birthday (YYYY-MM-DD).", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void performAction(String action, JTextField userText, JPasswordField passwordText) {
        String email = userText.getText();
        String password = new String(passwordText.getPassword());

        switch (action) {
            case "login":
                if (LoginBackend.checkLogin(email, password)) {
                   mainGuiFrame.dispose();
                   frame.dispose();
                    new BookOverviewGui(books);
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Please check your credentials and try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "delete":
                if(LoginBackend.checkLogin(email, password)) {
                    LoginBackend.deleteAccount(email, password);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Delete failed. Please check your credentials and try again.", "Delete Failed", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
    }
}