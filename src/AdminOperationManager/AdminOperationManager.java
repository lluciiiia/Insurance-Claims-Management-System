package src.AdminOperationManager;

import src.ClaimProcessManager.ClaimProcessManager;

import java.util.HashMap;

/*
 * @author <Seokyung Kim - s3939114>
 */
public interface AdminOperationManager {
    void handleAddClaim(ClaimProcessManager claimProcessManager, HashMap<String, HashMap<String, ?>> dataMap);

    void handleUpdateClaim(ClaimProcessManager claimProcessManager, HashMap<String, HashMap<String, ?>> dataMap);

    void handleDeleteClaim(ClaimProcessManager claimProcessManager);

    void handleGetClaim(ClaimProcessManager claimProcessManager);

    void handleGetAllClaims(ClaimProcessManager claimProcessManager);
}

