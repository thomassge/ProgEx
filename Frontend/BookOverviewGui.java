package Frontend;

import Backend.Manager;
import DataStructure.Book;

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

public class BookOverviewGui implements ActionListener {

    private final String[] columnNames = {"ID", "Title", "Author", "Genre", "Quantity"};
    private final int[] columnWidth = {-100, 50, 50, 50, -100,};
    private final BookOverviewGui thisBookOverviewGui = this;
    private JFrame bookOverviewFrame;
    private Object[][] bookMenuData;
    private JMenuBar bookOverviewMenuBar;
    private JTable bookOverviewTable;
    private JTextField bookOverviewSearchField;
    private ArrayList<Book> books;

    public BookOverviewGui(ArrayList <Book> books) {
        this.books = books;
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
        addItemToMenuWithActionCommand(menu, "Logout", "Logout");
    }

    private void addItemToMenuWithActionCommand(JMenu menu, String itemName, String actionCmd) {
        JMenuItem bookOverviewItem = new JMenuItem(itemName);
        bookOverviewItem.setActionCommand(actionCmd);
        bookOverviewItem.addActionListener( this);
        menu.add(bookOverviewItem);
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

    private void addMouseListener() {
        bookOverviewTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    int viewRow = target.getSelectedRow();
                    if (viewRow != -1) {
                        int modelRow = target.convertRowIndexToModel(viewRow);
                        int id = Integer.parseInt(target.getModel().getValueAt(modelRow, 0).toString());
                        Book selectedBook = findBookById(id);
                        if (selectedBook != null) {
                            new DetailedBookView(selectedBook, thisBookOverviewGui);
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
        JPanel BookOverviewGuiPanel = new JPanel();
        createSearchField();
        BookOverviewGuiPanel.add(bookOverviewSearchField);
        bookOverviewFrame.add(BookOverviewGuiPanel, BorderLayout.NORTH);
    }

    private void createSearchField() {
        bookOverviewSearchField = new JTextField(15);
        bookOverviewSearchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable(bookOverviewSearchField.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable(bookOverviewSearchField.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable(bookOverviewSearchField.getText());
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

    public void actionPerformed(ActionEvent e) {
        if ("AccountGui".equals(e.getActionCommand())) {
            new AccountGui();
        } else if ("PersonalBooksGui".equals(e.getActionCommand())) {
            new PersonalBooksGui(books, thisBookOverviewGui);
        } else if ("Logout".equals(e.getActionCommand())) {
            new MainGui();
        }
    }

    public void refreshBooks() {
        this.books = Manager.getBooks();
        initializeBookOverviewData(books);

        for (int i = 0; i < bookOverviewTable.getRowCount(); i++) {
            for (int j = 0; j < bookOverviewTable.getColumnCount(); j++) {
                bookOverviewTable.setValueAt(null, i, j);
            }
        }

        for (int i = 0; i < bookMenuData.length; i++) {
            for (int j = 0; j < bookMenuData[i].length; j++) {
                bookOverviewTable.setValueAt(bookMenuData[i][j], i, j);
            }
        }
        ((AbstractTableModel) bookOverviewTable.getModel()).fireTableDataChanged();
    }
}