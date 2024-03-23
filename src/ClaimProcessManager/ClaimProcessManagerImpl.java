package src.ClaimProcessManager;
/*
 * @author <Seokyung Kim - s3939114>
 */

import src.domain.Claim;

import java.util.ArrayList;
import java.util.List;

public class ClaimProcessManagerImpl implements ClaimProcessManager {
    private List<Claim> claims;

    @Override
    public void add(Claim claim) {
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

    @Override
    public void addAll(List<Claim> claims) {
        this.claims = new ArrayList<Claim>();
        this.claims.addAll(claims);
    }
}