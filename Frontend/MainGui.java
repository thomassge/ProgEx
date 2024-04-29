package Frontend;

import Backend.Magager;
import DataStructure.Book;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainGui {

    private JFrame mainGuiFrame = new JFrame();
    private ArrayList<Book> books;

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

        BufferedImage backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/Frontend/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (backgroundImage != null) {
            JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
            backgroundLabel.setPreferredSize(new Dimension(800, 600));
            mainGuiFrame.setContentPane(backgroundLabel);
        }

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
