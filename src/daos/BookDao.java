package daos;

import business.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao extends Dao implements BookDaoInterface {

    public BookDao(String databaseName) {
        super(databaseName);
    }

    public BookDao(Connection con) {
        super(con);
    }

    /**
            * Retrieves a list of all books from the "books" table.
     *
             * @return A list of Book objects representing all books in the database.
     */
    public List<Book> DisplayAllBook() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        Book b = null;
        ArrayList<Book> books= new ArrayList();
        try {
            con = getConnection();

            String query = "select * from books";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                b = new Book(rs.getInt("bookId"), rs.getInt("genreId"), rs.getString("title"), rs.getString("author"), rs.getInt("numberOfCopies"));
                books.add(b);
            }

        } catch (SQLException e) {
            System.out.println("Exception occured in  the displayAllBooks() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in  the displayAllBooks() method:  " + e.getMessage());
            }
        }


        return books;
    }

    /**
     * Removes a book from the "books" table based on the given bookId.
     *
     * @param bookId The ID of the book to be removed.
     * @return The number of rows affected by the removal operation.
     */public int removeBook(int bookId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;

        try {
            con = getConnection();

            String command = "delete from books where bookId=?";
            ps = con.prepareStatement(command);
            ps.setInt(1, bookId);
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Exception occurred in  the removeBook() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occurred in  the removeBook() method:  " + e.getMessage());
            }
        }


        return rowsAffected;
    }
    /**
     * Adds a new book to the "books" table with the specified details.
     *
     * @param bookId          The ID of the new book.
     * @param genreId         The genre ID of the new book.
     * @param title           The title of the new book.
     * @param author          The author of the new book.
     * @param numberOfCopies  The initial number of copies of the new book.
     * @return The auto-generated ID of the new book, or -1 if the operation fails.
     */
    public int addBook(int bookId, int genreId, String title, String author, int numberOfCopies) {
        Connection con = null;
        PreparedStatement ps = null;
        //This will be used to hold the generated ID (i.e. the value auto-generated
        // by MySQL when inserting this entry into the database
        ResultSet generatedKeys = null;
        // Set the newId value to a default of -1
        // If the value returned by the method is -1, we know that the update failed
        // as the id value was never updated
        int newId = -1;

        try {
            con = this.getConnection();

            String query = "INSERT INTO books(bookId,genreId,title,author,numberOfCopies) VALUES (?, ?, ?, ?, ?)";

            // Need to get the id back, so have to tell the database to return the id it generates
            // That is why we include the Statement.RETURN_GENERATED_KEYS parameter
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, bookId);
            ps.setInt(2, genreId);
            ps.setString(3, title);
            ps.setString(4, author);
            ps.setInt(5, numberOfCopies);

            // Because this is CHANGING the database, use the executeUpdate method
            ps.executeUpdate();

            // Find out what the id generated for this entry was
            generatedKeys = ps.getGeneratedKeys();
            // If there was a result, i.e. if the entry was inserted successfully
            if (generatedKeys.next()) {
                // Get the id value that was generated by MySQL when the entry was inserted
                newId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("\tA problem occurred during the addBook method:");
            System.err.println("\t" + e.getMessage());
            newId = -1;
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.err.println("A problem occurred when closing down the addBook method:\n" + e.getMessage());
            }
        }
        return newId;
    }

    /**
     * Allows a user to borrow a book, updating the "books" table and checking user eligibility.
     *
     * @param bookId The ID of the book to be borrowed.
     * @param userId The ID of the user attempting to borrow the book.
     * @return The number of rows affected by the borrow operation.
     */
    public int borrowBook(int bookId, int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;

        try {
            con = getConnection();

            // To check if the user is allowed to borrow a book
            String able = "SELECT disable FROM users WHERE userId = ?";
            ps = con.prepareStatement(able);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                int userDisable = rs.getInt("disable");
                if (userDisable == 1) {
                    // Check if the book is available (has more than 0 copies)
                    String query = "SELECT numberOfCopies FROM books WHERE bookId = ?";
                    ps = con.prepareStatement(query);
                    ps.setInt(1, bookId);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        int numberOfCopies = rs.getInt("numberOfCopies");
                        if (numberOfCopies > 0) {
                            // If the user is allowed to borrow the book, decrease the number of copies and update the database.
                            String borrow = "UPDATE books SET numberOfCopies = ? WHERE bookId = ?";
                            ps = con.prepareStatement(borrow);
                            ps.setInt(1, numberOfCopies - 1);
                            ps.setInt(2, bookId);
                            rowsAffected = ps.executeUpdate();
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the BorrowBook method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occurred in the BorrowBook method: " + e.getMessage());
            }
        }

        return rowsAffected;
    }


    /**
     * Allows a user to return a borrowed book, updating the "books" table and checking user eligibility.
     *
     * @param bookId The ID of the book to be returned.
     * @param userId The ID of the user attempting to return the book.
     * @return The number of rows affected by the return operation.
     */
    public int returnBook(int bookId, int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;

        try {
            con = getConnection();

            // Check if the user has borrowed the book
            String borrowed = "SELECT bookId FROM user_books WHERE userId = ? AND bookId = ?";
            ps = con.prepareStatement(borrowed);
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            rs = ps.executeQuery();

            if (rs.next()) {
                // if the user has borrowed the book, increase the number of copies and update the database
                String returnBook = "UPDATE books SET numberOfCopies = numberOfCopies + 1 WHERE bookId = ?";
                ps = con.prepareStatement(returnBook);
                ps.setInt(1, bookId);
                rowsAffected = ps.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("Exception occurred in the ReturnBook method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occurred in the ReturnBook method: " + e.getMessage());
            }
        }

        return rowsAffected;
    }

    /**
     * Updates the number of copies for a specified book in the "books" table.
     *
     * @param bookId           The ID of the book to be updated.
     * @param newNumberOfCopies The new number of copies for the specified book.
     * @return The number of rows affected by the update operation.
     */
    public int UpdateNumberOfCopies(int bookId, int newNumberOfCopies) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            con = getConnection();

            // Allows for the increase or decease of the No. of copies for the specified bookId
            String query = "UPDATE books SET numberOfCopies = ? WHERE bookId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, newNumberOfCopies);
            ps.setInt(2, bookId);

            // Execute the update query
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Exception occurred in the UpdateNumberOfCopies method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.err.println("Exception occurred when closing down the UpdateNumberOfCopies method:\n" + e.getMessage());
            }
        }

        return rowsAffected;
    }

}
