package src;

import src.ClaimProcessManager.ClaimProcessManager;
import src.domain.Claim;

import java.util.List;

public class util {

    public static void handleAddClaim(ClaimProcessManager claimProcessManager) {
        // TODO: Interact with the user to get information for the new claim
//        claimProcessManager.add(claim);
    }
    public static void handleUpdateClaim(ClaimProcessManager claimProcessManager) {
//        claimProcessManager.update();
    }

    public static void handleDeleteClaim(ClaimProcessManager claimProcessManager){
//        claimProcessManager.delete();
    }

    public static void handleGetClaim(ClaimProcessManager claimProcessManager){
//        claimProcessManager.getOne();
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
