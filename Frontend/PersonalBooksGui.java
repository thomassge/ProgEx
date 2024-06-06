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
    private JFrame PersonalBooksFrame;
    private JTable ordersTable;
    Customer loggedInUser = Manager.getUser();
    private final ArrayList<Book> books;
    private DefaultTableModel tableModel;
    private BookOverviewGui bookOverviewGui;
    public PersonalBooksGui(List<Order> orders, ArrayList<Book> books, BookOverviewGui bookOverviewGui) {
        this.books = books;
        createPersonalBooksFrame();
        createPersonalBooksTable(books);
        this.bookOverviewGui = bookOverviewGui;
    }

    private void createPersonalBooksFrame() {
        PersonalBooksFrame = new JFrame("Personal Order");
        PersonalBooksFrame.setLayout(new BorderLayout());
        PersonalBooksFrame.setSize(600, 400);
        PersonalBooksFrame.setLocationRelativeTo(null);
        PersonalBooksFrame.setVisible(true);
    }

    private void createPersonalBooksTable(ArrayList<Book> books) {
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
        PersonalBooksFrame.add(scrollPane, BorderLayout.CENTER);

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
                            createBookDetailsFrame(selectedBook);
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

    private void createBookDetailsFrame(Book book) {
        JFrame bookDetailsFrame = new JFrame("Book Details");
        bookDetailsFrame.setSize(200, 200);
        bookDetailsFrame.setLocationRelativeTo(null);

        JButton extendButton = new JButton("Extend Book");
        extendButton.addActionListener(e -> {
            try {
                LendBook.extendBook(Manager.getUser().getOrderForBook(book.getId()));
                tableModel.setRowCount(0);
                List<Order> orders = loggedInUser.getOrders();
                for (Order order : orders) {
                    Book tempBook = order.getBook();
                    Object[] rowData = {tempBook.getTitle(), order.getOrderDate(), order.getReturnDate()};
                    tableModel.addRow(rowData);
                }
                tableModel.fireTableDataChanged();
            }catch (BookIsNotExtendableException extendableException ){
                JOptionPane.showMessageDialog(null,
                        "The maximum extension time has been reached.",
                        "Extension Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton returnButton = new JButton("Return Book");
        returnButton.addActionListener(e -> {
            LendBook.returnBook(Manager.getUser().getOrderForBook(book.getId()));
            tableModel.setRowCount(0);
            List<Order> orders = loggedInUser.getOrders();
            for (Order order : orders) {
                Book tempBook= order.getBook();
                Object[] rowData = {tempBook.getTitle(), order.getOrderDate(), order.getReturnDate()};
                tableModel.addRow(rowData);
            }
            tableModel.fireTableDataChanged();
            bookDetailsFrame.dispose();
            bookOverviewGui.refreshBooks();
        });

        JPanel panel = new JPanel();
        panel.add(extendButton);
        panel.add(returnButton);

        bookDetailsFrame.add(panel);
        bookDetailsFrame.setVisible(true);
    }
}
