package src.FileManager;

import src.Claim;
import src.Customer;
import src.CustomerType;
import src.InsuranceCard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManagerImpl implements FileManager{
    private final Path CUSTOMERS_FILE = Paths.get("src/data/customers.txt");
    private final Path INSURANCE_CARDS_FILE = Paths.get("src/data/insurance_cards.txt");
    private final Path CLAIMS_FILE = Paths.get("src/data/claims.txt");
    private final Path RECEIVER_BANKING_INFO_FILE = Paths.get("src/data/receiver_banking_info.txt");
    private final Path CUSTOMER_RELATIONSHIPS_FILE = Paths.get("src/data/customer_relationships.txt");

    private List<Customer> customers = new ArrayList<>();

    @Override
    public boolean loadFiles() throws IOException {
        loadCustomersFromFile();
        loadCustomerRelationshipsFromFile();

//        System.out.println("Loaded Customers:");
//        for (Customer customer : customers) {
//            customer.printCustomer();
//        }

        loadInsuranceCardsFromFile();
        loadReceiverBankingInfoFromFile();
        loadClaimsFromFile();
        return true;
    }

    @Override
    public List<Customer> loadCustomersFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                String fullName = parts[1];
                CustomerType customerType = CustomerType.valueOf(parts[2]);
                Customer customer = new Customer(id, fullName, customerType);
                customers.add(customer);
            }
        }
        return customers;
    }

    @Override
    public void loadReceiverBankingInfoFromFile() throws IOException{
    }

    @Override
    public void loadCustomerRelationshipsFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_RELATIONSHIPS_FILE.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String policyHolderId = parts[0];
                List<Customer> dependents = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    String dependentId = parts[i];
                    Customer dependent = findCustomerById(dependentId);
                    if (dependent != null) {
                        dependents.add(dependent);
                    }
                }
                Customer policyHolder = findCustomerById(policyHolderId);
                if (policyHolder != null) {
                    policyHolder.setDependents(dependents);
                }
            }
        }
    }

    @Override
    public Customer findCustomerById(String customerId) {
        for (Customer customer : customers) {
            if (customer.getId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public List<InsuranceCard> loadInsuranceCardsFromFile() {
        return null;
    }

    @Override
    public List<Claim> loadClaimsFromFile() {
        return null;
    }
}
