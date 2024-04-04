package src.domain;

/*
 * @author <Seokyung Kim - s3939114>
 */

public class ReceiverBankingInfo {
    private String id;
    private String bank;
    private String name;
    private String number;

    public ReceiverBankingInfo(String id, String bank, String name, String number) {
        this.id = id;
        this.bank = bank;
        this.name = name;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public String getBank() {
        return bank;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

}