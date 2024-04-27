package Frontend;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BookOverviewGui implements ActionListener {

    private JFrame bookOverviewFrame;
    private final String[] columnNames = {"ID", "Title", "Author", "Genre", "Quantity", "Price"};
    private final int[] columnWidth = {-100, 50, 50, 50, -100, -80};
    private Object[][] bookMenuData = {
            {1, "Mein Tagebuch", "Angela Merkel", "langweilig", 99, 19.99},
            {2, "To Kill a Mockingbird", "Harper Lee", "Fiction", 50, 10.99},
            {3, "1984", "George Orwell", "Fiction", 80, 12.99}
    };
    private JMenuBar bookOverviewMenuBar;
    private JTable bookOverviewTable;
    private JTextField searchField;

    public BookOverviewGui() {
        createFrame();
        createMenuBar();
        createTable();
        createSearchBar();

        JScrollPane bookOverviewScrollPane = new JScrollPane(bookOverviewTable);
        bookOverviewFrame.setJMenuBar(bookOverviewMenuBar);
        bookOverviewFrame.add(bookOverviewScrollPane);
    }

    private void createFrame() {
        bookOverviewFrame = new JFrame("Book Overview");
        bookOverviewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bookOverviewFrame.setSize(800, 600);
        bookOverviewFrame.setLocationRelativeTo(null);
        bookOverviewFrame.setVisible(true);

        JScrollPane bookOverviewScrollPane = new JScrollPane(bookOverviewTable);
        bookOverviewFrame.add(bookOverviewScrollPane);
    }

    private void createMenuBar() {
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
            new AccountGui();
        } else if ("Personal Books".equals(e.getActionCommand())) {
            new PersonalBooksGui();
        }
    }

    private void createTable() {
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

    private void addMouseListener(){
        bookOverviewTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int id = Integer.parseInt(target.getModel().getValueAt(row, 0).toString());
                    if (row != -1) {
                        new DetailedBookView(id);
                    }
                }
            }
        });
    }

    private void createSearchBar() {
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
