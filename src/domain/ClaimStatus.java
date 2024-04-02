package src.domain;

/*
 * @author <Seokyung Kim - s3939114>
 */

public enum ClaimStatus {
    NEW,
    PROCESSING,
    DONE;

    public static ClaimStatus valueOfOrDefault(String str, ClaimStatus defaultValue) {
        try {
            return ClaimStatus.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }
}