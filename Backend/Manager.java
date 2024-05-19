package Backend;

import DataStructure.Book;
import DataStructure.BookAdministration;
import DataStructure.Customer;
import DataStructure.Orders;

import java.util.ArrayList;
import java.sql.SQLException;

public class Manager {

   static DatabaseConnection dbConn;
   static DataProcessor processor;

   static Customer user;
  static  BookAdministration bookAdministration;
    LoginBackend loginBackend;

    public static void main(String[] args) {
        Manager manager = new Manager();

            bookAdministration.DisplayBooksInConsole();
            LoginBackend.checkLogin("jane.smith@example.com", "password456");
            user.PrintPersonalBooks();

    }


    public Manager() {

        dbConn = new DatabaseConnection();
        processor = new DataProcessor();
        loginBackend = new LoginBackend();
        bookAdministration = new BookAdministration();

        try {
            LoadBooks();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public static void LoadBooks() throws SQLException {
       bookAdministration.SetBooks(processor.processBooks(dbConn.executeQuery(DatabaseConnection.Command.GetAllBooks)));
    }

    public static void LoadPersonalBooks() throws SQLException{
        int id = user.getId();
        ArrayList<Orders> orders = processor.ProccesUserOrders( dbConn.executeQueryPrepared(dbConn.PrepareWith1Int(DatabaseConnection.GetCommand(DatabaseConnection.Command.GetBooksFromUser),id)));
        user.setOrders(orders);
    }



    public static boolean SetUser(Customer customer){
        user = customer;
        try {
            LoadPersonalBooks();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return customer !=null;
    }

    public static Customer GetUser(){
        return user;
    }

    public static void WriteUser(){
       System.out.println("teste");
        System.out.println(user.geteMail());
        System.out.println(user.getId());

    }
/*
    public static void LoadBookIds() throws SQLException{  //Annika
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
*/
    public static ArrayList<Orders> GetPersonalBooks(){
       return user.getOrders();
    }

public static ArrayList<Book> GetBooks(){
        return  bookAdministration.getBooks();
}
/*
public static Book GetSingleBookByID(int id){       //Annika
        ArrayList<Book> books = Manager.GetUser().getOrders();
        for(Book book : books){
                if(book.getId() == id){
                  return book;
                }
}

   public static void UpdateBookQty(int id, int qty) {
        bookAdministration.UpdateBookQty(id, qty);
    }

    public ArrayList<Book> GetBookIds(){    //Annika
      //  return bookAdministration.getBookIds();
    }
*/
}



