package com.example.tableview;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Ordre {
    private SimpleIntegerProperty ordrenr;
    private SimpleStringProperty kundeNavn;
    private SimpleStringProperty dato;


    private ObservableList<Vare> vareListe = FXCollections.observableArrayList();

    public Ordre(int ordrenr, String kundeNavn, String dato) {
        this.ordrenr = new SimpleIntegerProperty(ordrenr);
        this.kundeNavn = new SimpleStringProperty(kundeNavn);
        this.dato = new SimpleStringProperty(dato);
    }

    public void tilføjVare(Vare vare) {
        vareListe.add(vare);
    }

    public int getOrdrenr() {
        return ordrenr.get();
    }

    public String getKundeNavn() {
        return kundeNavn.get();
    }

    public String getDato() {
        return dato.get();
    }

    public ObservableList<Vare> getVareListe() {
        return vareListe;
    }

    public void setVareListe(ObservableList<Vare> vareListe) {
        this.vareListe = vareListe;
    }

    public void setOrdrenr(int ordrenr) {
        this.ordrenr.set(ordrenr);
    }

    public void setKundeNavn(String kundeNavn) {
        this.kundeNavn.set(kundeNavn);
    }

    public void setDato(String dato) {
        this.dato.set(dato);
    }
}
