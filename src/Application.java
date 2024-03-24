package src;

import src.ClaimProcessManager.ClaimProcessManager;
import src.ClaimProcessManager.ClaimProcessManagerImpl;
import src.FileManager.FileManager;
import src.FileManager.FileManagerImpl;
import src.domain.Claim;
import src.domain.Customer;

import java.io.IOException;
import java.util.*;

/*
 * @author <Seokyung Kim - s3939114>
 */
public class Application {
    private HashMap<String, HashMap<String, ?>> dataMap;
    private final ClaimProcessManager claimProcessManager;
    private final FileManager fileManager;

    public Application(ClaimProcessManager claimProcessManager, HashMap<String, HashMap<String, ?>> dataMap, FileManager fileManager) {
        this.claimProcessManager = claimProcessManager;
        this.dataMap = dataMap;
        this.fileManager = fileManager;
    }

    public void start() {
        displayMenu();
        handleUserInput();
    }

    public void displayMenu() {
        System.out.println("Welcome to the Insurance Claims Management System!");
        System.out.println("1. Add Claim");
        System.out.println("2. Update Claim");
        System.out.println("3. Delete Claim");
        System.out.println("4. Get Claim Details");
        System.out.println("5. Get All Claims");
        System.out.println("6. Exit");
    }

    public void handleUserInput() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    util.handleAddClaim(claimProcessManager, dataMap);
                    break;
                case 2:
                    util.handleUpdateClaim(claimProcessManager);
                    break;
                case 3:
                    util.handleDeleteClaim(claimProcessManager);
                    break;
                case 4:
                    util.handleGetClaim(claimProcessManager);
                    break;
                case 5:
                    util.handleGetAllClaims(claimProcessManager);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    saveDataAndExit();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        } while (choice != 6);

        scanner.close();
    }

    private void saveDataAndExit() {
        try {
            // Save all data to files using FileManager
            fileManager.saveFiles((HashMap<String, HashMap<String, ?>>) dataMap);
        } catch (IOException e) {
            System.out.println("An error occurred while saving data: " + e.getMessage());
        }

        System.out.println("Exiting...");
    }

    public static void main(String[] args) throws IOException {
        // Initialize ClaimProcessManager and FileManager
        ClaimProcessManager claimProcessManager = new ClaimProcessManagerImpl();
        FileManager fileManager = new FileManagerImpl();
        HashMap<String, HashMap<String, ?>> dataMap = fileManager.loadFiles();

        // Retrieve claims from dataMap and add them to ClaimProcessManager
        HashMap<String, Claim> claims = (HashMap<String, Claim>) dataMap.get("Claim");
        claimProcessManager.addAll(claims);

        // Initialize Application with ClaimProcessManager and FileManager
        Application application = new Application(claimProcessManager, dataMap, fileManager);

        // Start the text-based user interface
        application.start();
    }
}
