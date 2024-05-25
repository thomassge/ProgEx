package Frontend;

import Backend.BookUnavailableException;
import Backend.LendBook;
import Backend.Manager;
import DataStructure.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetailedBookView implements ActionListener {
    private JFrame detailedBookView;
    private final String[] rowNames = {"ID", "Title", "Author", "ISBN", "Release date", "Publisher", "Genre", "Description", "Qty"};
    private Object [][] detailedBookViewData;
    private Book selectedBook;
    DefaultTableModel tableModel;

    private BookOverviewGui bookOverviewGui;

    public DetailedBookView(Book selectedBook, BookOverviewGui bookOverviewGui) {
        this.selectedBook = selectedBook;
        initializeDetailedBookViewData(selectedBook);
        createDetailedBookTableFrame();
        createDetailedBookTable();
        createDetailedBookTableMenuBar();
        addLendButton();
        this.bookOverviewGui = bookOverviewGui;
    }

    private void initializeDetailedBookViewData(Book selectedBook) {
        detailedBookViewData = new Object[rowNames.length][2];

        detailedBookViewData[0][0] = selectedBook.getId();
        detailedBookViewData[1][0] = selectedBook.getTitle();
        detailedBookViewData[2][0] = selectedBook.getAuthor();
        detailedBookViewData[3][0] = selectedBook.getIsbn();
        detailedBookViewData[4][0] = selectedBook.getReleaseDate();
        detailedBookViewData[5][0] = selectedBook.getPublisher();
        detailedBookViewData[6][0] = selectedBook.getGenre();
        detailedBookViewData[7][0] = selectedBook.getDescription();
        detailedBookViewData[8][0] = selectedBook.getQty();
    }
    private void createDetailedBookTableFrame() {
        detailedBookView = new JFrame("Detailed Book");
        detailedBookView.setSize(800, 300);
        detailedBookView.setLocationRelativeTo(null);
        detailedBookView.setVisible(true);
    }

    private void createDetailedBookTableMenuBar() {
        JMenuBar detailedBookMenuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        addItemToMenuWithActionCommand(menu);
        detailedBookMenuBar.add(menu);
        detailedBookView.setJMenuBar(detailedBookMenuBar);
    }

    private void addItemToMenuWithActionCommand(JMenu menu) {
        JMenuItem detailedBookItem = new JMenuItem("Back");
        detailedBookItem.setActionCommand("back");
        detailedBookItem.addActionListener( this);
        menu.add(detailedBookItem);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JMenuItem) {
            JMenuItem source = (JMenuItem) e.getSource();
            if ("Back".equals(source.getText())) {
                detailedBookView.dispose();
            }
        }
    }

    private void createDetailedBookTable() {
       JPanel panel = new JPanel(new BorderLayout());

        JTable defaultBookAttributes = new JTable(new DefaultTableModel(rowNames.length, 1));
        tableModel = (DefaultTableModel) defaultBookAttributes.getModel();
        for(int i = 0; i< rowNames.length;i++){
            tableModel.setValueAt(rowNames[i], i, 0);
        }

        JTable selectedBookValues = new JTable(detailedBookViewData, new String[]{"Value"});
        selectedBookValues.setEnabled(false);
        selectedBookValues.setTableHeader(null);

        panel.add(defaultBookAttributes, BorderLayout.WEST);
        panel.add(selectedBookValues, BorderLayout.CENTER);

        detailedBookView.add(panel);
    }

    private void addLendButton() {
        JButton lendButton = new JButton("Lend");
        lendButton.addActionListener(e -> {
            try {
                LendBook.lendBook(selectedBook);
                bookOverviewGui.refreshBooks();
                selectedBook = Manager.GetBookById(selectedBook.getId());
                detailedBookViewData[8][0] = selectedBook.getQty();
                tableModel.fireTableDataChanged();
                createDetailedBookTable();

                JOptionPane.showMessageDialog(null, "Book has been lent successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (BookUnavailableException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        detailedBookView.add(lendButton, BorderLayout.SOUTH);
    }

}