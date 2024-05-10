package Frontend;

import Backend.LoginBackend;
import Backend.Manager;
import DataStructure.Customer;

import javax.swing.*;
import java.awt.*;

public class AccountGui {

    Customer loggedInUser = Manager.GetUser();
    JFrame accountFrame = new JFrame();

    public AccountGui() {
        try {
            createAccountFrame();
            System.out.println("Account GUI has been initialized.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error initializing Account GUI: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void createAccountFrame() {
        accountFrame = new JFrame("Account Information");
        accountFrame.setLayout(new BorderLayout());
        accountFrame.setSize(300, 300);
        accountFrame.setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel("Name: " + loggedInUser.getName()));
        infoPanel.add(new JLabel("Surname: " + loggedInUser.getFname()));
        infoPanel.add(new JLabel("Email: " + loggedInUser.geteMail()));
        infoPanel.add(new JLabel("Birthday: " + loggedInUser.getBirthday()));
        infoPanel.add(new JLabel("Address: " + loggedInUser.getAddress()));
        infoPanel.add(new JLabel("Zip Code: " + loggedInUser.getZip_code()));
        infoPanel.add(new JLabel("City: " + loggedInUser.getCity()));

        JButton editButton = new JButton("Edit Information");
        editButton.setBounds(100, 265, 80, 25);
        editButton.addActionListener(e -> openEditWindow());

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(10, 265, 80, 25);
        logoutButton.addActionListener(e -> {
            accountFrame.dispose();
            new MainGui();
        });
        JPanel buttonPanel = new JPanel(new FlowLayout());

        buttonPanel.add(editButton);
        buttonPanel.add(logoutButton);
        accountFrame.add(buttonPanel, BorderLayout.SOUTH);
        accountFrame.add(infoPanel, BorderLayout.CENTER);
        accountFrame.setVisible(true);
    }

    private void openEditWindow() {
        JFrame editFrame = new JFrame("Edit Information");
        editFrame.setSize(300, 375);
        editFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        editFrame.add(panel);

        JTextField nameField = createLabeledTextField(panel, "Name", 20, loggedInUser.getName());
        JTextField firstNameField = createLabeledTextField(panel, "First Name", 50, loggedInUser.getFname());
        JTextField emailField = createLabeledTextField(panel, "Email", 80, loggedInUser.geteMail());
        JTextField birthdayField = createLabeledTextField(panel, "Birthday", 110, loggedInUser.getBirthday().toString());
        JTextField addressField = createLabeledTextField(panel, "Address", 140, loggedInUser.getAddress());
        JTextField zipCodeField = createLabeledTextField(panel, "Zip Code", 170, loggedInUser.getZip_code());
        JTextField cityField = createLabeledTextField(panel, "City", 200, loggedInUser.getCity());

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(10, 265, 80, 25);
        saveButton.addActionListener(e -> {
            loggedInUser.setName(nameField.getText());
            loggedInUser.setFname(firstNameField.getText());
            loggedInUser.seteMail(emailField.getText());

            String birthdayText = birthdayField.getText();
            if (birthdayText.matches("\\d{4}-\\d{2}-\\d{2}")) {
                loggedInUser.setBirthday(java.sql.Date.valueOf(birthdayText));
            } else {
                JOptionPane.showMessageDialog(editFrame, "Invalid date format. Please use yyyy-mm-dd.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            loggedInUser.setAddress(addressField.getText());
            loggedInUser.setZip_code(zipCodeField.getText());
            loggedInUser.setCity(cityField.getText());
            editFrame.dispose();
            LoginBackend.editAccount(loggedInUser, loggedInUser.getName(), loggedInUser.getFname(), loggedInUser.geteMail(),
                                    loggedInUser.getPassword(), birthdayText, loggedInUser.getAddress(), loggedInUser.getZip_code(), loggedInUser.getCity());
            accountFrame.dispose();
            new AccountGui();
        });
        panel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(100, 265, 80, 25);
        cancelButton.addActionListener(e -> editFrame.dispose());
        panel.add(cancelButton);

        editFrame.setVisible(true);
    }
    private JTextField createLabeledTextField(JPanel panel, String labelText, int y, String text) {
        JLabel label = new JLabel(labelText);
        label.setBounds(10, y, 80, 25);
        panel.add(label);

        JTextField textField = new JTextField(20);
        textField.setBounds(100, y, 165, 25);
        textField.setText(text);
        panel.add(textField);

        return textField;
    }
}