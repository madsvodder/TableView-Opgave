package com.example.tableview;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Vare {
    private SimpleIntegerProperty varenr;
    private SimpleStringProperty varebeskrivelse;
    private SimpleIntegerProperty amount;

    public Vare(int varenr, String varebeskrivelse, int amount) {
        this.varenr = new SimpleIntegerProperty(varenr);
        this.varebeskrivelse = new SimpleStringProperty(varebeskrivelse);
        this.amount = new SimpleIntegerProperty(amount);
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
}
