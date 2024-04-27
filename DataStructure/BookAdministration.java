package DataStructure;

import java.util.ArrayList;
import java.util.List;

public class BookAdministration {

    List<Book> books = new ArrayList<Book>();


    public void AddBook(Book book){
        books.add(book);
    }

    public void RemoveBook(Book book){
        books.remove(book);
    }

    public void ClearBooks(){
        books.clear();
    }



}
