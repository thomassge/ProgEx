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

    public ArrayList<Book> processBooks(ResultSet rs) {
        ArrayList<Book> books = new ArrayList<>();
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }


        public Customer processCustomer(ResultSet rs) {
            Customer customer = null;
            try {
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return customer;
        }

        public String processUserEmail(ResultSet rs){
            try{
                if(rs.next()){

                  return rs.getString("email");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

    public String processUserPassword(ResultSet rs){
        try{
            if(rs.next()){

                return rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}
