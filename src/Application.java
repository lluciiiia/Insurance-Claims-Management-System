package src;

import src.ClaimProcessManager.ClaimProcessManager;
import src.ClaimProcessManager.ClaimProcessManagerImpl;
import src.FileManager.FileManager;
import src.FileManager.FileManagerImpl;

import java.io.IOException;
import java.util.*;

/*
 * @author <Seokyung Kim - s3939114>
 */
public class Application {
    private final ClaimProcessManager claimProcessManager;
    private final FileManager fileManager;

    public Application(ClaimProcessManager claimProcessManager, FileManager fileManager) {
        this.claimProcessManager = claimProcessManager;
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
//                    claimProcessManager.add();
                    break;
                case 2:
//                    claimProcessManager.update();
                    break;
                case 3:
//                    claimProcessManager.delete();
                    break;
                case 4:
//                    claimProcessManager.getOne();
                    break;
                case 5:
//                    claimProcessManager.getAll();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        } while (choice != 6);

        scanner.close();
    }
    public static void main(String[] args) throws IOException {
        // Initialize ClaimProcessManager and FileManager
        ClaimProcessManager claimProcessManager = new ClaimProcessManagerImpl();
        FileManager fileManager = new FileManagerImpl();
        HashMap<String, List> objectList = fileManager.loadFiles();

        // Initialize Application with ClaimProcessManager and FileManager
        Application application = new Application(claimProcessManager, fileManager);

        // Start the text-based user interface
        application.start();
    }
}
