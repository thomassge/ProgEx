package Main;

import Frontend.MainGui;
import javax.swing.*;

public class main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGui());
    }
}
