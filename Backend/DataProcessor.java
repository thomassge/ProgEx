package Backend;

import DataStructure.Book;
import DataStructure.Customer;
import DataStructure.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * parses the sql resultsets so that the data can be used in the system
 */
public class DataProcessor {

    /**
     * processes the user's borrowed books and reads the data from the table and creates a list of orders
     *
     * @param rs
     * @return a list of orders
     * @throws SQLException
     */
    public static ArrayList<Order> proccesUserOrders(ResultSet rs) throws SQLException {
        ArrayList<Order> orders = new ArrayList<Order>();

        while (rs.next()) {
            Order order = new Order();
            int bookingId = rs.getInt("booking_id");
            order.setBookingId(bookingId);
            int bookid = rs.getInt("book_id");
            order.setBook(Manager.getBooks().stream().filter(b -> b.getId() == bookid).findFirst().orElse(null));
            Date lendingDate = rs.getDate("lending_date");
            Date returnDate = rs.getDate("deadline");
            order.setOrderDate(lendingDate);
            order.setReturnDate(returnDate);
            orders.add(order);
        }
        return orders;
    }

    /**
     * processes the user to do this the function parses the data from the table and creates a list of orders
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public static Customer processCustomer(ResultSet rs) throws SQLException {
        Customer customer = null;

        if (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String fname = rs.getString("fname");
            String email = rs.getString("email");
            String password = rs.getString("password");
            Date birthday = rs.getDate("birthday");
            String address = rs.getString("address");
            String zipCode = rs.getString("zip_code");
            String city = rs.getString("city");

            customer = new Customer(id, name, fname, email, password, birthday, address, zipCode, city);
        }

        return customer;
    }

    /**
     * processes all books therefore the method reads the data from the table and creates a list of orders
     *
     * @param rs
     * @return all books in a list
     * @throws SQLException
     */
    public ArrayList<Book> processBooks(ResultSet rs) throws SQLException {

        ArrayList<Book> books = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            String isbn = rs.getString("isbn");
            Date releaseDate = rs.getDate("release_date");
            String publisher = rs.getString("publisher");
            String genre = rs.getString("genre");
            String description = rs.getString("description");
            int qty = rs.getInt("qty");
            books.add(new Book(id, title, author, isbn, releaseDate, publisher, genre, description, qty));
        }
        return books;
    }
}
