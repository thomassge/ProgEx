package Backend;

import DataStructure.Book;
import DataStructure.Order;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

/**
 * The lendBook class performs the functions of lending, extending and returning books.
 */
public class LendBook {
    static DatabaseConnection dbConn = new DatabaseConnection();

    /**
     * Lends a book to a user.
     * This method reduces the available quantity of the book by 1 and saves the lending data
     * in the database and in the program. If the book is not available, an exception is triggered.
     *
     * @param book The book object to be borrowed.
     * @throws BookUnavailableException if the book is not available (i.e. the quantity is less than 1).
     */
    public static void lendBook(Book book) throws BookUnavailableException {
        if (book.getQty() < 1) {
            throw new BookUnavailableException("The book is currently not available.");
        } else {
            int bookID = book.getId();
            int customerID = Manager.getUser().getId();
            Date lendingDate = new Date();
            Date returnDate = calculateReturnDate(lendingDate);
            int bookQty = book.getQty() - 1;
            insertLentBookInDB(bookID, customerID, lendingDate, returnDate);
            updateBookQtyInDB(bookID, bookQty);
            try {
                Manager.loadBooks();
                Manager.loadPersonalBooks();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Extends the lending period of a book.
     * This method checks whether the lending period of a book can be extended,
     * and updates the return date in the database. If the extension is not possible,
     * an exception is triggered.
     *
     * @param order The order, which contains the details of the book lending.
     * @throws BookIsNotExtendableException if the lending period cannot be extended.
     */
    public static void extendBook(Order order) throws BookIsNotExtendableException {

        int bookingId = order.getBookingId();
        Date lendingDate = order.getOrderDate();
        // Umwandlung von java.sql.Date zu java.util.Date
        java.util.Date utilLendingDate = new java.util.Date(lendingDate.getTime());
        //Date date = new Date();
        Date returnDate = order.getReturnDate();
        // Umwandlung von java.sql.Date zu java.util.Date
        java.util.Date utilReturnDate = new java.util.Date(returnDate.getTime());

        Date extendingDate = calculateReturnDate(utilReturnDate);
        //java.util.Date utilExtendingDate = new java.util.Date(extendingDate.getTime());

        if (!isBookExtendable(utilLendingDate, extendingDate)) {
            throw new BookIsNotExtendableException("The maximum extension time has been reached.");
        } else {
            updateLentBookInDB(bookingId, extendingDate);
            try {
                Manager.loadPersonalBooks();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns a lent book.
     * This method increases the available quantity of the returned book by 1 and
     * updates the corresponding entries in the database and in the program.
     *
     * @param order The order, which contains the details of the book lending.
     */
    public static void returnBook(Order order) {
        int bookId = order.getBook().getId();
        int bookingId = order.getBookingId();

        int bookQty = Manager.getBookById(bookId).getQty() + 1;
        removeLentBookInDB(bookingId);
        updateBookQtyInDB(bookId, bookQty);
        try {
            Manager.loadBooks();
            Manager.loadPersonalBooks();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new entry about the lent book to the database.
     *
     * @param bookID      The ID of the lent book.
     * @param customerID  The ID of the user who is lending the book.
     * @param lendingDate The date on which the book is lent.
     * @param returnDate  The date on which the book is to be returned.
     */
    public static void insertLentBookInDB(int bookID, int customerID, java.util.Date lendingDate, java.util.Date returnDate) {
        try {
            java.sql.Date sqlLendingDate = new java.sql.Date(lendingDate.getTime());
            java.sql.Date sqlReturnDate = new java.sql.Date(returnDate.getTime());
            PreparedStatement stmt = dbConn.insertLentBooksInDatabase(DatabaseConnection.getCommand(DatabaseConnection.Command.InsertLentBook), bookID, customerID, sqlLendingDate, sqlReturnDate);
            dbConn.executeQueryPreparedUpdate(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a book lending entry in the database.
     *
     * @param bookingId     The ID of the lending entry to be updated.
     * @param extendingDate The extended date on which the book is to be returned.
     */
    public static void updateLentBookInDB(int bookingId, java.util.Date extendingDate) {
        try {
            java.sql.Date sqlExtendingDate = new java.sql.Date(extendingDate.getTime());
            PreparedStatement stmt = dbConn.updateLentBooksInDatabase(DatabaseConnection.getCommand(
                                     DatabaseConnection.Command.UpdateLentBook), bookingId, sqlExtendingDate);
            dbConn.executeQueryPreparedUpdate(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a book lending entry in the database.
     *
     * @param bookingId The ID of the lending entry to be removed.
     */
    public static void removeLentBookInDB(int bookingId) {
        try {
            PreparedStatement stmt = dbConn.removeLentBookFromDatabase(DatabaseConnection.getCommand(
                                     DatabaseConnection.Command.RemoveLentBook), bookingId);
            dbConn.executeQueryPreparedUpdate(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the book quantity in the database.
     *
     * @param bookID  The ID of the book whose quantity is to be updated.
     * @param bookQty The current number of available books.
     */
    public static void updateBookQtyInDB(int bookID, int bookQty) {
        try {
            PreparedStatement stmt = dbConn.updateBookQtyInDatabase(DatabaseConnection.getCommand(DatabaseConnection.Command.UpdateBookQty), bookID, bookQty);
            dbConn.executeQueryPreparedUpdate(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the return date based on the lending date.
     * The return date is 7 days after the lending date.
     *
     * @param lendingDate The date on which the book was lent.
     * @return The calculated return date, which is 7 days after the lending date.
     */
    public static Date calculateReturnDate(Date lendingDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lendingDate);
        calendar.add(Calendar.DATE, 7); // Add 7 days to current date
        return calendar.getTime();
    }

    /**
     * Checks whether the lending of a book can be extended.
     * This method checks whether the return date is within 30 days of the loan date,
     * to determine whether a renewal is possible.
     *
     * @param lendingDate The original lending date.
     * @param returnDate  The current return date.
     * @return true if the book is renewable (i.e. the return date is within 30 days of the loan date); false if not.
     */
    private static boolean isBookExtendable(Date lendingDate, Date returnDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lendingDate);
        calendar.add(Calendar.DATE, 30); // Add 30 days to current date
        Date extendedDate = calendar.getTime();
        return returnDate.getTime() <= extendedDate.getTime();
    }
}
