package business;

import java.util.Objects;


public class LoanFee {
    private int loanId;
    private Double loanFee;

    /*
     * @param loanId, int loanId
     * @param loanFee, double loanFee
     * */
    public LoanFee(int loanId, Double loanFee) {
        this.loanId = loanId;
        this.loanFee = loanFee;
    }

   /* public LoanFee() {
        this.loanId = 0;
        this.loanFee = 0.0;
    }*/

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public Double getLoanFee() {
        return loanFee;
    }

    public void setLoanFee(Double loanFee) {
        this.loanFee = loanFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanFee loanFee1 = (LoanFee) o;
        return loanId == loanFee1.loanId && Objects.equals(loanFee, loanFee1.loanFee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, loanFee);
    }

    @Override
    public String toString() {
        return "LoanFee{" +
                "loanId=" + loanId +
                ", loanFee=" + loanFee +
                '}';
    }
}
