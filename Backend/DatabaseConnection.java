package Backend;
import DataStructure.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;


public class DatabaseConnection {

    private String url = "jdbc:mysql://localhost:8080/Database?useSSL=false";
    private String user = "root";
    private String password = "12345";
    private Connection conn = null;

    static String getAllBooks = "select * from book";
    static String createAccount = "INSERT INTO customer (name, fname, email, password, birthday, address, zip_code, city) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
    //static String getQuantityBooks = "SELECT book_id, SUM(quantity) AS total_quantity FROM qty WHERE book_id = ? GROUP BY book_id";
    static  String getMostBorrowedBook = "SELECT Book.id, Book.title, COUNT(*) AS num_borrowed FROM Book JOIN lent_books ON Book.id = lent_books.book_id GROUP BY Book.id, Book.title ORDER BY num_borrowed DESC";
    static String getAllLentBooks = "SELECT * FROM lent_books";
   // static String getCustomersIdenticalBook = "SELECT Customer.* FROM Customer JOIN lent_books ON Customer.id = lent_books.customer_id WHERE lent_books.book_id = ?";

    public static void main(String[] args) {

    DatabaseConnection db = new DatabaseConnection();
    System.out.println("Connecting to database..." + db.isConnectionOpen());
    try {
        db.executeQuery(Command.GetAllBooks);
    }catch (SQLException e) {
        e.printStackTrace();
    }

    }


    public enum Command{
        GetAllBooks,
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
            case GetUserName: return getUserName;
            case GetUserEmail: return getUserEmail;
            case GetUserPassword: return getUserPassword;
            case GetCustomer: return getCustomer;
            case GetBooksFromUser: return getBooksFromUser;
            case GetBookTitle: return getBookTitle;
            case GetBookISBN: return getBookISBN;
            case GetAvailableBooks: return getAvailableBooks;
        }
        return null;
    }

    public ResultSet executeQuery(Command command) throws SQLException {
        String sql = GetCommand(command);
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
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

    public void printAllColumns(ResultSet rs) {
        try {
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
        } catch (SQLException e) {

        }
    }
}
