package business;

import java.util.Date;
import java.util.Objects;

public class Loan {

    private int loanId;
    private int bookId;
    private int userId;
    private Date dateOfLoan;
    private Date dueDate;
    private Date returnDate;

    public Loan() {

    }

    public Loan(int loanId,int bookId, int userId, Date dateOfLoan, Date dueDate, Date returnDate){
        this.loanId = loanId;
        this.bookId = bookId;
        this.userId = userId;
        this.dateOfLoan = dateOfLoan;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDateOfLoan() {
        return dateOfLoan;
    }

    public void setDateOfLoan(Date dateOfLoan) {
        this.dateOfLoan = dateOfLoan;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return loanId == loan.loanId && bookId == loan.bookId && userId == loan.userId && Objects.equals(dateOfLoan, loan.dateOfLoan) && Objects.equals(dueDate, loan.dueDate) && Objects.equals(returnDate, loan.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, bookId, userId, dateOfLoan, dueDate, returnDate);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", bookId=" + bookId +
                ", userId=" + userId +
                ", dateOfLoan=" + dateOfLoan +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
