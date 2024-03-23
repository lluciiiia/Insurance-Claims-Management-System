package src.ClaimProcessManager;
/*
 * @author <Seokyung Kim - s3939114>
 */

import src.domain.Claim;

import java.util.List;

public interface ClaimProcessManager {
    void add(Claim claim);

    void update(Claim claim);

    boolean delete(String claimId);

    Claim getOne(String claimId);

    List<Claim> getAll();

    void addAll(List<Claim> claims);
}