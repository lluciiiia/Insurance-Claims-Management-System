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

    public String getFullName() {
        return fullName;
    }

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
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

    public List<Customer> getDependents() {
        if (this.customerType == DEPENDENT) {
            throw new UnsupportedOperationException("Only policy holders can have dependents.");
        }
        return dependents;
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

}
