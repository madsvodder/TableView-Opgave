package com.example.tableview;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serial;
import java.io.Serializable;

public class Vare implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    // Transient fields for JavaFX properties
    private transient SimpleIntegerProperty varenr;
    private transient SimpleStringProperty varebeskrivelse;
    private transient SimpleIntegerProperty amount;

    // Serializable backup fields
    private int varenrValue;
    private String varebeskrivelseValue;
    private int amountValue;

    public Vare(int varenr, String varebeskrivelse, int amount) {
        this.varenr = new SimpleIntegerProperty(varenr);
        this.varebeskrivelse = new SimpleStringProperty(varebeskrivelse);
        this.amount = new SimpleIntegerProperty(amount);

        this.varenrValue = varenr;
        this.varebeskrivelseValue = varebeskrivelse;
        this.amountValue = amount;
    }

    public int getVarenr() {
        return varenr.get();
    }

    public String getVarebeskrivelse() {
        return varebeskrivelse.get();
    }

    public void setVarenr(int varenr) {
        this.varenr.set(varenr);
    }

    public void setVarebeskrivelse(String varebeskrivelse) {
        this.varebeskrivelse.set(varebeskrivelse);
    }

    public int getAmount() {
        return amount.get();
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    @Override
    public String toString() {
        return varebeskrivelse.get();
    }

    /**
     * Custom serialization logic
     */
    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        // Backup values from transient fields
        varenrValue = getVarenr();
        varebeskrivelseValue = getVarebeskrivelse();
        amountValue = getAmount();

        // Serialize the object
        out.defaultWriteObject();
    }

    /**
     * Custom deserialization logic
     */
    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        // Deserialize the object
        in.defaultReadObject();

        // Restore JavaFX properties
        this.varenr = new SimpleIntegerProperty(varenrValue);
        this.varebeskrivelse = new SimpleStringProperty(varebeskrivelseValue);
        this.amount = new SimpleIntegerProperty(amountValue);
    }
}
