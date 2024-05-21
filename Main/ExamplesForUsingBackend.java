package Main;

import Backend.LendBook;
import Backend.LoginBackend;
import Backend.Manager;
import DataStructure.*;

import java.util.ArrayList;

public class ExamplesForUsingBackend {

    public static void main(String[] args) {

        //Läd automatisch alle bücher von der datenbank ins system, ist schon in der main und mach alles automatisch
        Manager manager = new Manager();

        // Alle Methoden sind Aktuell Statisch und brauchen kein stezielles objekt um aufgerufen zu werden.

        //Holt alle Bücher
        ArrayList<Book> books = Manager.GetBooks();

        //Holt bücher vom user (vorher muss die methode 'checkLogin' aufgerufen worden sein) (in der 'order' ist immer ein 'Book' objekt, ein ausleihdatum und ein rückgabedatum)
        ArrayList<Orders> personalBooks = Manager.GetPersonalBooks();

        //Holt den User (vorher muss die methode 'checkLogin' aufgerufen worden sein)
        Customer user = Manager.GetUser();

        //Checkt ob benutzer sich anmelden darf, setzt ein lokales 'Customer' Objekt in 'Manager klasse'
        boolean darfEingelogtWerden = LoginBackend.checkLogin("test@email.com","passwort");

        //Erstellt einen account & setzt gleich das lokale 'Customer' Objekt
        LoginBackend.createAccount("userName", "lastName","test@email.de", "password", "01.01.2000","adress", "12345Zip", "city");

        //bearbeite benutzerinformationen
        LoginBackend.editAccount(Manager.GetUser(),"userName", "lastName","test@email.de", "password", "01.01.2000","adress", "12345Zip", "city");

        //funktionen zum ausleihen und Verlängern und zurückgeben sind in arbeit


        //der funktion wird das ausgewählte buch übergeben die funkion fügt dem benutzer das buch hinzu und zieht eins von der menge ab
        //wenn das Buch nicht verfügbar ist wird eine Exception geworfen, die aufgefangen werden kann.
        // vielleicht kann dem Benutzer ein Fenster mit einem entsprechenden Hinweis in der GUI angezeigt werden
        // die Tabelle in der ui muss sich die lister der Bücher erneut vom manager mit getBooks und getPersonalBooks holen und neu anzeigen

       // LendBook.lendBook(book);



        // der Funtion wird die order übergeben die verlängert werden soll, die funktion verlängert das buch um 7 tage sofern das buch nicht mehr als 30 tage ausgeliehen ist
        //wenn das Buch nicht verlängerbar ist, wird eine Exception geworfen, die aufgefangen werden kann
        // vielleicht kann dem Benutzer ein Fenster mit einem entsprechenden Hinweis in der GUI angezeigt werden
        // die Tabelle in der ui muss sich die lister der ausgeliehenen bücher mit getPersonalBooks holen und neu anzeigen

        //LendBook.extendBook(order);

        //der funktion wird die order übergeben die zurückgegeben werden soll, die funktion erhöht die anzahl des buches wieder um 1  und löscht die order aus der datenbank
        // die Tabelle in der ui muss sich die lister der Bücher erneut vom manager mit getBooks und getPersonalBooks holen und neu anzeigen

       // LendBook.returnBook(order);



        // Besispiel programmablauf

       // ArrayList<Book> books = Manager.GetBooks();
        Book book = books.get(7);
        LoginBackend.checkLogin("jane.smith@example.com", "password456");

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        System.out.println("Vor Ausleihe:");
        LendBook.lendBook(book);
        Manager.GetUser().PrintPersonalBooks();


        LendBook.extendBook(Manager.GetUser().getOrderForBook(book.getId()));
        System.out.println("Nach Verlängerung:");
        Manager.GetUser().PrintPersonalBooks();



        LendBook.returnBook(Manager.GetUser().getOrderForBook(book.getId()));
        System.out.println("Nach Rückgabe:");
        Manager.GetUser().PrintPersonalBooks();


    }



}
