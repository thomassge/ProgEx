/*

package Frontend;

import DataStructure.Book;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DetailedBookView implements ActionListener {
    private JFrame DetailedBookView;
    private JMenuBar DetailedBookMenuBar;
    private String[] rowNames = {"ID", "Title", "Author", "ISBN", "Release date", "Publisher", "Genre", "Description", "Qty"};
    private ArrayList<Book> books;

    public DetailedBookView(ArrayList<Book> books, int id) {
        this.books = books;
        createDetailedBookTableFrame();
        createDetailedBookTableMenuBar();
        createDetailedBookTable();
    }

    private void createDetailedBookTableFrame() {
        DetailedBookView = new JFrame("Detailed Book");
        DetailedBookView.setSize(800, 600);
        DetailedBookView.setLocationRelativeTo(null);
        DetailedBookView.setVisible(true);
    }

    private void createDetailedBookTableMenuBar() {
        DetailedBookMenuBar = new JMenuBar();
        JMenu menu = new JMenu("Detailed Book View");
        addItemToMenuWithActionCommand(menu, "Back", "back");
        DetailedBookMenuBar.add(menu);
    }

    private void addItemToMenuWithActionCommand(JMenu menu, String itemName, String actionCmd) {
        JMenuItem detailedBookItem = new JMenuItem(itemName);
        detailedBookItem.setActionCommand(actionCmd);
        detailedBookItem.addActionListener( this);
        menu.add(detailedBookItem);
    }

    public void actionPerformed(ActionEvent e) {
        if ("Back".equals(e.getActionCommand())) {
            DetailedBookView.dispose();
        }
    }

    private void createDetailedBookTable() {
        detailedBookTable = new JTable(rowNames, bookData) {
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

}

 */
