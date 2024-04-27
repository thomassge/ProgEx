package Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGui {

    private JFrame mainGuiFrame = new JFrame();

    public MainGui() {
        createFrame();
        //createBackground();
        createGridLayout();
    }

    void createFrame(){
        mainGuiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGuiFrame.setTitle("Welcome to our Online Library");
        mainGuiFrame.setSize(800, 600);
        mainGuiFrame.setLocationRelativeTo(null);
        mainGuiFrame.setVisible(true);
    }

    /*

    void createBackground() {
        ImageIcon backgroundImageIcon = new ImageIcon("background.png");
        JLabel backgroundLabel = new JLabel();

        //mainGuiFrame.add(backgroundLabel);
    }

     */

    void createGridLayout(){
        JPanel flowPanel = new JPanel(new FlowLayout());
        JPanel boxPanel = new JPanel();
        JButton LogInButton = new JButton("Log In");
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));


        Dimension logInButtonSize = new Dimension(110, 50);
        LogInButton.setPreferredSize(logInButtonSize);

        flowPanel.add(LogInButton);
        mainGuiFrame.add(flowPanel, BorderLayout.SOUTH);

        LogInButton.addActionListener((ActionEvent e) -> {
            new BookOverviewGui();
            mainGuiFrame.dispose();
        });
    }

}
