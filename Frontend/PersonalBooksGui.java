package Frontend;

import DataStructure.Book;
import DataStructure.Orders;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalBooksGui {
    private JFrame PersonalBooksFrame;
    private JTable ordersTable;

    public PersonalBooksGui(List<Orders> orders, ArrayList<Book> books) {
        createPersonalBooksFrame();
        createPersonalBooksTable(orders, books);
    }

    private void createPersonalBooksFrame() {
        PersonalBooksFrame = new JFrame("Personal Orders");
        PersonalBooksFrame.setLayout(new BorderLayout());
        PersonalBooksFrame.setSize(600, 400);
        PersonalBooksFrame.setLocationRelativeTo(null);
        PersonalBooksFrame.setVisible(true);
    }

    private void createPersonalBooksTable(List<Orders> orders, ArrayList<Book> books) {
        String[] columnNames = {"Book Title", "Order Date", "Return Date"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Orders order : orders) {
            Object[] rowData = {books.getFirst().getTitle(), order.getOrderdate(), order.getReturndate()};
            tableModel.addRow(rowData);
        }

        ordersTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(ordersTable);
        PersonalBooksFrame.add(scrollPane, BorderLayout.CENTER);
    }
}
