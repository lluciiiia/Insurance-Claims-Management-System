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

        System.out.print("Claim ID (format: f**********): ");
        String claimId = scanner.nextLine();
        // TODO: handle the duplicated Id
        if (!isValidClaimId(claimId)) {
            System.out.println("Invalid claim ID format. Please enter a valid claim ID (format: f**********).");
        }

        Date claimDate = null;
        try {
            System.out.print("Claim Date (yyyy-MM-dd): ");
            String claimDateStr = scanner.nextLine();
            claimDate = dateFormat.parse(claimDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
            return;
        }

        System.out.print("Insured Person ID: ");
        String insuredPersonId = scanner.nextLine();

        HashMap<String, Customer> customerHashMap = (HashMap<String, Customer>) dataMap.get("Customer");
        Customer insuredPerson = customerHashMap.get(insuredPersonId);
        if (insuredPerson == null) {
            System.out.println("Insured Person with ID " + insuredPersonId + " not found.");
            return;
        }

        System.out.print("Card Number: ");
        String cardNumber = scanner.nextLine(); // Assuming the user enters a valid card number

        Date examDate = null;
        try {
            System.out.print("Exam Date (yyyy-MM-dd): ");
            String examDateStr = scanner.nextLine();
            examDate = dateFormat.parse(examDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
            return;
        }

        System.out.print("Documents (separated by comma): ");
        String documentsStr = scanner.nextLine();
        List<String> documents = Arrays.asList(documentsStr.split(","));

        System.out.print("Claim Amount: ");
        double claimAmount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character

        System.out.print("Claim Status (NEW, PROCESSING, DONE): ");
        String statusStr = scanner.nextLine().toUpperCase();
        if (!isValidClaimStatus(statusStr)) {
            System.out.println("Invalid claim status. Please enter either NEW, PROCESSING, or DONE.");
        }
        ClaimStatus status = ClaimStatus.valueOf(statusStr);

        // Get Receiver Banking Info
        HashMap<String, ReceiverBankingInfo> receiverBankingInfoHashMap = (HashMap<String, ReceiverBankingInfo>) dataMap.get("ReceiverBankingInfo");
        ReceiverBankingInfo receiverBankingInfo = getReceiverBankingInfoFromUser(receiverBankingInfoHashMap);

        // Create the claim object
        Claim claim = new Claim(claimId, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status, receiverBankingInfo);

        // Add the claim to the ClaimProcessManager
        claimProcessManager.add(claim);
        
        System.out.println("New claim added successfully!");
    }

    public static ReceiverBankingInfo getReceiverBankingInfoFromUser(HashMap<String, ReceiverBankingInfo> receiverBankingInfoMap) {
        Scanner scanner = new Scanner(System.in);

        // Display prompt and get receiver banking info ID from user
        System.out.print("Enter Receiver Banking Info ID: ");
        String receiverBankingInfoId = scanner.nextLine();

        // Check if the entered ID exists in the receiver banking info map
        ReceiverBankingInfo receiverBankingInfo = receiverBankingInfoMap.get(receiverBankingInfoId);
        if (receiverBankingInfo == null) {
            // If the ID does not exist, inform the user and return null
            System.out.println("Receiver Banking Info ID not found. Please enter a valid ID.");
            return null;
        }

        // Return the ReceiverBankingInfo object corresponding to the entered ID
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
