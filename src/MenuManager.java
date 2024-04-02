package src;

import src.AdminOperationManager.AdminOperationManager;
import src.AdminOperationManager.AdminOperationManagerImpl;
import src.ClaimProcessManager.ClaimProcessManager;
import src.FileManager.FileManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/*
 * @author <Seokyung Kim - s3939114>
 */

public class MenuManager {

    public static void displayMenu() {
        System.out.println("Welcome to the Insurance Claims Management System!");
        System.out.println("1. Add Claim");
        System.out.println("2. Update Claim");
        System.out.println("3. Delete Claim");
        System.out.println("4. Get Claim Details");
        System.out.println("5. Get All Claims");
        System.out.println("6. Exit");
    }

    public static void handleUserInput(ClaimProcessManager claimProcessManager, HashMap<String, HashMap<String, ?>> dataMap, FileManager fileManager) {
        AdminOperationManager adminManager = new AdminOperationManagerImpl();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    adminManager.handleAddClaim(claimProcessManager, dataMap);
                    break;
                case 2:
                    adminManager.handleUpdateClaim(claimProcessManager, dataMap);
                    break;
                case 3:
                    adminManager.handleDeleteClaim(claimProcessManager);
                    break;
                case 4:
                    adminManager.handleGetClaim(claimProcessManager);
                    break;
                case 5:
                    adminManager.handleGetAllClaims(claimProcessManager);
                    break;
                case 6:
                    System.out.println("Exiting...");

                    try {
                        fileManager.saveFiles((HashMap<String, HashMap<String, ?>>) dataMap);
                    } catch (IOException e) {
                        System.out.println("An error occurred while saving data: " + e.getMessage());
                    }

                    System.out.println("System ended.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        } while (choice != 6);

        scanner.close();
    }

}
