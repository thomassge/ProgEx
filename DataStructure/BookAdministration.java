package DataStructure;

import java.util.ArrayList;

/**
 * The BookAdministration class manages a collection of books in the program.
 */
public class BookAdministration {

    ArrayList<Book> books = new ArrayList<Book>();


    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public Book getBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }
}
