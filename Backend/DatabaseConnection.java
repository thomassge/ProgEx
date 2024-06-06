package Backend;

import java.sql.*;
import java.util.Date;

/**
 * Establishes a connection to the database and has methods for querying the database.
 */
public class DatabaseConnection {

    //sql statements for database
    static String getCustomerByEmailAndPassword = "SELECT * FROM customer WHERE email = ? AND password = ?";
    static String getAllBooks = "SELECT book.*, qty.qty FROM book, qty WHERE book.id = qty.book_id";
    static String createAccount = "INSERT INTO customer (name, fname, email, password, birthday, address, zip_code, city) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    static String deleteAccount = "DELETE FROM customer WHERE email = ? AND password = ?";
    static String editAccount = "UPDATE customer SET name = ?, fname = ?, email = ?, password = ?, birthday = ?, address = ?, zip_code = ?, city = ? WHERE email = ?";
    static String getCustomer = "SELECT * FROM customer WHERE id = ?";
    static String getBooksFromUser = "SELECT * FROM lent_books WHERE customer_id = ?";
    static String insertLentBook = "INSERT INTO lent_books (book_id, customer_id, deadline, lending_date) VALUES (?, ?, ?, ?)";
    static String updateLentBook = "UPDATE lent_books SET deadline = ? WHERE booking_id = ?";
    static String removeLentBook = "DELETE FROM lent_books WHERE booking_id = ?";
    static String updateBookQty = "UPDATE qty SET qty = ? WHERE book_id = ?";
    // Database information
    private final String url = "jdbc:mysql://localhost:8080/Database?useSSL=false";
    private final String user = "root";
    private final String password = "12345";
    private Connection conn = null;

    /**
     * After creating the class, it connects directly to the database
     */
    public DatabaseConnection() {
        connect();
    }

    /**
     * Used to allow only certain predefined commands for database queries
     *
     * @param command enum with the allowed commands
     * @return sql statement
     */
    public static String getCommand(Command command) {
        switch (command) {

            case GetAllBooks:
                return getAllBooks;
            case CreateAccount:
                return createAccount;
            case DeleteAccount:
                return deleteAccount;
            case EditAccount:
                return editAccount;
            case GetCustomer:
                return getCustomer;
            case GetBooksFromUser:
                return getBooksFromUser;
            case InsertLentBook:
                return insertLentBook;
            case UpdateLentBook:
                return updateLentBook;
            case RemoveLentBook:
                return removeLentBook;
            case UpdateBookQty:
                return updateBookQty;
        }
        return null;
    }

    /**
     * Establishes a connection to the database
     */
    public void connect() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes predefined sql commands on the database
     *
     * @param command
     * @return the result table
     * @throws SQLException
     */
    public ResultSet executeQuery(Command command) throws SQLException {
        String sql = getCommand(command);
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }

    /**
     * Executes prepared sql statements on the database
     *
     * @param stmt
     * @return the result table
     * @throws SQLException
     */
    public ResultSet executeQueryPrepared(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }

    /**
     * Executes prepared sql statements on the database that modify the database
     *
     * @param stmt
     * @throws SQLException
     */
    public void executeQueryPreparedUpdate(PreparedStatement stmt) throws SQLException {
        stmt.executeUpdate();
    }

