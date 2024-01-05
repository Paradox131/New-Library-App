package daos;

import business.Loan;

import java.util.List;

public interface LoanDaoInterface {
    public List<Loan> getLoansCurrent(int userId);
    public List<Loan> getLoansFormer(int userId);
    public List<Loan> getLoanAsAdmin(int userType);
}
