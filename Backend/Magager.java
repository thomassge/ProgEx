package Backend;

import DataStructure.Book;
import DataStructure.BookAdministration;
import DataStructure.Customer;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

public class Magager {

    DatabaseConnection dbConn;
    DataProcessor processor;

    Customer user;
    BookAdministration bookAdministration;
    LoginBackend loginBackend;

    public static void main(String[] args) {
        Magager magager = new Magager();

        try{
            magager.LoadBooks();
            magager.bookAdministration.DisplayBooksInConsole();
        }catch (SQLException e){
        }
    }


    public Magager() {

        dbConn = new DatabaseConnection();
        processor = new DataProcessor();
        loginBackend = new LoginBackend();
        bookAdministration = new BookAdministration();
    }


    public void LoadBooks() throws SQLException {
       bookAdministration.SetBooks(processor.processBooks(dbConn.executeQuery(DatabaseConnection.Command.GetAllBooks)));
    }

public ArrayList<Book> GetBooks(){
        return bookAdministration.getBooks();
}

}
