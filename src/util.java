package src;

import src.ClaimProcessManager.ClaimProcessManager;
import src.domain.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class util {
    static Scanner scanner = new Scanner(System.in);
    static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void handleAddClaim(ClaimProcessManager claimProcessManager, HashMap<String, HashMap<String, ?>> dataMap) {
        System.out.println("Enter the following information for the new claim:");

        String claimId;
        do {
            System.out.print("Claim ID (format: f**********): ");
            claimId = scanner.nextLine();
            if (!isValidClaimId(claimId)) {
                System.out.println("Invalid claim ID format. Please enter a valid claim ID (format: f**********).");
            }
        } while (!isValidClaimId(claimId));

        Date claimDate;
        do {
            System.out.print("Claim Date (yyyy-MM-dd): ");
            String claimDateStr = scanner.nextLine();
            try {
                claimDate = dateFormat.parse(claimDateStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
                claimDate = null;
            }
        } while (claimDate == null);


        HashMap<String, Customer> customerHashMap = (HashMap<String, Customer>) dataMap.get("Customer");

        System.out.print("Insured Person ID: ");
        String insuredPersonId;
        Customer insuredPerson;

        do {
            insuredPersonId = scanner.nextLine();
            insuredPerson = customerHashMap.get(insuredPersonId);
            if (insuredPerson == null) {
                System.out.println("Insured Person with ID " + insuredPersonId + " not found. Please enter a valid ID.");
                System.out.print("Insured Person ID: ");
            }
        } while (insuredPerson == null);


        HashMap<String, InsuranceCard> insuranceCardsMap = (HashMap<String, InsuranceCard>) dataMap.get("InsuranceCard");
        System.out.print("Card Number: ");
        String cardNumber;
        InsuranceCard insuranceCard;

        do {
            cardNumber = scanner.nextLine();
            insuranceCard = insuranceCardsMap.get(cardNumber);
            if (insuranceCard == null) {
                System.out.println("Card Number " + cardNumber + " not found. Please enter a valid card number.");
                System.out.print("Card Number: ");
            }
        } while (insuranceCard == null);


        Date examDate;
        do {
            System.out.print("Exam Date (yyyy-MM-dd): ");
            String examDateStr = scanner.nextLine();
            try {
                examDate = dateFormat.parse(examDateStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
                examDate = null;
            }
        } while (examDate == null);

        // TODO: automatic claimId and cardNumber?
        List<String> documents;
        do {
            System.out.print("Documents (ClaimId_CardNumber_DocumentName.pdf) (separated by comma): ");
            String documentsStr = scanner.nextLine();
            documents = Arrays.asList(documentsStr.split(","));
        } while (!isValidDocuments(claimId, cardNumber, documents));

        double claimAmount;
        do {
            System.out.print("Claim Amount: ");
            claimAmount = scanner.nextDouble();
            scanner.nextLine();
        } while (claimAmount < 0);

        ClaimStatus status;
        do {
            System.out.print("Claim Status (NEW, PROCESSING, DONE): ");
            String statusStr = scanner.nextLine().toUpperCase();
            status = ClaimStatus.valueOfOrDefault(statusStr, null);
            if (status == null) {
                System.out.println("Invalid claim status. Please enter either NEW, PROCESSING, or DONE.");
            }
        } while (status == null);

        ReceiverBankingInfo receiverBankingInfo = getReceiverBankingInfoFromUser(dataMap);

        Claim claim = new Claim(claimId, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, receiverBankingInfo);

        claimProcessManager.add(claim);

        System.out.println("New claim added successfully!");
    }

    private static boolean isValidDocuments(String claimId, String cardNumber, List<String> documents) {
        for (String document : documents) {
            if (!isValidDocumentName(claimId, cardNumber, document)) {
                System.out.println("Invalid document name format. Document names should follow the format: ClaimId_CardNumber_DocumentName.pdf");
                return false;
            }
        }
        return true;
    }

    private static boolean isValidDocumentName(String claimId, String cardNumber, String document) {
        // Document name format: ClaimId_CardNumber_DocumentName.pdf
        String[] parts = document.split("_");

        // Check if the document name contains three parts
        if (parts.length != 3) {
            return false;
        }

        // Extract claim ID and card number from the document name
        String documentClaimId = parts[0];
        String documentCardNumber = parts[1];

        // Check if the claim ID and card number match the provided claim ID and card number
        if (!documentClaimId.equals(claimId) || !documentCardNumber.equals(cardNumber)) {
            return false;
        }

        // Extract the file extension
        String documentName = parts[2];
        String[] fileNameParts = documentName.split("\\.");

        // Check if the file name consists of two parts and the extension is "pdf"
        return fileNameParts.length == 2 && fileNameParts[1].equalsIgnoreCase("pdf");
    }



    public static ReceiverBankingInfo getReceiverBankingInfoFromUser(HashMap<String, HashMap<String, ?>> dataMap) {
        System.out.print("Enter Receiver Banking Info ID: ");
        String receiverBankingInfoId;

        HashMap<String, ReceiverBankingInfo> receiverBankingInfoMap = (HashMap<String, ReceiverBankingInfo>) dataMap.get("ReceiverBankingInfo");
        ReceiverBankingInfo receiverBankingInfo;

        // Loop until a valid ID is provided
        while (true) {
            receiverBankingInfoId = scanner.nextLine();
            receiverBankingInfo = receiverBankingInfoMap.get(receiverBankingInfoId);

            // Check if the entered ID exists in the hashmap
            if (receiverBankingInfo != null) {
                break; // Exit the loop if a valid ID is found
            } else {
                System.out.println("Receiver Banking Info ID not found. Please enter a valid ID:");
            }
        }
        return receiverBankingInfo;
    }



    public static void handleUpdateClaim(ClaimProcessManager claimProcessManager, HashMap<String, HashMap<String, ?>> dataMap) {
        System.out.println("Enter the claim ID to update: ");
        String claimId = scanner.nextLine();

        Claim claimToUpdate = claimProcessManager.getOne(claimId);
        if (claimToUpdate == null) {
            System.out.println("Claim with ID " + claimId + " not found.");
            return;
        }
        System.out.println("Claim Information:");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-15s %-20s %-15s %-15s %-10s %-30s %-20s%n",
                "ID", "Claim Date", "Insured Person", "Card Number", "Exam Date", "Claim Amount", "Status", "Receiver Banking Info", "Documents");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        claimToUpdate.printClaim();

        System.out.println("Choose the field to update:");
        System.out.println("1. Claim Date");
        System.out.println("2. Insured Person");
        System.out.println("3. Card Number");
        System.out.println("4. Exam Date");
        System.out.println("5. Documents");
        System.out.println("6. Claim Amount");
        System.out.println("7. Claim Status");
        System.out.println("8. Receiver Banking Info");
        System.out.print("Enter your choice: ");

        int choice = 0;
        while (choice < 1 || choice > 8) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 8) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        // Switch case to handle each choice
        switch (choice) {
            case 1:
                Date newClaimDate = null;
                while (newClaimDate == null) {
                    try {
                        System.out.print("Enter new claim date (yyyy-MM-dd): ");
                        String claimDateStr = scanner.nextLine();
                        newClaimDate = dateFormat.parse(claimDateStr);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
                    }
                }
                claimToUpdate.setClaimDate(newClaimDate);
                break;
            case 2:
                Customer newInsuredPerson = null;
                while (newInsuredPerson == null) {
                    System.out.print("Enter new Insured Person ID: ");
                    String newInsuredPersonId = scanner.nextLine();
                    HashMap<String, Customer> customerHashMap = (HashMap<String, Customer>) dataMap.get("Customer");
                    newInsuredPerson = customerHashMap.get(newInsuredPersonId);
                    if (newInsuredPerson == null) {
                        System.out.println("Insured Person with ID " + newInsuredPersonId + " not found.");
                    }
                }
                claimToUpdate.setInsuredPerson(newInsuredPerson);
                break;
            case 3:
                InsuranceCard newInsuranceCard = null;
                String newCardNumber = null;
                while (newInsuranceCard == null) {
                    System.out.print("Enter new card number: ");
                    newCardNumber = scanner.nextLine();
                    HashMap<String, InsuranceCard> insuranceCardsMap = (HashMap<String, InsuranceCard>) dataMap.get("InsuranceCard");
                    newInsuranceCard = insuranceCardsMap.get(newCardNumber);
                    if (newInsuranceCard == null) {
                        System.out.println("Card Number " + newCardNumber + " not found. Please enter a valid card number.");
                        System.out.print("Card Number: ");
                    }
                }
                claimToUpdate.setCardNumber(newCardNumber);
                break;
            case 4:
                Date newExamDate = null;
                while (newExamDate == null) {
                    try {
                        System.out.print("Enter new exam date (yyyy-MM-dd): ");
                        String examDateStr = scanner.nextLine();
                        newExamDate = dateFormat.parse(examDateStr);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
                    }
                }
                claimToUpdate.setExamDate(newExamDate);
                break;
            case 5:
                List<String> newDocuments = null;
                String cardNumber = claimToUpdate.getCardNumber();
                do {
                    System.out.print("Enter new documents (ClaimId_CardNumber_DocumentName.pdf) (separated by comma): ");
                    String documentsStr = scanner.nextLine();
                    newDocuments = Arrays.asList(documentsStr.split(","));
                } while (!isValidDocuments(claimId, cardNumber, newDocuments));
                claimToUpdate.setDocuments(newDocuments);
                break;
            case 6:
                double newClaimAmount = 0;
                while (newClaimAmount <= 0) {
                    System.out.print("Enter new claim amount: ");
                    try {
                        newClaimAmount = Double.parseDouble(scanner.nextLine());
                        if (newClaimAmount <= 0) {
                            System.out.println("Claim amount must be greater than zero.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }
                claimToUpdate.setClaimAmount(newClaimAmount);
                break;
            case 7:
                ClaimStatus newStatus = null;
                while (newStatus == null) {
                    System.out.print("Enter new claim status (NEW, PROCESSING, DONE): ");
                    String newStatusStr = scanner.nextLine().toUpperCase();
                    if (!isValidClaimStatus(newStatusStr)) {
                        System.out.println("Invalid claim status. Please enter either NEW, PROCESSING, or DONE.");
                    } else {
                        newStatus = ClaimStatus.valueOf(newStatusStr);
                    }
                }
                claimToUpdate.setStatus(newStatus);
                break;
            case 8:
                ReceiverBankingInfo newReceiverBankingInfo = null;
                while (newReceiverBankingInfo == null) {
                    System.out.print("Enter new Receiver Banking Info ID: ");
                    String newReceiverBankingInfoId = scanner.nextLine();
                    HashMap<String, ReceiverBankingInfo> receiverBankingInfoHashMap = (HashMap<String, ReceiverBankingInfo>) dataMap.get("ReceiverBankingInfo");
                    newReceiverBankingInfo = receiverBankingInfoHashMap.get(newReceiverBankingInfoId);
                    if (newReceiverBankingInfo == null) {
                        System.out.println("Receiver Banking Info ID not found. Please enter a valid ID.");
                    }
                }
                claimToUpdate.setReceiverBankingInfo(newReceiverBankingInfo);
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                return;
        }

        claimProcessManager.update(claimToUpdate);
        System.out.println("Claim updated successfully!");
    }


    public static void handleDeleteClaim(ClaimProcessManager claimProcessManager){
        System.out.print("Enter the claim ID to delete: ");
        String claimId = scanner.nextLine();

        if (isValidClaimId(claimId)) {
            if (claimProcessManager.delete(claimId)) {
                System.out.println("The claim with ID " + claimId + " has been successfully deleted.");
            } else {
                System.out.println("Error: The claim with ID " + claimId + " does not exist.");
            }
        } else {
            System.out.println("Error: Invalid claim ID format.");
        }
    }

    public static void handleGetClaim(ClaimProcessManager claimProcessManager) {

        System.out.print("Enter the claim ID to get: ");
        String claimId = scanner.nextLine();

        if (isValidClaimId(claimId)) {
            Claim claim = claimProcessManager.getOne(claimId);
            if (claim == null) {
                System.out.println("Error: The claim with ID " + claimId + " does not exist.");
            }
            System.out.println("Claim Information:");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-15s %-20s %-15s %-20s %-15s %-15s %-10s %-30s %-20s%n",
                    "ID", "Claim Date", "Insured Person", "Card Number", "Exam Date", "Claim Amount", "Status", "Receiver Banking Info", "Documents");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            claim.printClaim();
        } else {
            System.out.println("Invalid claim ID format. Please enter a valid claim ID (format: f**********).");
        }

    }

    // Method to validate the format of the claim ID
    private static boolean isValidClaimId(String claimId) {
        // Check if the claim ID follows the specified format (f********** where * is a digit)
        return claimId.matches("f\\d{10}");
    }

    public static boolean isValidClaimStatus(String statusStr) {
        for (ClaimStatus status : ClaimStatus.values()) {
            if (status.name().equals(statusStr)) {
                return true;
            }
        }
        return false;
    }

    public static void handleGetAllClaims(ClaimProcessManager claimProcessManager){
        List<Claim> claims = claimProcessManager.getAll();

        System.out.println("Claims Information:");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-15s %-20s %-15s %-15s %-10s %-30s %-20s%n",
                "ID", "Claim Date", "Insured Person", "Card Number", "Exam Date", "Claim Amount", "Status", "Receiver Banking Info", "Documents");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Claim claim: claims) {
            claim.printClaim();
        }
    }
}
