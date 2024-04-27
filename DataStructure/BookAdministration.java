package DataStructure;

import java.util.ArrayList;
import java.util.List;

public class BookAdministration {

    ArrayList<Book> books = new ArrayList<Book>();


    public ArrayList<Book> getBooks() {
        return books;
    }

    public void SetBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public void AddBook(Book book){
        books.add(book);
    }

    public void RemoveBook(Book book){
        books.remove(book);
    }

    public void ClearBooks(){
        books.clear();
    }

    public void DisplayBooksInConsole(){
        System.out.println("Books:");
        System.out.println(books.size());
        for(Book book : books){
            System.out.println(book.toString());
        }
    }



}
