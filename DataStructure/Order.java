package DataStructure;

import java.util.Date;

/**
 * The Order class represents an order in the program.
 */
public class Order {
    Book book;
    Date orderDate;
    Date returnDate;
    int bookingId;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getBookingId() {     //Annika
        return bookingId;
    }

    public void setBookingId(int bookingId) {   //Annika
        this.bookingId = bookingId;
    }


}
