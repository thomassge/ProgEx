package DataStructure;

import java.net.IDN;
import java.util.Date;

public class Orders {
Book book;
Date orderdate;
Date returndate;
int bookingId;  //Annika

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public Date getReturndate() {
        return returndate;
    }

    public void setReturndate(Date returndate) {
        this.returndate = returndate;
    }

    public int getBookingId() {     //Annika
        return bookingId;
    }

    public void setBookingId(int bookingId) {   //Annika
        this.bookingId = bookingId;
    }

    public void WriteOrderInConsole() {
    System.out.println("book: " + book.id + " " + book.title + " lending: " + orderdate + " return: " + returndate);
    }

}
