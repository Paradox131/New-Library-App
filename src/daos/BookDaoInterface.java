package daos;

import business.Book;

import java.util.List;

public interface BookDaoInterface {
    public List<Book> DisplayAllBook();

    public int removeBook(int bookId);

    public int addBook(int bookId, int genreId, String title,String author,int numberOfCopies);

    public int borrowBook(int bookId, int userId);

    public  int returnBook(int bookId,int userId);

    public int UpdateNumberOfCopies(int bookId, int newNumberOfCopies);
}
