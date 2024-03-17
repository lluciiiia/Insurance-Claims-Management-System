/*
 * @author <Seokyung Kim - s3939114>
 */

import java.util.List;

interface ClaimProcessManager {
    void add(Claim claim);
    void update(Claim claim);
    void delete(String claimId);
    Claim getOne(String claimId);
    List<Claim> getAll();
}