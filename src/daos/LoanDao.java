package daos;

import business.Loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanDao extends Dao implements LoanDaoInterface {
    public LoanDao(String databaseName) {
        super(databaseName);
    }

    public LoanDao(Connection con) {
        super(con);
    }

    /**
     * @param userId
     * @return Loan, show all details in the loan list currently
     */

    //Get current details from Loan list
    @Override
    public List<Loan> getLoansCurrent(int userId) {
        List<Loan> loans = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            //Get a connection to the database
            conn = getConnection();
            String query = "SELECT * FROM loans WHERE userId = ? And returnDate = Null";
            ps = conn.prepareStatement(query);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while (rs.next()){
                Loan l = new Loan();
                l.setLoanId(rs.getInt("loanId"));
                l.setBookId(rs.getInt("bookId"));
                l.setUserId(rs.getInt("userId"));
                l.setDateOfLoan(rs.getDate("dateOfLoan"));
                l.setDueDate(rs.getDate("dueDate"));
                l.setReturnDate(rs.getDate("returnDate"));

                loans.add(l);
            }

        }catch (SQLException e){
            System.out.println("An exception occurred in the getLoansCurrent(): " + e.getMessage());
        } finally {
            try{
                rs.close();
            } catch (SQLException e) {
                System.out.println("An exception occurred when closing the ResultSet of the getLoanCurrent():" + e.getMessage());
            }
            try{
                ps.close();
            } catch (SQLException e) {
                System.out.println("An exception occurred when closing the PreparedStatement of the getLoanCurrent():" + e.getMessage());
            }
            freeConnection(conn);
        }
        return loans;
    }

    /**
     * @param userId
     * @return Loan, show all details in the loan list formerly
     */

    //Get former details from Loan list
    @Override
    public List<Loan> getLoansFormer(int userId) {
        List<Loan> loans = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            //Get a connection to the database
            conn = getConnection();
            String query = "SELECT * FROM loans WHERE userId = ? AND returnDate != Null";
            ps = conn.prepareStatement(query);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while (rs.next()){
                Loan l = new Loan();
                l.setLoanId(rs.getInt("loanId"));
                l.setBookId(rs.getInt("bookId"));
                l.setUserId(rs.getInt("userId"));
                l.setDateOfLoan(rs.getDate("dateOfLoan"));
                l.setDueDate(rs.getDate("dueDate"));
                l.setReturnDate(rs.getDate("returnDate"));

                loans.add(l);
            }

        }catch (SQLException e){
            System.out.println("An exception occurred in the getLoansFormer(): " + e.getMessage());
        } finally {
            try{
                rs.close();
            } catch (SQLException e) {
                System.out.println("An exception occurred when closing the ResultSet of the getLoansFormer():" + e.getMessage());
            }
            try{
                ps.close();
            } catch (SQLException e) {
                System.out.println("An exception occurred when closing the PreparedStatement of the getLoansFormer():" + e.getMessage());
            }
            freeConnection(conn);
        }
        return loans;
    }

    /**
     * @return
     */
    @Override
    public List<Loan> getLoanAsAdmin(int userType) {
        List<Loan> loans = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            //Get a connection to the database
            conn = getConnection();
            String query = "SELECT * FROM loans WHERE userType = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1,userType);
            rs = ps.executeQuery();
            while (rs.next()){
                Loan l = new Loan();
                l.setLoanId(rs.getInt("loanId"));
                l.setBookId(rs.getInt("bookId"));
                l.setUserId(rs.getInt("userId"));
                l.setDateOfLoan(rs.getDate("dateOfLoan"));
                l.setDueDate(rs.getDate("dueDate"));
                l.setReturnDate(rs.getDate("returnDate"));

                loans.add(l);
            }
        }catch (SQLException e){
            System.out.println("An exception occurred in the getLoanAsAdmin(): " + e.getMessage());
        } finally {
            try{
                rs.close();
            } catch (SQLException e) {
                System.out.println("An exception occurred when closing the ResultSet of the getLoanAsAdmin():" + e.getMessage());
            }
            try{
                ps.close();
            } catch (SQLException e) {
                System.out.println("An exception occurred when closing the PreparedStatement of the getLoanAsAdmin():" + e.getMessage());
            }
            freeConnection(conn);
        }
        return loans;
    }
}
