package src.FileManager;

import src.domain.*;

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

    private HashMap<String, Customer> customersMap = new HashMap<>();
    private HashMap<String, InsuranceCard> insuranceCardsMap = new HashMap<>();
    private HashMap<String, Claim> claimsMap = new HashMap<>();
    private HashMap<String, ReceiverBankingInfo> receiverBankingInfoHashMap = new HashMap<>();

    @Override
    public HashMap<String, HashMap<String, ?>> loadFiles() throws IOException {
        loadCustomersFromFile();
        loadCustomerRelationshipsFromFile();
        loadInsuranceCardsFromFile();
        loadReceiverBankingInfoFromFile();
        loadClaimsFromFile();
        HashMap<String, HashMap<String, ?>> dataMap = new HashMap<>();
        dataMap.put("Customer", customersMap);
        dataMap.put("InsuranceCard", insuranceCardsMap);
        dataMap.put("Claim", claimsMap);
        dataMap.put("ReceiverBankingInfo", receiverBankingInfoHashMap);

        return dataMap;
    }

    @Override
    public void loadCustomersFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                String fullName = parts[1];
                CustomerType customerType = CustomerType.valueOf(parts[2]);
                Customer customer = new Customer(id, fullName, customerType);
                customersMap.put(id, customer);
            }
        }
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
                if (policyHolder != null && policyHolder.getCustomerType()==CustomerType.POLICY_HOLDER) {
                    policyHolder.setDependents(dependents);
                }
            }
        }
    }

    @Override
    public void loadInsuranceCardsFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INSURANCE_CARDS_FILE.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String cardNumber = parts[0];
                String cardHolderId = parts[1];
                String policyOwner = parts[2];
                String dateString = parts[3];
                Date expirationDate = dateFormat.parse(dateString);

                Customer dependent = customersMap.get(cardHolderId);

                InsuranceCard insuranceCard = new InsuranceCard(cardNumber, dependent, policyOwner, expirationDate);
                dependent.setInsuranceCard(insuranceCard);

                insuranceCardsMap.put(cardNumber, insuranceCard);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void loadReceiverBankingInfoFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(RECEIVER_BANKING_INFO_FILE.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                String bank = parts[1];
                String name = parts[2];
                String number = parts[3];

                ReceiverBankingInfo receiverBankingInfo = new ReceiverBankingInfo(id, bank, name, number);
                receiverBankingInfoHashMap.put(id, receiverBankingInfo);
            }
        }
    }

    @Override
    public void loadClaimsFromFile() {

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

                claimsMap.put(id, claim);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveFiles(HashMap<String, HashMap<String, ?>> dataMap) throws IOException {
        saveCustomersToFile((HashMap<String, Customer>) dataMap.get("Customer"));
        saveCustomerRelationshipsToFile((HashMap<String, Customer>) dataMap.get("Customer"));
        saveInsuranceCardsToFile((HashMap<String, InsuranceCard>) dataMap.get("InsuranceCard"));
        saveReceiverBankingInfoToFile((HashMap<String, ReceiverBankingInfo>) dataMap.get("ReceiverBankingInfo"));
        saveClaimsToFile((HashMap<String, Claim>) dataMap.get("Claim"));
    }

    private void saveCustomersToFile(HashMap<String, Customer> customers) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMERS_FILE.toFile()))) {
            for (Customer customer : customers.values()) {
                writer.write(customer.getId() + "," + customer.getFullName() + "," + customer.getCustomerType().toString());
                writer.newLine();
            }
        }
    }

    private void saveCustomerRelationshipsToFile(HashMap<String, Customer> customers) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_RELATIONSHIPS_FILE.toFile()))) {
            for (Customer customer : customers.values()) {
                StringBuilder line = new StringBuilder(customer.getId());
                if (customer.getCustomerType() == CustomerType.POLICY_HOLDER) {
                    for (Customer dependent : customer.getDependents()) {
                        line.append(",").append(dependent.getId());
                    }
                }
                writer.write(line.toString());
                writer.newLine();
            }
        }
    }

    private void saveInsuranceCardsToFile(HashMap<String, InsuranceCard> insuranceCards) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INSURANCE_CARDS_FILE.toFile()))) {
            for (InsuranceCard insuranceCard : insuranceCards.values()) {
                writer.write(insuranceCard.getCardNumber() + "," +
                        insuranceCard.getCardHolder().getId() + "," +
                        insuranceCard.getPolicyOwner() + "," +
                        dateFormat.format(insuranceCard.getExpirationDate()));
                writer.newLine();
            }
        }
    }

    private void saveReceiverBankingInfoToFile(HashMap<String, ReceiverBankingInfo> receiverBankingInfo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECEIVER_BANKING_INFO_FILE.toFile()))) {
            for (ReceiverBankingInfo info : receiverBankingInfo.values()) {
                writer.write(info.getId() + "," + info.getBank() + "," + info.getName() + "," + info.getNumber());
                writer.newLine();
            }
        }
    }

    private void saveClaimsToFile(HashMap<String, Claim> claims) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLAIMS_FILE.toFile()))) {
            for (Claim claim : claims.values()) {
                writer.write(claim.getId() + "," +
                        dateFormat.format(claim.getClaimDate()) + "," +
                        claim.getInsuredPerson().getId() + "," +
                        claim.getCardNumber() + "," +
                        dateFormat.format(claim.getExamDate()) + "," +
                        String.join(";", claim.getDocuments()) + "," +
                        claim.getClaimAmount() + "," +
                        claim.getStatus().toString() + "," +
                        claim.getReceiverBankingInfo().getId());
                writer.newLine();
            }
        }
    }
}
