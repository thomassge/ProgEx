//Annika
package Backend;

import DataStructure.Book;
import DataStructure.Customer;
import DataStructure.Orders;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Calendar;
import java.util.Date;

public class LendBook {
    static DatabaseConnection dbConn = new DatabaseConnection();

    public static void lendBook(Book book) throws BookUnavailableException {
        if(book.getQty()<1){
            throw new BookUnavailableException("Das Buch ist nicht verfügbar.");
        } else {
        int bookID = book.getId();
        int customerID = Manager.GetUser().getId();
        Date lendingDate = new Date();
        Date returnDate = calculateReturnDate(lendingDate);
        int bookQty = book.getQty() - 1;
        insertLentBookInDB(bookID, customerID, lendingDate, returnDate);
        updateBookQtyInDB(bookID, bookQty);
        try {
            Manager.LoadBooks();
            Manager.LoadPersonalBooks();
        } catch (SQLException e){
            e.printStackTrace();
        }

        }
    }

    public static void extendBook(Orders order) throws BookIsNotExtendableException{

        int bookingId = order.getBookingId();
        Date lendingDate = order.getOrderdate();
        // Umwandlung von java.sql.Date zu java.util.Date
        java.util.Date utilLendingDate = new java.util.Date(lendingDate.getTime());
        //Date date = new Date();
        Date returnDate = order.getReturndate();
        // Umwandlung von java.sql.Date zu java.util.Date
        java.util.Date utilReturnDate = new java.util.Date(returnDate.getTime());

        Date extendingDate = calculateReturnDate(utilReturnDate);
        //java.util.Date utilExtendingDate = new java.util.Date(extendingDate.getTime());

        if(!isBookExtendable(utilLendingDate, extendingDate)){
            throw new BookIsNotExtendableException("Das Buch kann nicht verlängert werden.");
        } else {
            updateLentBookInDB(bookingId, extendingDate);
            try {
                Manager.LoadPersonalBooks();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }



    public static void returnBook(Orders order){
        int bookId = order.getBook().getId();
        int bookingId = order.getBookingId();

        int bookQty = Manager.GetBookById(bookId).getQty() + 1;
        removeLentBookInDB(bookingId);  //Annika
        updateBookQtyInDB(bookId, bookQty);
        try {
            Manager.LoadBooks();
            Manager.LoadPersonalBooks();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertLentBookInDB(int bookID, int customerID, java.util.Date lendingDate, java.util.Date returnDate) {
        try {
            java.sql.Date sqlLendingDate = new java.sql.Date(lendingDate.getTime());
            java.sql.Date sqlReturnDate = new java.sql.Date(returnDate.getTime());
            PreparedStatement stmt = dbConn.insertLentBooksInDatabase(DatabaseConnection.GetCommand(DatabaseConnection.Command.InsertLentBook), bookID, customerID, sqlLendingDate, sqlReturnDate);
            dbConn.executeQueryPreparedUpdate(stmt);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateLentBookInDB(int bookingId, java.util.Date extendingDate) {
        try {
            java.sql.Date sqlExtendingDate = new java.sql.Date(extendingDate.getTime());
            PreparedStatement stmt = dbConn.updateLentBooksInDatabase(DatabaseConnection.GetCommand(DatabaseConnection.Command.UpdateLentBook), bookingId, sqlExtendingDate);
            dbConn.executeQueryPreparedUpdate(stmt);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void removeLentBookInDB(int bookingId) {
        try {
            PreparedStatement stmt = dbConn.removeLentBookFromDatabase(DatabaseConnection.GetCommand(DatabaseConnection.Command.RemoveLentBook), bookingId);
            dbConn.executeQueryPreparedUpdate(stmt);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateBookQtyInDB(int bookID, int bookQty) {
        try {
            PreparedStatement stmt = dbConn.updateBookQtyInDatabase(DatabaseConnection.GetCommand(DatabaseConnection.Command.UpdateBookQty), bookID, bookQty);
            dbConn.executeQueryPreparedUpdate(stmt);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Date calculateReturnDate(Date lendingDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lendingDate);
        calendar.add(Calendar.DATE, 7); // Add 7 days to current date
        return calendar.getTime();
    }

    private static boolean isBookExtendable(Date lendingDate, Date returnDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lendingDate);
        calendar.add(Calendar.DATE, 30); // Add 30 days to current date
        Date extendedDate = calendar.getTime();
        return returnDate.getTime() <= extendedDate.getTime();
    }
}
