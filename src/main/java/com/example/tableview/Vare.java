package com.example.tableview;

import java.io.Serial;
import java.io.Serializable;

public class Vare implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    // Transient fields for JavaFX properties
    private int varenr;
    private String varebeskrivelse;
    private int amount;

    public Vare() {}

    public Vare(int varenr, String varebeskrivelse, int amount) {
        this.varenr = varenr;
        this.varebeskrivelse = varebeskrivelse;
        this.amount = amount;
    }

    public int getVarenr() {
        return varenr;
    }

    public String getVarebeskrivelse() {
        return varebeskrivelse;
    }

    public void setVarenr(int varenr) {
        this.varenr = varenr;
    }

    public void setVarebeskrivelse(String varebeskrivelse) {
        this.varebeskrivelse = varebeskrivelse;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return varebeskrivelse;
    }
}
