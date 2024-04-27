package Main;

import Backend.DatabaseConnection;
import Frontend.MainGui;

import javax.swing.*;
import java.io.IOException;

public class main {
    private DatabaseConnection db;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(MainGui::new);
    }
}
