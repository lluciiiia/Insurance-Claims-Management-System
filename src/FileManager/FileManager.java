package src.FileManager;

import java.io.IOException;
import java.util.HashMap;

/*
 * @author <Seokyung Kim - s3939114>
 */

public interface FileManager {

    public HashMap<String, HashMap<String, ?>> loadFiles() throws IOException;

    void loadReceiverBankingInfoFromFile() throws IOException;

    void loadCustomerRelationshipsFromFile() throws IOException;

    void loadCustomersFromFile() throws IOException;

    void loadInsuranceCardsFromFile() throws IOException;

    void loadClaimsFromFile() throws IOException;

    public void saveFiles(HashMap<String, HashMap<String, ?>> objectsHashMap) throws IOException;

}
