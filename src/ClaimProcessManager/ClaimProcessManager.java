package src.ClaimProcessManager;

import src.domain.Claim;

import java.util.HashMap;
import java.util.List;

/*
 * @author <Seokyung Kim - s3939114>
 */

public interface ClaimProcessManager {
    void add(Claim claim);

    boolean update(Claim claim);

    boolean delete(String claimId);

    Claim getOne(String claimId);

    List<Claim> getAll();

    void addAll(HashMap<String, Claim> claims);
}