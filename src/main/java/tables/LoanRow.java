package tables;

/**
 * Klasė skirta laikyti paskolos eilutės lentelėje informacija
 **/
public class LoanRow {
    private final int DIGITS_AFTER_DECIMAL = 2;

    private final int month;
    private final double loanLeft;
    private final double monthlyPayment;
    private final double interest;
    private final double credit;


    public LoanRow(int month, double loanLeft, double monthlyPayment, double interest, double credit) {
        this.month = month;
        this.loanLeft = loanLeft;
        this.monthlyPayment = monthlyPayment;
        this.interest = interest;
        this.credit = credit;
    }

    public int getMonth() {
        return month;
    }

    /**
     *  Šie getteriai suapvalina gražinamą vertę 'DIGITS_AFTER_DECIMAL' tikslumu
     **/
    public String getLoanLeft() {
        return String.format("%.2f", loanLeft);
    }

    public String getMonthlyPayment() {
        return String.format("%.2f", monthlyPayment);
    }

    public String getInterest() {
        return String.format("%.2f", interest);
    }

    public String getCredit() {
        return String.format("%.2f", credit);
    }
}
