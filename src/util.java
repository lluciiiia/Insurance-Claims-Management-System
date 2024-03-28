package src;

import src.domain.*;

import java.util.*;

public class util {
    static Scanner scanner = new Scanner(System.in);

    public static boolean isValidDocuments(String claimId, String cardNumber, List<String> documents) {
        for (String document : documents) {
            if (!isValidDocumentName(claimId, cardNumber, document)) {
                System.out.println("Invalid document name format. Document names should follow the format: ClaimId_CardNumber_DocumentName.pdf");
                return false;
            }
        }
        return true;
    }

    public static boolean isValidDocumentName(String claimId, String cardNumber, String document) {
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





    // Method to validate the format of the claim ID
    public static boolean isValidClaimId(String claimId) {
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


}
