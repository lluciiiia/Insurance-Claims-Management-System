package src.ClaimProcessManager;

import src.domain.Claim;

import java.util.*;

/*
 * @author <Seokyung Kim - s3939114>
 */


public class ClaimProcessManagerImpl implements ClaimProcessManager {
    private HashMap<String, Claim> claims;

    @Override
    public void add(Claim claim) {
        if (this.claims == null) {
            this.claims = new HashMap<String, Claim>();
        }
        this.claims.put(claim.getId(), claim);
    }

    @Override
    public void update(Claim claim) {
        // Implement update logic
    }

    @Override
    public boolean delete(String claimId) {
        Claim claim = claims.get(claimId);
        if (claim != null) {
            claims.remove(claimId);
            return true;
        }
        return false;
    }

    @Override
    public Claim getOne(String claimId) {
        Claim claim = claims.get(claimId);
        if (claim != null) {
            return claim;
        }
        return null;
    }

    @Override
    public List<Claim> getAll() {
        List<Claim> claimList = new ArrayList<>(claims.values());
        return claimList;
    }

    @Override
    public void addAll(HashMap<String, Claim> claims) {
        this.claims = claims;
    }
}