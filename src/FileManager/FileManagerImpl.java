package src.FileManager;

import src.*;

import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;

public class FileManagerImpl implements FileManager{
    private final Path CUSTOMERS_FILE = Paths.get("src/data/customers.txt");
    private final Path CUSTOMER_RELATIONSHIPS_FILE = Paths.get("src/data/customer_relationships.txt");
    private final Path INSURANCE_CARDS_FILE = Paths.get("src/data/insurance_cards.txt");
    private final Path RECEIVER_BANKING_INFO_FILE = Paths.get("src/data/receiver_banking_info.txt");
    private final Path CLAIMS_FILE = Paths.get("src/data/claims.txt");

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private List<Customer> customerList = new ArrayList<>();
    private HashMap<String, Customer> customersMap = new HashMap<>();
    private List<InsuranceCard> insuranceCardList = new ArrayList<>();
    private List<ReceiverBankingInfo> receiverBankingInfoList = new ArrayList<>();
    private HashMap<String, ReceiverBankingInfo> receiverBankingInfoHashMap = new HashMap<>();
    private List<Claim> claimList = new ArrayList<>();

    @Override
    public boolean loadFiles() throws IOException {
        loadCustomersFromFile();
        loadCustomerRelationshipsFromFile();
        loadInsuranceCardsFromFile();
        System.out.println("Loaded Customers:");
        for (Customer customer : customerList) {
            customer.printCustomer();
        }
        loadReceiverBankingInfoFromFile();
        loadClaimsFromFile();

        System.out.println("Claims Information:");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-15s %-20s %-15s %-15s %-10s %-30s %-20s%n",
                "ID", "Claim Date", "Insured Person", "Card Number", "Exam Date", "Claim Amount", "Status", "Receiver Banking Info", "Documents");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Claim claim: claimList) {
            claim.printClaim();
        }


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
                customerList.add(customer);
                customersMap.put(id, customer);
            }
        }
        return customerList;
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
                Date expirationDate = dateFormat.parse(dateString);

                Customer dependent = customersMap.get(dependentId);

                InsuranceCard insuranceCard = new InsuranceCard(cardNumber, dependent, policyOwner, expirationDate);
                dependent.setInsuranceCard(insuranceCard);

                insuranceCardList.add(insuranceCard);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return insuranceCardList;
    }


    @Override
    public void loadReceiverBankingInfoFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(RECEIVER_BANKING_INFO_FILE.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                String bank = parts[1];
                String insuredCustomerId = parts[2];
                String number = parts[3];

                String insuredCustomerName = customersMap.get(insuredCustomerId).getFullName();
                ReceiverBankingInfo receiverBankingInfo = new ReceiverBankingInfo(id, bank, insuredCustomerName, number);
                receiverBankingInfoList.add(receiverBankingInfo);
                receiverBankingInfoHashMap.put(id, receiverBankingInfo);
            }
        }
    }

    @Override
    public List<Claim> loadClaimsFromFile() {


        try (BufferedReader reader = new BufferedReader(new FileReader(CLAIMS_FILE.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                Date claimDate = dateFormat.parse(parts[1]);
                String insuredPersonId = parts[2];
                String cardNumber = parts[3];
                Date examDate = dateFormat.parse(parts[4]);
                List<String> documentList = Arrays.asList(parts[5].split(";")); // Splitting documents
                double claimAmount = Double.parseDouble(parts[6]);
                ClaimStatus status = ClaimStatus.valueOf(parts[7]);
                String receiverBankingInfoId = parts[8];

                ReceiverBankingInfo receiverBankingInfo = receiverBankingInfoHashMap.get(receiverBankingInfoId);
                if (receiverBankingInfo == null) {
                    throw new IllegalArgumentException("Receiver Banking Info not found for card number: " + cardNumber);
                }

                Customer insuredPerson = customersMap.get(insuredPersonId);
                if (insuredPerson == null) {
                    throw new IllegalArgumentException("Insured Person (Customer) not found for ID: " + insuredPersonId);
                }

                Claim claim = new Claim(id, claimDate, insuredPerson, cardNumber, examDate, documentList, claimAmount, status, receiverBankingInfo);

                insuredPerson.addClaim(claim);

                claimList.add(claim);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return claimList;
    }

}
