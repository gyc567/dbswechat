package com.dbs.model;

/**
 * Created by eric567 [email:gyc567@126.com]
 * on 11/16/2016.
 */
public class Transaction {

    private String payerName;
    private String payeeName;
    private String transferNote;
    private String transferDate;
    private String transferAmout;
    private TransferStatus tranferStatus;

    public TransferStatus getTranferStatus() {
        return tranferStatus;
    }

    public void setTranferStatus(TransferStatus tranferStatus) {
        this.tranferStatus = tranferStatus;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getTransferNote() {
        return transferNote;
    }

    public void setTransferNote(String transferNote) {
        this.transferNote = transferNote;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransferAmout() {
        return transferAmout;
    }

    public void setTransferAmout(String transferAmout) {
        this.transferAmout = transferAmout;
    }
}
