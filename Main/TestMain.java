package Main;

import Backend.BookUnavailableException;
import Backend.LendBook;
import Backend.LoginBackend;
import Backend.Manager;
import DataStructure.*;

import java.util.ArrayList;

public class TestMain {

    public static void main(String[] args) {

        //Lädt automatisch alle Bücher von der Datenbank ins System, ist schon in der main und macht alles automatisch
        Manager manager = new Manager();

        //Holt alle Bücher
        ArrayList<Book> books = Manager.GetBooks();
        Book book = books.get(0);
        LoginBackend.checkLogin("jane.smith@example.com", "password456");
/*
        try {
            LendBook.lendBook(book);
        } catch (BookUnavailableException e) {
            // Zeigen Sie die Fehlermeldung in Ihrer GUI an
            System.out.println(e.getMessage());
        }
*/


        //LendBook.extendBook(book);    //extendBook funktioniert nicht, da Manager.GetUser().getOrders().get(bookID) null zurückgibt??
        LendBook.returnBook(book);      //returnBook funktioniert bis auf Aktualisierung der Quantity
        }
}