    /**
     * Inserts variables into the sql statement
     *
     * @param sql
     * @param userEmail
     * @param userPassword
     * @return the prepered statement
     * @throws SQLException
     */
    public PreparedStatement prepareGetCustomerByEmailAndPassword(String sql, String userEmail, String userPassword) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userEmail);
        pstmt.setString(2, userPassword);
        return pstmt;
    }

    /**
     * Inserts an integer into a sql statement
     *
     * @param sql
     * @param i
     * @return the prepered statement
     * @throws SQLException
     */
    public PreparedStatement prepareWith1Int(String sql, int i) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, i);
        return pstmt;
    }

    /**
     * Adds all variables needed to create an account to the sql statement
     *
     * @param sql
     * @param userName
     * @param userFname
     * @param userEmail
     * @param password
     * @param birthday
     * @param address
     * @param zipCode
     * @param city
     * @return the prepered statement
     * @throws SQLException
     */
    public PreparedStatement prepareCreateAccount(String sql, String userName, String userFname, String userEmail, String password, String birthday, String address, String zipCode, String city) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userName);
        pstmt.setString(2, userFname);
        pstmt.setString(3, userEmail);
        pstmt.setString(4, password);
        pstmt.setString(5, birthday);
        pstmt.setString(6, address);
        pstmt.setString(7, zipCode);
        pstmt.setString(8, city);
        return pstmt;
    }

    /**
     * Adds all variables needed to delete an account to the sql statement
     *
     * @param sql
     * @param userEmail
     * @param password
     * @return the prepered statement
     * @throws SQLException
     */
    public PreparedStatement prepareDeleteAccount(String sql, String userEmail, String password) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userEmail);
        pstmt.setString(2, password);
        return pstmt;
    }

    /**
     * Adds all variables needed to edit an account to the sql statement
     *
     * @param sql
     * @param userName
     * @param userFname
     * @param userEmail
     * @param password
     * @param birthday
     * @param address
     * @param zipCode
     * @param city
     * @param previousEmail
     * @return the prepered statement
     * @throws SQLException
     */
    public PreparedStatement prepareEditAccount(String sql, String userName, String userFname, String userEmail, String password, String birthday, String address, String zipCode, String city, String previousEmail) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userName);
        pstmt.setString(2, userFname);
        pstmt.setString(3, userEmail);
        pstmt.setString(4, password);
        pstmt.setString(5, birthday);
        pstmt.setString(6, address);
        pstmt.setString(7, zipCode);
        pstmt.setString(8, city);
        pstmt.setString(9, previousEmail);
        return pstmt;
    }

    /**
     * Adds all variables needed to borrow a book to the sql statement
     *
     * @param sql
     * @param bookID
     * @param customerID
     * @param lendingDate
     * @param returnDate
     * @return the prepered statement
     * @throws SQLException
     */
    public PreparedStatement insertLentBooksInDatabase(String sql, int bookID, int customerID, Date lendingDate, Date returnDate) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, bookID);
        pstmt.setInt(2, customerID);
        pstmt.setDate(3, (java.sql.Date) returnDate);
        pstmt.setDate(4, (java.sql.Date) lendingDate);
        return pstmt;
    }

    /**
     * Adds all variables needed to extend a book to the sql statement
     *
     * @param sql
     * @param bookingId
     * @param returnDate
     * @return the prepered statement
     * @throws SQLException
     */
    public PreparedStatement updateLentBooksInDatabase(String sql, int bookingId, Date returnDate) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setDate(1, (java.sql.Date) returnDate);
        pstmt.setInt(2, bookingId);
        return pstmt;
    }

    /**
     * Adds all variables needed to return a book to the sql statement
     *
     * @param sql
     * @param bookingId
     * @return the prepered statement
     * @throws SQLException
     */
    public PreparedStatement removeLentBookFromDatabase(String sql, int bookingId) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, bookingId);
        return pstmt;
    }

    /**
     * Adds all variables needed to change the availability of a book to the sql statement
     *
     * @param sql
     * @param bookID
     * @param bookQty
     * @return the prepered statement
     * @throws SQLException
     */
    public PreparedStatement updateBookQtyInDatabase(String sql, int bookID, int bookQty) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, bookQty);
        pstmt.setInt(2, bookID);
        return pstmt;
    }

    /**
     * An enum that provides all executable sql commands to prevent unwanted sql commands from being executed.
     */
    public enum Command {
        GetAllBooks, CreateAccount, DeleteAccount, EditAccount, GetUserName, GetUserEmail, GetUserPassword, GetCustomer, GetBooksFromUser, GetBookTitle, GetBookISBN, GetSearchGenre, GetSearchAuthor, GetSearchReleaseDate, GetAvailableBooks, GetQuantityBooks, InsertLentBook, UpdateLentBook, RemoveLentBook, UpdateBookQty,
    }
}