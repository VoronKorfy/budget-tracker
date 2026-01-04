package model;

public class Transaction {
    private float amount;
    private String description;

    private Transaction next;

    public Transaction(float amount, String description) {
        this.amount = amount;
        this.description = description;
    }
    public float getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }
    public Transaction getNext() {
        return next;
    }
    public void setNext(Transaction next) {
        this.next = next;
    }
}

