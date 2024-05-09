package Backend;

import DataStructure.Book;
import DataStructure.BookAdministration;
import DataStructure.Customer;
import DataStructure.Orders;

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

    public void LoadPersonalBooks(){
        int id = user.getId();

        List<Orders> orders;



    }


    public void LoadBookIds() throws SQLException{  //Annika
        ArrayList<Book> books = GetBooks();
        ArrayList<Integer> bookIds = new ArrayList<Integer>();

        for(int id : bookIds){

            for(Book book : books){
                if(book.getId() == id){
                  //  book.setQty();
                }

            }
        }
    }

public ArrayList<Book> GetBooks(){
        return  bookAdministration.getBooks();
}
/*
    public ArrayList<Book> GetBookIds(){    //Annika
      //  return bookAdministration.getBookIds();
    }
*/
}



