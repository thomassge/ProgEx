package Frontend;

import DataStructure.Book;
import DataStructure.Customer;
import DataStructure.Orders;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class BookOverviewGui implements ActionListener {

    private JFrame bookOverviewFrame;
    private final String[] columnNames = {"ID", "Title", "Author", "Genre", "Quantity"};
    private final int[] columnWidth = {-100, 50, 50, 50, -100,};
    private Object[][] bookMenuData;
    private JMenuBar bookOverviewMenuBar;
    private JTable bookOverviewTable;
    private JTextField searchField;
    private final ArrayList<Book> books;
    private List<Orders> orders;
    private final Customer loggedInUser;

    public BookOverviewGui(ArrayList <Book> books, Customer loggedInUser) {
        this.books = books;
        this.loggedInUser = loggedInUser;
        initializeBookOverviewData(books);
        createBookOverviewFrame();
        createBookOverviewMenuBar();
        createBookOverviewTable();
        createBookOverviewSearchBar();

        JScrollPane bookOverviewScrollPane = new JScrollPane(bookOverviewTable);
        bookOverviewFrame.setJMenuBar(bookOverviewMenuBar);
        bookOverviewFrame.add(bookOverviewScrollPane);
    }

    private void initializeBookOverviewData(ArrayList<Book> books){
        if (books == null) {
            books = new ArrayList<>();
        }

        bookMenuData = new Object[books.size()][columnNames.length];

        for (int selectedBook = 0; selectedBook < books.size(); selectedBook++) {
            bookMenuData[selectedBook][0] = books.get(selectedBook).getId();
            bookMenuData[selectedBook][1] = books.get(selectedBook).getTitle();
            bookMenuData[selectedBook][2] = books.get(selectedBook).getAuthor();
            bookMenuData[selectedBook][3] = books.get(selectedBook).getGenre();
            bookMenuData[selectedBook][4] = books.get(selectedBook).getQty();
        }
    }
    private void createBookOverviewFrame() {
        bookOverviewFrame = new JFrame("Book Overview");
        bookOverviewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bookOverviewFrame.setSize(800, 600);
        bookOverviewFrame.setLocationRelativeTo(null);
        bookOverviewFrame.setVisible(true);

        JScrollPane bookOverviewScrollPane = new JScrollPane(bookOverviewTable);
        bookOverviewFrame.add(bookOverviewScrollPane);
    }

    private void createBookOverviewMenuBar() {
        bookOverviewMenuBar = new JMenuBar();
        JMenu menu = new JMenu("Overview");
        bookOverviewMenuBar.add(menu);
        addItemToMenuWithActionCommand(menu, "Account", "AccountGui");
        addItemToMenuWithActionCommand(menu, "Personal Books", "PersonalBooksGui");
    }

    private void addItemToMenuWithActionCommand(JMenu menu, String itemName, String actionCmd) {
        JMenuItem bookOverviewItem = new JMenuItem(itemName);
        bookOverviewItem.setActionCommand(actionCmd);
        bookOverviewItem.addActionListener( this);
        menu.add(bookOverviewItem);
    }

    public void actionPerformed(ActionEvent e) {
        if ("Account".equals(e.getActionCommand())) {
            new AccountGui(loggedInUser);
        } else if ("Personal Books".equals(e.getActionCommand())) {
            new PersonalBooksGui(orders, books);
        }
    }

    private void createBookOverviewTable() {
        bookOverviewTable = new JTable(bookMenuData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        TableColumnModel columnModel = bookOverviewTable.getColumnModel();

        for (int selectedColumn = 0; selectedColumn < columnWidth.length; selectedColumn++) {
            TableColumn column = columnModel.getColumn(selectedColumn);
            column.setPreferredWidth((column.getPreferredWidth() + columnWidth[selectedColumn]));
        }

        addMouseListener();
    }
/* Funktion hatte einen fehler, wenn man in der suche gesucht hat und dann auf die detailedBookView klickt, wurde der falsche index übergeben
    da durch das sortieren die ursprügnlichen indexe der zeilen beibehalten werden und das dann nicht mit der angezeigten zeile übereinstimmt.

    private void addMouseListener(){
        bookOverviewTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int id = Integer.parseInt(target.getModel().getValueAt(row, 0).toString());
                    if (row != -1) {
                        Book selectedBook = books.get(row);
                        new DetailedBookView(selectedBook);
                    }
                }
            }
        });
    }
*/
private void addMouseListener() {
    bookOverviewTable.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) {
                JTable target = (JTable) e.getSource();
                int viewRow = target.getSelectedRow(); // Zeilenindex in der angezeigten Tabelle
                if (viewRow != -1) {
                    int modelRow = target.convertRowIndexToModel(viewRow); // Konvertiere zum Modellindex
                    int id = Integer.parseInt(target.getModel().getValueAt(modelRow, 0).toString());
                    Book selectedBook = findBookById(id);
                    if (selectedBook != null) {
                        new DetailedBookView(selectedBook);
                    } else {
                        System.out.println("No book found with ID: " + id);
                    }
                }
            }
        }
    });
}


    private Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }


    private void createBookOverviewSearchBar() {
        JPanel panel = new JPanel();
        createSearchField();
        panel.add(searchField);
        bookOverviewFrame.add(panel, BorderLayout.NORTH);
    }

    private void createSearchField() {
        searchField = new JTextField(15);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable(searchField.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable(searchField.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable(searchField.getText());
            }
        });
    }

    private void filterTable(String searchText) {
        RowSorter<? extends TableModel> sorter = bookOverviewTable.getRowSorter();
        if (sorter == null) {
            sorter = new TableRowSorter<>(bookOverviewTable.getModel());
            bookOverviewTable.setRowSorter(sorter);
        }
        ((TableRowSorter<? extends TableModel>) sorter).setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
    }
}
