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
            int qty = rs.getInt("qty");     // Annika
            books.add(new Book(id, title, author, isbn, releaseDate, publisher, genre, description, qty));  //Annika
        }
        return books;
    }

    public static ArrayList<Orders> ProccesUserOrders(ResultSet rs) throws SQLException {
        ArrayList<Orders> orders = new ArrayList<Orders>();

        while (rs.next()) {
            Orders order = new Orders();
            int bookingId = rs.getInt("booking_id");    //Annika
            order.setBookingId(bookingId);  //Annika
            int bookid = rs.getInt("book_id");
            order.setBook(Manager.GetBooks().stream().filter(b -> b.getId() == bookid).findFirst().orElse(null));
            Date lendingDate = rs.getDate("lending_date");
            Date returnDate = rs.getDate("deadline");
            order.setOrderdate(lendingDate);
            order.setReturndate(returnDate);
            orders.add(order);
        }


        return orders;
    }


    public static Customer processCustomer(ResultSet rs) throws SQLException {
        Customer customer = null;
        System.out.print("vor proccess: ");
        System.out.println(rs == null);

        if (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String fname = rs.getString("fname");
            String email = rs.getString("email");
            String password = rs.getString("password");
            // Date birthday = new Date();//rs.getDate("birthday");
            Date birthday = rs.getDate("birthday");
            String address = rs.getString("address");
            String zipCode = rs.getString("zip_code");
            String city = rs.getString("city");

            customer = new Customer(id, name, fname, email, password, birthday, address, zipCode, city);
        }
        System.out.print("nach proccess: ");
        System.out.println(customer == null);
        return customer;
    }

    public String processUserEmail(ResultSet rs) throws SQLException {
        if (rs.next()) {

            return rs.getString("email");
        }
        return null;
    }

    public String processUserPassword(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getString("password");
        }
        return null;
    }

}