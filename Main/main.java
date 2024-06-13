package Main;

import Backend.Manager;
import Frontend.MainGui;

import javax.swing.*;
/**
 * Programming Exercises Project 2024
 * @author Sara Abassi
 * @author Tim Bornemann
 * @author Eyüp Korkmaz
 * @author Thomas Levantis
 * @author Annika Zoe Schäffler
 * @author Finn Schäffler
 */
public class main {
    /**
     * Starts the application
     *
     * @param args start arguments
     */
    public static void main(String[] args) {
        Manager manager = new Manager();
        SwingUtilities.invokeLater(MainGui::new);

    }
}
