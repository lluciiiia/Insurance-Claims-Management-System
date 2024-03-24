package src.FileManager;

import src.domain.Claim;
import src.domain.Customer;
import src.domain.InsuranceCard;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/*
 * @author <Seokyung Kim - s3939114>
 */


public interface FileManager {

    public HashMap<String, HashMap<String, ?>> loadFiles() throws IOException;

    void loadReceiverBankingInfoFromFile() throws IOException;

    void loadCustomerRelationshipsFromFile() throws IOException;

    public HashMap<String, Customer> loadCustomersFromFile() throws IOException;

    public HashMap<String, InsuranceCard> loadInsuranceCardsFromFile() throws IOException;
    public HashMap<String, Claim> loadClaimsFromFile() throws IOException;

    public void saveFiles(HashMap<String, HashMap<String, ?>> objectsHashMap) throws IOException;

}
