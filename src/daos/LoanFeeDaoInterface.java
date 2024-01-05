package daos;

import business.Loan;
import business.LoanFee;

import java.util.ArrayList;

public interface LoanFeeDaoInterface {
    /**
     * @param loanId, the loan's Id
     * @return int, number of rows affected
     * method pays a user late fee
     */
    public int insertReturnDate(int loanId);

    /**
     * @param loanId, the loan's Id
     * @param fee,    the loan's fee
     * @return int, number of rows affected
     * method pays a user late while keeping track of the late fee
     **/
    public int payLateFee(int loanId, double fee);

    /**
     * deletes return Date of a loan
     *
     * @param loanId
     * @return 0 if no loan was updated or 1 if a loan was updated
     **/
    public int deleteReturnDate(int loanId);

    /* deletes late loan fee for a Loan
     *@param loanId
     *  @return 0 if no loan fee was deleted or 1 if a loan fee was deleted
     * **/
    public int deleteLateFee(int loanId);

    /**
     * gets a Loan
     *
     * @param loanId
     * @return Loan or null if no Loan is found
     **/
    public Loan getLoan(int loanId);

    /**
     * gets a LoanFee
     *
     * @param loanId
     * @return LoanFee or null if no LoanFee is found
     **/
    public LoanFee getLateLoanfee(int loanId);

    /**
     * gets a all overdue Loans of a particular user
     *
     * @param userId
     * @return an ArrayList of Loans
     **/
    public ArrayList<Loan> getOverDueLoans(int userId);

    /**
     * get a book's title when given a bookId
     *
     * @param bookId
     * @return the book's title or null if no book was found
     **/
    public String getBookName(int bookId);
}