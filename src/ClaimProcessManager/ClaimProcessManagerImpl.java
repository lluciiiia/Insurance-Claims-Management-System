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
        if (this.claims == null) {
            this.claims = new ArrayList<Claim>();
        }
        this.claims.add(claim);
    }

    @Override
    public void update(Claim claim) {
        // Implement update logic
    }

    @Override
    public void delete(String claimId) {
        if (this.claims != null) {
            this.claims.removeIf(claim -> claim.getId().equals(claimId));
        }
    }

    @Override
    public Claim getOne(String claimId) {
        if (this.claims != null) {
            for (Claim claim : this.claims) {
                if (claim.getId().equals(claimId)) {
                    return claim;
                }
            }
        }
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