package Main;

import Backend.*;
import DataStructure.*;

import java.util.ArrayList;

public class TestMain {

    public static void main(String[] args) {

        //Lädt automatisch alle Bücher von der Datenbank ins System, ist schon in der main und macht alles automatisch
        Manager manager = new Manager();

        //Holt alle Bücher
        ArrayList<Book> books = Manager.GetBooks();
        Book book = books.get(7);
        LoginBackend.checkLogin("jane.smith@example.com", "password456");

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        System.out.println("Vor Ausleihe:");
        LendBook.lendBook(book);
        Manager.GetUser().PrintPersonalBooks();
        System.out.println();
        LendBook.lendBook(book);
        Manager.GetUser().PrintPersonalBooks();
        System.out.println();
        LendBook.lendBook(book);
        Manager.GetUser().PrintPersonalBooks();

        /*
            LendBook.extendBook(Manager.GetUser().getOrderForBook(book.getId()));
            System.out.println("Nach Verlängerung:");
            Manager.GetUser().PrintPersonalBooks();



        LendBook.returnBook(Manager.GetUser().getOrderForBook(book.getId()));
        System.out.println("Nach Rückgabe:");
        Manager.GetUser().PrintPersonalBooks();
*/

/*
        try {
            LendBook.lendBook(book);
        } catch (BookUnavailableException e) {
            // Fehlermeldung in GUI anzeigen
            System.out.println(e.getMessage());
        }
 */
        /*
        LendBook.returnBook(Manager.GetPersonalBooks().getFirst());      //returnBook funktioniert bis auf wenn man zwei mal das gleiche Buch ausleiht und das zweite zurückgibt, dann wird das erste Buch auch zurückgegeben

        try {
            LendBook.extendBook(Manager.GetPersonalBooks().getFirst());
        } catch (BookIsNotExtendableException e) {
            // Fehlermeldung in GUI anzeigen
            System.out.println(e.getMessage());
        }
        */
    }
}
