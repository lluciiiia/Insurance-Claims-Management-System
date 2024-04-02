package src;

import src.ClaimProcessManager.ClaimProcessManager;
import src.ClaimProcessManager.ClaimProcessManagerImpl;
import src.FileManager.FileManager;
import src.FileManager.FileManagerImpl;
import src.domain.Claim;

import java.io.IOException;
import java.util.*;

/*
 * @author <Seokyung Kim - s3939114>
 */

public class Application {
    private HashMap<String, HashMap<String, ?>> dataMap;
    private final ClaimProcessManager claimProcessManager;
    private final FileManager fileManager;

    public Application(ClaimProcessManager claimProcessManager, HashMap<String, HashMap<String, ?>> dataMap,
                       FileManager fileManager) {
        this.claimProcessManager = claimProcessManager;
        this.dataMap = dataMap;
        this.fileManager = fileManager;
    }

    public void start() {
        MenuManager.displayMenu();
        MenuManager.handleUserInput(claimProcessManager, dataMap, fileManager);
    }

    public static void main(String[] args) throws IOException {
        ClaimProcessManager claimProcessManager = new ClaimProcessManagerImpl();
        FileManager fileManager = new FileManagerImpl();
        HashMap<String, HashMap<String, ?>> dataMap = fileManager.loadFiles();

        HashMap<String, Claim> claims = (HashMap<String, Claim>) dataMap.get("Claim");
        claimProcessManager.addAll(claims);

        Application application = new Application(claimProcessManager, dataMap, fileManager);

        application.start();
    }
}
