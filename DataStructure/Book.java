package DataStructure;

import java.util.Date;

public class Book {

    int id;
    String title;
    String author;
    String publisher;
    String isbn;
    String genre;
    String description;
    Date release_date;
    int qty;

    public Book(int id, String title, String author, String isbn, Date releaseDate, String publisher, String genre, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.release_date = releaseDate;
        this.publisher = publisher;
        this.genre = genre;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
