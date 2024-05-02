package Backend;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import DataStructure.*;

public class DataProcessor {

DatabaseConnection dbConn;


public DataProcessor() {
    dbConn = new DatabaseConnection();
}

// Richtige weise um zu verarbeiten
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
            books.add(new Book(id, title, author, isbn, releaseDate, publisher, genre, description));
        }
        return books;
    }

    public ArrayList<Orders> ProccesUserOrders(ResultSet rs) throws SQLException{
    ArrayList<Orders> orders = new ArrayList<Orders>();

    while (rs.next()){
        Orders order = new Orders();

    }

return null;
    }


        public Customer processCustomer(ResultSet rs) throws SQLException{
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

        public String processUserEmail(ResultSet rs) throws SQLException {
                if(rs.next()){

                  return rs.getString("email");
                }
            return null;
        }

    public String processUserPassword(ResultSet rs) throws SQLException{
            if(rs.next()){
                return rs.getString("password");
            }
        return null;
    }

}