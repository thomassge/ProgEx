package DataStructure;

import java.util.Date;

/**
 * The Book class represents a book in the program.
 */
public class Book {

    int id;
    String title;
    String author;
    String publisher;
    String isbn;
    String genre;
    String description;
    Date releaseDate;
    int qty;

    public Book(int id, String title, String author, String isbn, Date releaseDate, String publisher, String genre, String description, int qty) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.releaseDate = releaseDate;
        this.publisher = publisher;
        this.genre = genre;
        this.description = description;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getQty() {
        return qty;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title='" + title + '\'' + ", author='" + author + '\'' + ", publisher='" + publisher + '\'' + ", isbn='" + isbn + '\'' + ", genre='" + genre + '\'' + ", description='" + description + '\'' + ", release_date=" + releaseDate + ", qty=" + qty + '}';
    }
}
