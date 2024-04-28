package Frontend;

import Backend.Magager;
import DataStructure.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainGui {

    private JFrame mainGuiFrame = new JFrame();
    private ArrayList<Book> books = new ArrayList<Book>();

    public MainGui() {

        Magager manager = new Magager();
        try{
            manager.LoadBooks();
        }catch (SQLException e){}

        this.books = manager.GetBooks();
        createMainGuiFrame();
        createMainGuiLayout();
    }

    private void createMainGuiFrame(){
        mainGuiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGuiFrame.setTitle("Welcome to our Online Library");
        mainGuiFrame.setSize(800, 600);
        mainGuiFrame.setLocationRelativeTo(null);

        ImageIcon backgroundImageIcon = new ImageIcon("background.png");
        JLabel backgroundLabel = new JLabel(backgroundImageIcon);
        mainGuiFrame.setContentPane(backgroundLabel);
        System.out.println("TEST");

        mainGuiFrame.setLayout(new BorderLayout());

        mainGuiFrame.setVisible(true);
    }

    private void createMainGuiLayout(){
        JPanel flowPanel = new JPanel(new FlowLayout());
        JPanel boxPanel = new JPanel();
        JButton LogInButton = new JButton("Log In");
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));

        Dimension logInButtonSize = new Dimension(110, 50);
        LogInButton.setPreferredSize(logInButtonSize);

        flowPanel.add(LogInButton);
        mainGuiFrame.add(flowPanel, BorderLayout.SOUTH);

        LogInButton.addActionListener((ActionEvent e) -> {
            new BookOverviewGui(books);
            mainGuiFrame.dispose();
        });
    }
}
