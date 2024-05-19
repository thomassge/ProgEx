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
        int bookQty = book.getQty() - 1;        //SQL Befehl, der automatisch dekrementiert? Überprüfen, dass nicht ins negative gezählt wird
        insertLentBookInDB(bookID, customerID, lendingDate, returnDate);
        updateBookQtyInDB(bookID, bookQty);
        }
    }

    public static void extendBook(Book book){
        int bookID = book.getId();
        int customerID = Manager.GetUser().getId();
        // TODO: schreibe Methode getLendingDate in Orders in Managerklasse -> denn die Methode get(bookID) gibt null zurück in einem Array das nur ein Buch enthält
        Date lendingDate = Manager.GetUser().getOrderForBook(bookID).getOrderdate();
        // Umwandlung von java.sql.Date zu java.util.Date
        java.util.Date utilLendingDate = new java.util.Date(lendingDate.getTime());
        //Date date = new Date();
        Date returnDate = Manager.GetUser().getOrderForBook(bookID).getReturndate();
        // Umwandlung von java.sql.Date zu java.util.Date
        java.util.Date utilReturnDate = new java.util.Date(returnDate.getTime());

        Date extendingDate = calculateReturnDate(utilReturnDate);
        //java.util.Date utilExtendingDate = new java.util.Date(extendingDate.getTime());

        if(!isBookExtendable(utilLendingDate, extendingDate)){
            //TODO Buch ist nicht verlängerbar -> Fehlermeldung in GUI
            System.out.println("Test");
        } else {
            updateLentBookInDB(bookID, customerID, extendingDate);
        }
    }

    public static void returnBook(Book book){
        int bookID = book.getId();
        int customerID = Manager.GetUser().getId();
        Date lendingDate = Manager.GetUser().getOrderForBook(bookID).getOrderdate();
        Date returnDate = Manager.GetUser().getOrderForBook(bookID).getReturndate();
        int bookQty = book.getQty() + 1;
        removeLentBookInDB(bookID, customerID);
        updateBookQtyInDB(bookID, bookQty);
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

    public static void updateLentBookInDB(int bookID, int customerID, java.util.Date extendingDate) {
        try {
            java.sql.Date sqlExtendingDate = new java.sql.Date(extendingDate.getTime());
            PreparedStatement stmt = dbConn.updateLentBooksInDatabase(DatabaseConnection.GetCommand(DatabaseConnection.Command.UpdateLentBook), bookID, customerID, sqlExtendingDate);
            dbConn.executeQueryPreparedUpdate(stmt);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void removeLentBookInDB(int bookID, int customerID) {
        try {
            PreparedStatement stmt = dbConn.removeLentBookFromDatabase(DatabaseConnection.GetCommand(DatabaseConnection.Command.RemoveLentBook), bookID, customerID);
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
        // 2592000 = 30 Tage in Millisekunden
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lendingDate);
        calendar.add(Calendar.DATE, 30); // Add 30 days to current date
        Date extendedDate = calendar.getTime();
        return returnDate.getTime() <= extendedDate.getTime();
    }
}
