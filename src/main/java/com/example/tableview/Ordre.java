package com.example.tableview;

import java.io.*;
import java.util.ArrayList;

public class Ordre implements Serializable {
    private static final long serialVersionUID = 1L;

    // Transient fields for JavaFX properties
    private int ordrenr;
    private String kundeNavn;
    private String dato;

    private ArrayList<Vare> vareListe = new ArrayList<>();

    // Default constructor (required for Jackson deserialization)
    public Ordre() {}

    public Ordre(int ordrenr, String kundeNavn, String dato) {
        this.ordrenr = ordrenr;
        this.kundeNavn = kundeNavn;
        this.dato = dato;
    }

    public void tilføjVare(Vare vare) {
        vareListe.add(vare);
    }

    public int getOrdrenr() {
        return ordrenr;
    }

    public String getKundeNavn() {
        return kundeNavn;
    }

    public String getDato() {
        return dato;
    }

    public ArrayList<Vare> getVareListe() {
        return vareListe;
    }

    public void setVareListe(ArrayList<Vare> vareListe) {
        this.vareListe = vareListe;
    }

    public void setOrdrenr(int ordrenr) {
        this.ordrenr = ordrenr;
    }

    public void setKundeNavn(String kundeNavn) {
        this.kundeNavn = kundeNavn;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }
}
