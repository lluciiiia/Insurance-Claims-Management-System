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

    public HashMap<String, List<?>> loadFiles() throws IOException;

    void loadReceiverBankingInfoFromFile() throws IOException;

    void loadCustomerRelationshipsFromFile() throws IOException;

    public List<Customer> loadCustomersFromFile() throws IOException;

    public List<InsuranceCard> loadInsuranceCardsFromFile() throws IOException;
    public List<Claim> loadClaimsFromFile() throws IOException;

    public void saveFiles(HashMap<String, List<?>> objectList) throws IOException;

}
