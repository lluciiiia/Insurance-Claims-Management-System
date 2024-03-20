package src.FileManager;

import src.Claim;
import src.Customer;
import src.InsuranceCard;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/*
 * @author <Seokyung Kim - s3939114>
 */


public interface FileManager {

    public boolean loadFiles() throws IOException;

    void loadReceiverBankingInfoFromFile() throws IOException;

    void loadCustomerRelationshipsFromFile() throws IOException;

    public List<Customer> loadCustomersFromFile() throws IOException;

    Customer findCustomerById(String customerId);

    public List<InsuranceCard> loadInsuranceCardsFromFile() throws IOException;
    public List<Claim> loadClaimsFromFile() throws IOException;

}
