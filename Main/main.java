package Main;

import Backend.Manager;
import Frontend.MainGui;
import javax.swing.*;

public class main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        SwingUtilities.invokeLater(() -> new MainGui());

    }
}
