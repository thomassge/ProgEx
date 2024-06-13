package Frontend;

import Backend.BookIsNotExtendableException;
import Backend.LendBook;
import Backend.Manager;
import DataStructure.Book;
import DataStructure.Customer;
import DataStructure.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PersonalBooksGui {
    private final ArrayList<Book> books;
    private final BookOverviewGui bookOverviewGui;
    private JFrame personalBooksFrame;
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    Customer loggedInUser = Manager.getUser();

    public PersonalBooksGui(ArrayList<Book> books, BookOverviewGui bookOverviewGui) {
        this.books = books;
        createPersonalBooksFrame();
        createPersonalBooksTable();
        this.bookOverviewGui = bookOverviewGui;
    }

    private void createPersonalBooksFrame() {
        personalBooksFrame = new JFrame("Personal Order");
        personalBooksFrame.setLayout(new BorderLayout());
        personalBooksFrame.setSize(600, 400);
        personalBooksFrame.setLocationRelativeTo(null);
        personalBooksFrame.setVisible(true);
    }

    private void createPersonalBooksTable() {
        String[] columnNames = {"Book Title", "Order Date", "Return Date"};

         tableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable ( int row, int column){
            return false;
        }
        };

        List<Order> orders = loggedInUser.getOrders();
        for (Order order : orders) {
            Book book= order.getBook();
            Object[] rowData = {book.getTitle(), order.getOrderDate(), order.getReturnDate()};
            tableModel.addRow(rowData);
        }

        ordersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        personalBooksFrame.add(scrollPane, BorderLayout.CENTER);

        addMouseListener();
    }

    private void addMouseListener() {
        ordersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    int viewRow = target.getSelectedRow();
                    if (viewRow != -1) {
                        int modelRow = target.convertRowIndexToModel(viewRow);
                        String title = target.getModel().getValueAt(modelRow, 0).toString();
                        Book selectedBook = findBookByTitle(title);
                        if (selectedBook != null) {
                            createExtendReturnFrame(selectedBook);
                        } else {
                            System.out.println("No book found with ID: " + title);
                        }
                    }
                }
            }
        });
    }

    private Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    private void createExtendReturnFrame(Book book) {
        JFrame extendReturnFrame = new JFrame("Book Details");
        extendReturnFrame.setSize(200, 200);
        extendReturnFrame.setLocationRelativeTo(null);

        JButton extendButton = new JButton("Extend Book");
        extendButton.addActionListener(e -> {
            try {
                LendBook.extendBook(Manager.getUser().getOrderForBook(book.getId()));
                updateTable();
            } catch (BookIsNotExtendableException extendableException) {
                JOptionPane.showMessageDialog(null,
                        "The maximum extension time has been reached.",
                        "Extension Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton returnButton = new JButton("Return Book");
        returnButton.addActionListener(e -> {
            LendBook.returnBook(Manager.getUser().getOrderForBook(book.getId()));
            extendReturnFrame.dispose();
            bookOverviewGui.refreshBooks();
            updateTable();
        });

        JPanel panel = new JPanel();
        panel.add(extendButton);
        panel.add(returnButton);

        extendReturnFrame.add(panel);
        extendReturnFrame.setVisible(true);
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        List<Order> orders = loggedInUser.getOrders();
        for (Order order : orders) {
            Book tempBook = order.getBook();
            Object[] rowData = {tempBook.getTitle(), order.getOrderDate(), order.getReturnDate()};
            tableModel.addRow(rowData);
        }
        tableModel.fireTableDataChanged();
    }
}
