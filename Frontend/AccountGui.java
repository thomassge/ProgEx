package Frontend;

import DataStructure.Customer;

import javax.swing.*;
import java.awt.*;

public class AccountGui {

        private final Customer loggedInUser;

        public AccountGui(Customer user) {
            this.loggedInUser = user;
            createAccountFrame();
        }

        private void createAccountFrame() {
            JFrame accountFrame = new JFrame("Account Information");
            accountFrame.setLayout(new BorderLayout());
            accountFrame.setSize(600, 400);
            accountFrame.setLocationRelativeTo(null);

            JPanel infoPanel = new JPanel();
            infoPanel.add(new JLabel("Name: " + loggedInUser.getName()));
            infoPanel.add(new JLabel("Surname: " + loggedInUser.getFname()));
            infoPanel.add(new JLabel("Email: " + loggedInUser.geteMail()));

            JButton editButton = new JButton("Edit Information");
            editButton.addActionListener(e -> openEditWindow());

            JButton logoutButton = new JButton("Logout");
            logoutButton.addActionListener(e -> {
                accountFrame.dispose();
                new MainGui();
            });

            accountFrame.add(infoPanel, BorderLayout.CENTER);
            accountFrame.add(editButton, BorderLayout.SOUTH);
            accountFrame.add(logoutButton, BorderLayout.NORTH);
            accountFrame.setVisible(true);
        }

        private void openEditWindow() {
            JFrame editFrame = new JFrame("Edit Information");
            editFrame.setSize(400, 300);
            editFrame.setLocationRelativeTo(null);

            JTextField nameField = new JTextField(loggedInUser.getName());
            JTextField surnameField = new JTextField(loggedInUser.getFname());
            JTextField emailField = new JTextField(loggedInUser.geteMail());

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(e -> {
                loggedInUser.setName(nameField.getText());
                loggedInUser.setFname(surnameField.getText());
                loggedInUser.seteMail(emailField.getText());
                editFrame.dispose();
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> editFrame.dispose());

            editFrame.add(nameField);
            editFrame.add(surnameField);
            editFrame.add(emailField);

            editFrame.add(saveButton);
            editFrame.add(cancelButton);
            editFrame.setVisible(true);
        }
}