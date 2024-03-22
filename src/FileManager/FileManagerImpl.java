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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FileManagerImpl implements FileManager{
    private final Path CUSTOMERS_FILE = Paths.get("src/data/customers.txt");
    private final Path CUSTOMER_RELATIONSHIPS_FILE = Paths.get("src/data/customer_relationships.txt");
    private final Path INSURANCE_CARDS_FILE = Paths.get("src/data/insurance_cards.txt");
    private final Path CLAIMS_FILE = Paths.get("src/data/claims.txt");
    private final Path RECEIVER_BANKING_INFO_FILE = Paths.get("src/data/receiver_banking_info.txt");

    private List<Customer> customers = new ArrayList<>();
    private HashMap<String, Customer> customersMap = new HashMap<>();
    private List<InsuranceCard> insuranceCards = new ArrayList<>();

    @Override
    public boolean loadFiles() throws IOException {
        loadCustomersFromFile();
        loadCustomerRelationshipsFromFile();
        loadInsuranceCardsFromFile();
                System.out.println("Loaded Customers:");
        for (Customer customer : customers) {
            customer.printCustomer();
        }
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
                customersMap.put(id, customer);
            }
        }
        return customers;
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
                    Customer dependent = customersMap.get(dependentId);
                    if (dependent != null) {
                        dependents.add(dependent);
                    }
                }
                Customer policyHolder = customersMap.get(policyHolderId);
                if (policyHolder != null) {
                    policyHolder.setDependents(dependents);
                }
            }
        }
    }

    @Override
    public List<InsuranceCard> loadInsuranceCardsFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INSURANCE_CARDS_FILE.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String cardNumber = parts[0];
                String dependentId = parts[1];
                String policyOwner = parts[2];
                String dateString = parts[3];
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date expirationDate = dateFormat.parse(dateString);

                Customer dependent = customersMap.get(dependentId);

                InsuranceCard insuranceCard = new InsuranceCard(cardNumber, dependent, policyOwner, expirationDate);
                dependent.setInsuranceCard(insuranceCard);

                insuranceCards.add(insuranceCard);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return insuranceCards;
    }


    @Override
    public void loadReceiverBankingInfoFromFile() throws IOException{
    }


    @Override
    public List<Claim> loadClaimsFromFile() {
        return null;
    }
}
