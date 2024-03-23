package src.domain;
/*
 * @author <Seokyung Kim - s3939114>
 */

import java.util.List;

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

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}