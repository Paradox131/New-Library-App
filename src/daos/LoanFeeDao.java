package daos;

import business.Loan;
import business.LoanFee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LoanFeeDao extends Dao implements LoanFeeDaoInterface {


    public LoanFeeDao(String databaseName) {
        super(databaseName);
    }

    public LoanFeeDao(Connection con) {
        super(con);
    }


    /**
     * @param loanId @return int, number of rows affected
     *               inserts a user's return date
     */
    @Override
    public int insertReturnDate(int loanId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        try {
            con = getConnection();

            String command = "update loans set returnDate=? where loanId=?";
            ps = con.prepareStatement(command);
            ps.setString(1, (formatter.format(now)));
            ps.setInt(2, loanId);
            rowsAffected = ps.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Exception occured in  the payLateFee() method: " + e.getMessage());
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
                System.out.println("Exception occured in  the payLateFee() method:  " + e.getMessage());
            }
        }


        return rowsAffected;
    }

    /**
     * @param loanId
     * @param fee    @return int, number of rows affected
     *               method pays a user late while keeping track of the late fee
     **/
    @Override
    public int payLateFee(int loanId, double fee) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;

        try {
            con = getConnection();

            String command = "insert into loanFee(loanId,loanFee) values(?,?) ";
            ps = con.prepareStatement(command);
            ps.setInt(1, loanId);
            ps.setDouble(2, fee);
            rowsAffected = ps.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Exception occured in  the payLateFee() method: " + e.getMessage());
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
                System.out.println("Exception occured in  the payLateFee() method:  " + e.getMessage());
            }
        }


        return rowsAffected;
    }

    /**
     * deletes return Date of a loan
     *
     * @param loanId
     * @return 0 if no loan was updated or 1 if a loan was updated
     **/
    public int deleteReturnDate(int loanId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        try {
            con = getConnection();

            String command = "update loans set returnDate=null where loanId=? ";
            ps = con.prepareStatement(command);
            ps.setInt(1, loanId);
            rowsAffected = ps.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Exception occured in  the deleteReturnDate() method: " + e.getMessage());
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
                System.out.println("Exception occured in  the deleteReturnDate() method:  " + e.getMessage());
            }
        }


        return rowsAffected;
    }

    /* deletes late loan fee for a Loan
     *@param loanId
     *  @return 0 if no loan fee was deleted or 1 if a loan fee was deleted
     * **/
    public int deleteLateFee(int loanId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;

        try {
            con = getConnection();

            String command = "delete from loanFee where loanId=?";
            ps = con.prepareStatement(command);
            ps.setInt(1, loanId);
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Exception occurred in  the deleteLateFee() method: " + e.getMessage());
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
                System.out.println("Exception occurred in  the deleteLateFee() method:  " + e.getMessage());
            }
        }


        return rowsAffected;
    }

    /**
     * gets a Loan
     *
     * @param loanId
     * @return Loan or null if no Loan is found
     **/
    public Loan getLoan(int loanId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        Loan l = null;
        try {
            con = getConnection();

            String query = "select * from loans where loanId= ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, loanId);
            rs = ps.executeQuery();

            if (rs.next()) {
                l = new Loan(rs.getInt("loanId"),rs.getInt("bookId"),rs.getInt("userId"),rs.getDate("dateOfLoan"),rs.getDate("dueDate"),rs.getDate("returnDate"));
            }

        } catch (SQLException e) {
            System.out.println("Exception occured in  the getLoan() method: " + e.getMessage());
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
                System.out.println("Exception occured in  the getLoan() method:  " + e.getMessage());
            }
        }


        return l;
    }

    /**
     * gets a LoanFee
     *
     * @param loanId
     * @return LoanFee or null if no LoanFee is found
     **/
    public LoanFee getLateLoanfee(int loanId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        LoanFee l = null;
        try {
            con = getConnection();

            String query = "select * from loanfee where loanId= ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, loanId);
            rs = ps.executeQuery();

            if (rs.next()) {
                l = new LoanFee(rs.getInt("loanId"), rs.getDouble("loanFee"));
            }

        } catch (SQLException e) {
            System.out.println("Exception occured in  the checkLateLoanfee() method: " + e.getMessage());
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
                System.out.println("Exception occured in  the checkLateLoanfee() method:  " + e.getMessage());
            }
        }


        return l;

    }

    /**
     * gets a all overdue Loans of a particular user
     *
     * @param userId
     * @return an ArrayList of Loans
     **/
    public ArrayList<Loan> getOverDueLoans(int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Loan> loans = new ArrayList();
        try {
            con = getConnection();

            String query = "Select * from loans where dueDate<now() and userId = ? and returnDate is NULL";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            // ps.setString(2, "IS NULL");
            rs = ps.executeQuery();

            while (rs.next()) {
                Loan l = new Loan(rs.getInt("loanId"),rs.getInt("bookId"),rs.getInt("userId"),rs.getDate("dateOfLoan"),rs.getDate("dueDate"),rs.getDate("returnDate"));
                loans.add(l);
            }

        } catch (SQLException e) {
            System.out.println("Exception occured in the getOverDueLoans() method: " + e.getMessage());
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
                System.out.println("Exception occured in the final section of the getOverDueLoans() method: " + e.getMessage());
            }
        }
        return loans;
    }

    /**
     * get a book's title when given a bookId
     *
     * @param bookId
     * @return the book's title or null if no book was found
     **/
    public String getBookName(int bookId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String title = null;
        try {
            con = getConnection();

            String query = "Select title from books where bookId=?  ";
            ps = con.prepareStatement(query);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();

            if (rs.next()) {
                title = rs.getString("title");
            }

        } catch (SQLException e) {
            System.out.println("Exception occured in the getOverDueLoans() method: " + e.getMessage());
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
                System.out.println("Exception occured in the final section of the getOverDueLoans() method: " + e.getMessage());
            }
        }
        return title;
    }

}
