package Backend;
import DataStructure.Book;
import DataStructure.Orders;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;


public class DatabaseConnection {

    private String url = "jdbc:mysql://localhost:8080/Database?useSSL=false";
    private String user = "root";
    private String password = "12345";
    private Connection conn = null;

    static String getCustomerByEmailAndPassword = "SELECT * FROM customer WHERE email = ? AND password = ?";
    static String getAllBooks = "SELECT book.*, qty.qty FROM book, qty WHERE book.id = qty.book_id";       //Annika
    static String createAccount = "INSERT INTO customer (name, fname, email, password, birthday, address, zip_code, city) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    static String deleteAccount = "DELETE FROM customer WHERE email = ? AND password = ?";
    static String editAccount = "UPDATE customer SET name = ?, fname = ?, email = ?, password = ?, birthday = ?, address = ?, zip_code = ?, city = ? WHERE email = ?";
    static  String getUserName = "SELECT name FROM customer WHERE id = ?";
    static  String getUserEmail = "SELECT email FROM customer WHERE id = ?";
    static String getUserPassword = "SELECT password FROM customer WHERE id = ?";
    static String getCustomer = "SELECT * FROM customer WHERE id = ?";
    static String getBooksFromUser = "SELECT * FROM lent_books WHERE customer_id = ?";
    static String getBookTitle = "SELECT * FROM Book WHERE title LIKE '% %' ";
    static String getBookISBN = "SELECT * FROM Book WHERE ISBN = ?";
    static String getBookGenre = "SELECT * FROM Genre WHERE genre = ?";
    static String getBookAuthor = "SELECT * FROM Author WHERE author = ?";
    static String getReleaseDate = "SELECT * FROM Book WHERE release_date > ?";
    static String getAvailableBooks = "SELECT * FROM Book WHERE id NOT IN (SELECT book_id FROM lent_books)";
    static String getBookPerCustomer = "SELECT customer_id, COUNT(*) AS num_lent_books FROM lent_books GROUP BY customer_id";
    static String getQuantityBooks = "SELECT * FROM qty";
    static  String getMostBorrowedBook = "SELECT Book.id, Book.title, COUNT(*) AS num_borrowed FROM Book JOIN lent_books ON Book.id = lent_books.book_id GROUP BY Book.id, Book.title ORDER BY num_borrowed DESC";
    static String getAllLentBooks = "SELECT * FROM lent_books";
    static String insertLentBook = "INSERT INTO lent_books (book_id, customer_id, deadline, lending_date) VALUES (?, ?, ?, ?)";      //Annika
    static String updateLentBook ="UPDATE lent_books SET deadline = ? WHERE booking_id = ?"; //Annika
    static String removeLentBook = "DELETE FROM lent_books WHERE booking_id = ?";      //Annika
    static String updateBookQty = "UPDATE qty SET qty = ? WHERE book_id = ?";   //Annika
    // static String getCustomersIdenticalBook = "SELECT Customer.* FROM Customer JOIN lent_books ON Customer.id = lent_books.customer_id WHERE lent_books.book_id = ?";

