package Backend;
import java.sql.*;


public class DatabaseConnection {

    private String url = "jdbc:mysql://localhost:8080/Database?useSSL=false";
    private String user = "root";
    private String password = "12345";
    private Connection conn = null;

    String getAllBooks = "select * from book";
    String createAccount = "INSERT INTO customer (name, fname, email, password, birthday, address, zip_code, city) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String getUserName = "SELECT name FROM customer WHERE id = ?";
    String getUserEmail = "SELECT email FROM customer WHERE id = ?";
    String getUserPassword = "SELECT password FROM customer WHERE id = ?";
    String getCustomer = "SELECT * FROM customer WHERE id = ?";
    String getBooksFromUser = "SELECT * FROM lent_books WHERE customer_id = ?";
    String getBookTitle = "SELECT * FROM Book WHERE title LIKE '% %' ";
    String getBookISBN = "SELECT * FROM Book WHERE ISBN = ?";
    String getBookGenre = "SELECT * FROM Genre WHERE genre = ?";
    String getBookAuthor = "SELECT * FROM Author WHERE author = ?";
    String getReleaseDate = "SELECT * FROM Book WHERE release_date > ?";
    String getAvailableBooks= "SELECT * FROM Book WHERE id NOT IN (SELECT book_id FROM lent_books)";
    String getBookPerCustomer = "SELECT customer_id, COUNT(*) AS num_lent_books FROM lent_books GROUP BY customer_id";
    String getQuantityBooks= "SELECT book_id, COUNT(*) AS num_available FROM Book LEFT JOIN lent_books ON Book.id = lent_books.book_id GROUP BY book_id";

    public static void main(String[] args) {

    DatabaseConnection db = new DatabaseConnection();
    System.out.println("Connecting to database..." + db.isConnectionOpen());

    db.executeQuery(Command.GetQuantityBooks);
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

    private String GetCommand(Command command){
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
            case GetQuantityBooks: return getQuantityBooks;
        }
        return null;
    }

    public ResultSet executeQuery(Command command) {

        if(!isConnectionOpen()){
            return null;
        }

        String sql = GetCommand(command);

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.print(rs.getString(1) + " | ");
                System.out.print(rs.getString(2)+ " | ");

                /*System.out.print(rs.getString(3)+ " | ");
                System.out.print(rs.getString(4)+ " | ");
                System.out.print(rs.getString(5)+ " | ");
                System.out.print(rs.getString(6)+ " | ");
                System.out.print(rs.getString(7)+ " | ");
                System.out.print(rs.getString(8)+ " | ");
                System.out.print("\n");
                */
                 
            }

            return rs;
        } catch (SQLException e) {
            System.out.println("SQL-Error: ");
            e.printStackTrace();
        }
        return null;
    }
}
