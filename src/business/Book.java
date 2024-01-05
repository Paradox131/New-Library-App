package business;


import java.util.Objects;

public class Book {
    private int bookId;
    private int genreId;
    private String title;
    private String author;
    private int numberOfCopies;

    public Book(int bookId, int genreId, String title, String author, int numberOfCopies) {
        this.bookId = bookId;
        this.genreId = genreId;
        this.title = title;
        this.author = author;
        this.numberOfCopies = numberOfCopies;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
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

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return getBookId() == book.getBookId() && getGenreId() == book.getGenreId() && getNumberOfCopies() == book.getNumberOfCopies() && Objects.equals(getTitle(), book.getTitle()) && Objects.equals(getAuthor(), book.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getGenreId(), getTitle(), getAuthor(), getNumberOfCopies());
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", genreId=" + genreId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", numberOfCopies=" + numberOfCopies +
                '}';
    }
}