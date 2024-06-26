package src.domain;

import java.util.Date;

/*
 * @author <Seokyung Kim - s3939114>
 */

public class InsuranceCard {
    private String cardNumber;
    private Customer cardHolder;
    private String policyOwner;
    private Date expirationDate;

    public InsuranceCard(String cardNumber, Customer cardHolder, String policyOwner, Date expirationDate) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.policyOwner = policyOwner;
        this.expirationDate = expirationDate;
    }

    // Getters and setters
    public String getCardNumber() {
        return cardNumber;
    }

    public Customer getCardHolder() {
        return cardHolder;
    }

    public String getPolicyOwner() {
        return policyOwner;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

}