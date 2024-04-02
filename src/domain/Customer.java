package src.domain;

import static src.domain.CustomerType.*;

import java.util.ArrayList;
import java.util.List;

/*
 * @author <Seokyung Kim - s3939114>
 */

public class Customer {
    private String id;
    private String fullName;
    private InsuranceCard insuranceCard;
    private List<Claim> claims;
    private CustomerType customerType;
    private List<Customer> dependents;

    public Customer(String id, String fullName, CustomerType customerType) {
        this.id = id;
        this.fullName = fullName;
        this.customerType = customerType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public List<Claim> getClaims() {
        return claims;
    }

    public void setClaims(List<Claim> claims) {
        this.claims = claims;
    }

    public void addClaim(Claim claim) {
        if (this.claims == null) {
            this.claims = new ArrayList<Claim>();
        }
        this.claims.add(claim);
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public List<Customer> getDependents() {
        if (this.customerType == DEPENDENT) {
            throw new UnsupportedOperationException("Only policy holders can have dependents.");
        }
        return dependents;
    }

    public void addDependent(Customer dependent) {
        if (this.customerType == DEPENDENT || dependent.getCustomerType() == POLICY_HOLDER) {
            throw new UnsupportedOperationException("Only dependents can have policy holders.");
        }
        if (this.dependents.isEmpty()) {
            throw new UnsupportedOperationException("Please set dependents before adding a new dependent.");
        }
        this.dependents.add(dependent);
    }

    public void setDependents(List<Customer> dependents) {
        if (this.customerType != CustomerType.POLICY_HOLDER) {
            throw new UnsupportedOperationException("Only policy holders can have dependents.");
        }
        if (this.dependents != null) {
            throw new UnsupportedOperationException("A list of dependents already exists. To modify the list, please clear the existing dependents first.");
        }
        this.dependents = dependents;
    }

    public void printCustomer() {
        System.out.println("Customer ID: " + this.getId());
        System.out.println("Full Name: " + this.getFullName());
        System.out.println("Customer Type: " + this.getCustomerType());
        if (this.getInsuranceCard() != null) {
            System.out.println("Insurance Card Number: " + this.getInsuranceCard().getCardNumber());
            System.out.println("Insurance Card Holder: " + this.getInsuranceCard().getCardHolder().getId());
            System.out.println("Policy Owner: " + this.getInsuranceCard().getPolicyOwner());
            System.out.println("Expiration Date: " + this.getInsuranceCard().getExpirationDate());
        }
        if (this.getCustomerType() == POLICY_HOLDER && this.getDependents() != null && !this.getDependents().isEmpty()) {
            System.out.println("Dependents:");
            for (Customer dependent : this.getDependents()) {
                System.out.println("\tDependent ID: " + dependent.getId());
                System.out.println("\tDependent Full Name: " + dependent.getFullName());
                System.out.println("\tDependent Customer Type: " + dependent.getCustomerType());
            }
        }
    }

}
