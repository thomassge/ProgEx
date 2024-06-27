package Backend;

import DataStructure.Book;
import DataStructure.BookAdministration;
import DataStructure.Customer;
import DataStructure.Order;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Used to provide a centralized management facility to control basic backend functions
 */
public class Manager {

    static DatabaseConnection dbConn;
    static DataProcessor processor;
    static Customer user;
    static BookAdministration bookAdministration;

    /**
     * The class provides various static management methods to access the books from anywhere without special initialization.
     * Initializes the multiple classes which it uses and then loads all books into the system.
     */
    public Manager() {

        dbConn = new DatabaseConnection();
        processor = new DataProcessor();
        bookAdministration = new BookAdministration();

        try {
            loadBooks();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads and saves books from the database.
     * uses the connection to the database to load the books and lets the bookprocessor process and save them afterwards.
     *
     * @throws SQLException
     */
    public static void loadBooks() throws SQLException {
        bookAdministration.setBooks(processor.processBooks(dbConn.executeQuery(DatabaseConnection.Command.GetAllBooks)));
    }

    /**
     * Loads and saves the user's books from the database.
     * uses the connection to the database to load the books of the logged in user and lets the bookprocessor process and save them afterwards.
     *
     * @throws SQLException
     */
    public static boolean loadPersonalBooks() throws SQLException {

        if (user == null) {
            return false;
        }

        int id = user.getId();
        ArrayList<Order> orders = DataProcessor.processUserOrders(dbConn.executeQueryPrepared(dbConn.prepareWith1Int(DatabaseConnection.getCommand(DatabaseConnection.Command.GetBooksFromUser), id)));
        user.setOrders(orders);
        return true;
    }

    /**
     * Sets the user and then loads the user's borrowed books into the system
     *
     * @param customer
     * @return
     */
    public static boolean setUser(Customer customer) {
        user = customer;
        boolean b = false;
        try {
            b = loadPersonalBooks();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static Customer getUser() {
        return user;
    }

    public static ArrayList<Book> getBooks() {
        return bookAdministration.getBooks();
    }

    public static Book getBookById(int id) {
        return bookAdministration.getBookById(id);
    }
}