    public PreparedStatement PrepareGetCustomerByEmailAndPassword(String sql, String userEmail, String userPassword) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userEmail);
        pstmt.setString(2, userPassword);
        return pstmt;
    }

    public enum Command{
        GetAllBooks,
        CreateAccount,
        DeleteAccount,
        EditAccount,
        GetUserName,
        GetUserEmail,
        GetUserPassword,
        GetCustomer,
        GetBooksFromUser,
        GetBookTitle,
        GetBookISBN,
        GetSearchGenre,
        GetSearchAuthor,
        GetSearchReleaseDate,
        GetAvailableBooks,
        GetQuantityBooks,
        InsertLentBook,     //Annika
        UpdateLentBook,
        RemoveLentBook,     //Annika
        UpdateBookQty,   //Annika
    };
    public DatabaseConnection() {

        Connect();

    }
    public void Connect(){
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println("Connection failed! ");
            e.printStackTrace();
        }
    }

    public void Disconnect(){


        if (!isConnectionOpen()) {
            try {
                conn.close();
                System.out.println("Cennection closed");
            } catch (SQLException e) {
                System.out.println("Error closing connection");
                e.printStackTrace();
            }
        }
    }

    public boolean isConnectionOpen() {
        try {
            if (conn != null && !conn.isClosed()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error checking connection");
            e.printStackTrace();
        }
        return false;
    }

    public static String GetCommand(Command command){
        switch (command){

            case GetAllBooks: return getAllBooks;
            case CreateAccount: return createAccount;
            case DeleteAccount: return deleteAccount;
            case EditAccount: return editAccount;
            case GetUserName: return getUserName;
            case GetUserEmail: return getUserEmail;
            case GetUserPassword: return getUserPassword;
            case GetCustomer: return getCustomer;
            case GetBooksFromUser: return getBooksFromUser;
            case GetBookTitle: return getBookTitle;
            case GetBookISBN: return getBookISBN;
            case GetAvailableBooks: return getAvailableBooks;
            case GetQuantityBooks: return getQuantityBooks;
            case InsertLentBook: return insertLentBook; //Annika
            case UpdateLentBook: return updateLentBook; //Annika
            case RemoveLentBook: return removeLentBook; //Annika
            case UpdateBookQty: return updateBookQty;   //Annika
        }
        return null;
    }

    public ResultSet executeQuery(Command command) throws SQLException {
        String sql = GetCommand(command);
        Statement stmt = conn.createStatement();
     //   printAllColumns(stmt.executeQuery(sql));
        return stmt.executeQuery(sql);
    }

    public ResultSet executeQueryPrepared(PreparedStatement stmt) throws SQLException {
       // printAllColumns(stmt.executeQuery());
        return stmt.executeQuery();
    }

    public void executeQueryPreparedUpdate(PreparedStatement stmt) throws SQLException {
        stmt.executeUpdate();
    }


    public PreparedStatement PrepareWith1Int(String sql, int i) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, i);
        return pstmt;
    }

    public PreparedStatement PrepareCreateAccount(String sql, String userName, String userFname, String userEmail, String password, String birthday, String address, String zipCode, String city) throws SQLException {
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

    public PreparedStatement PrepareDeleteAccount(String sql, String userEmail, String password) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userEmail);
        pstmt.setString(2, password);
        return pstmt;
    }

    public PreparedStatement PrepareEditAccount(String sql, String userName, String userFname, String userEmail, String password, String birthday, String address, String zipCode, String city, String previousEmail) throws SQLException {
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

    //Annika
    public PreparedStatement insertLentBooksInDatabase(String sql, int bookID, int customerID, Date lendingDate, Date returnDate) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, bookID);
        pstmt.setInt(2, customerID);
        pstmt.setDate(3, (java.sql.Date) returnDate);
        pstmt.setDate(4, (java.sql.Date) lendingDate);
        return pstmt;
    }

    //Annika
    public PreparedStatement updateLentBooksInDatabase(String sql, int bookingId, Date returnDate) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setDate(1, (java.sql.Date) returnDate);
        pstmt.setInt(2, bookingId);
        return pstmt;
    }

    //Annika
    public PreparedStatement removeLentBookFromDatabase(String sql, int bookingId) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, bookingId);
        //pstmt.setInt(2, customerID);
        return pstmt;
    }

    //Annika
    public PreparedStatement updateBookQtyInDatabase(String sql, int bookID, int bookQty) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, bookQty);
        pstmt.setInt(2, bookID);
        return pstmt;
    }

/* Veraltet
    public ResultSet executeQuery(Command command) {

        if(!isConnectionOpen()){
            return null;
        }

        String sql = GetCommand(command);
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            printAllColumns(rs);
return rs;
        } catch (SQLException e) {
            System.out.println("SQL-Error: ");
            e.printStackTrace();
        }

        return null;
    }
*/

    public void printAllColumns(ResultSet rs) throws SQLException {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String value = rs.getString(i) + " | ";
                    System.out.println(columnName + ": " + value);
                }
                System.out.println("-------------------");
            }
    }
}
