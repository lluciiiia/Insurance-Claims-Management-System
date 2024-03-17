package src;
/*
 * @author <Seokyung Kim - s3939114>
 */

import java.util.List;

public class SimpleClaimProcessManager implements ClaimProcessManager {
    private List<Claim> claims;

    @Override
    public void add(Claim claim) {
        claims.add(claim);
    }

    @Override
    public void update(Claim claim) {
        // Implement update logic
    }

    @Override
    public void delete(String claimId) {
        // Implement delete logic
    }

    @Override
    public Claim getOne(String claimId) {
        // Implement getOne logic
        return null;
    }

    @Override
    public List<Claim> getAll() {
        return claims;
    }
}