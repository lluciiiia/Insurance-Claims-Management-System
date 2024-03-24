package src;

import src.ClaimProcessManager.ClaimProcessManager;
import src.domain.Claim;
import src.domain.ClaimStatus;
import src.domain.Customer;
import src.domain.ReceiverBankingInfo;

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


        System.out.print("Card Number: ");
        // TODO: validate if the card number exists in the insurance card, if it doesn't, ask the user to input until it exists.
        String cardNumber = scanner.nextLine();

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

        List<String> documents;
        do {
            System.out.print("Documents (separated by comma): ");
            String documentsStr = scanner.nextLine();
            documents = Arrays.asList(documentsStr.split(","));
        } while (!isValidDocuments(documents));

        double claimAmount;
        do {
            System.out.print("Claim Amount: ");
            claimAmount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
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

        // Create the claim object
        Claim claim = new Claim(claimId, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, receiverBankingInfo);

        // Add the claim to the ClaimProcessManager
        claimProcessManager.add(claim);

        System.out.println("New claim added successfully!");
    }

    private static boolean isValidDocuments(List<String> documents) {
        for (String document : documents) {
            if (!isValidDocumentName(document)) {
                System.out.println("Invalid document name format. Document names should follow the format: ClaimId_CardNumber_DocumentName.pdf");
                return false;
            }
        }
        return true;
    }

    private static boolean isValidDocumentName(String document) {
        // TODO: Validate if the claim and cardNumber match and exist
        // Document name format: ClaimId_CardNumber_DocumentName.pdf
        String[] parts = document.split("_");
        if (parts.length != 3) {
            return false;
        }
        String[] fileNameParts = parts[2].split("\\.");
        return fileNameParts.length == 2 && fileNameParts[1].equalsIgnoreCase("pdf");
    }


    public static ReceiverBankingInfo getReceiverBankingInfoFromUser(HashMap<String, HashMap<String, ?>> dataMap) {
        System.out.print("Enter Receiver Banking Info ID: ");
        String receiverBankingInfoId = scanner.nextLine();
        HashMap<String, ReceiverBankingInfo> receiverBankingInfoMap = (HashMap<String, ReceiverBankingInfo>) dataMap.get("ReceiverBankingInfo");
        ReceiverBankingInfo receiverBankingInfo = receiverBankingInfoMap.get(receiverBankingInfoId);
        if (receiverBankingInfo == null) {
            System.out.println("Receiver Banking Info ID not found. Please enter a valid ID.");
            return null;
        }
        return receiverBankingInfo;
    }


    public static void handleUpdateClaim(ClaimProcessManager claimProcessManager) {
//        claimProcessManager.update();
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
