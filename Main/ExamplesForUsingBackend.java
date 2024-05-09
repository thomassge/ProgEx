package Main;

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

    }



}
