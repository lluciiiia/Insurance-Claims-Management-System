package src;

/*
* @author <Seokyung Kim - s3939114>
*/

import static src.CustomerType.*;
import java.util.List;

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

//    public Customer(String id, String fullName, InsuranceCard insuranceCard, List<Claim> claims, CustomerType customerType) {
//        this.id = id;
//        this.fullName = fullName;
//        this.insuranceCard = insuranceCard;
//        this.claims = claims;
//        this.customerType = customerType;
//    }
//
//    public Customer(String id, String fullName, InsuranceCard insuranceCard, List<Claim> claims, CustomerType customerType, List<Customer> dependents) {
//        this.id = id;
//        this.fullName = fullName;
//        this.insuranceCard = insuranceCard;
//        this.claims = claims;
//
//        if (customerType != CustomerType.POLICY_HOLDER && !dependents.isEmpty()) {
//            throw new IllegalArgumentException("Only policy holders can have dependents.");
//        }
//
//        this.customerType = customerType;
//        this.dependents = dependents;
//    }

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

    public CustomerType getCustomerType() { return customerType; }

    public void setCustomerType(CustomerType customerType) { this.customerType = customerType; }

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
        if (!this.dependents.isEmpty()) {
            throw new UnsupportedOperationException("A list of dependents already exists. To modify the list, please clear the existing dependents first.");
        }
        this.dependents = dependents;
    }


}
