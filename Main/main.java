package Main;

import Frontend.MainGui;

import javax.swing.*;
import java.io.IOException;

public class main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGui::new);
    }
}
