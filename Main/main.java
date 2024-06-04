package Main;

import Backend.Manager;
import Frontend.MainGui;
import javax.swing.*;

public class main {

//Programming Exercises Project 2024 
//Group members who participated in creating the Project: Sara Abassi, Tim Bornemann, Eyüp Korkmaz, Thomas Levantis, Annika Zoe Schäffler, Finn Schäffler

    public static void main(String[] args) {
        Manager manager = new Manager();
        SwingUtilities.invokeLater(() -> new MainGui());

    }
}
