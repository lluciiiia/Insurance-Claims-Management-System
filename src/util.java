package src;

import src.ClaimProcessManager.ClaimProcessManager;
import src.domain.Claim;

import java.util.*;

public class util {
    static Scanner scanner = new Scanner(System.in);

    public static void handleAddClaim(ClaimProcessManager claimProcessManager) {
        // TODO: Interact with the user to get information for the new claim
//        claimProcessManager.add(claim);
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
