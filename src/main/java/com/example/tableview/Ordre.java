package com.example.tableview;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ordre implements Serializable {
    private static final long serialVersionUID = 1L;

    // Transient fields for JavaFX properties
    private transient SimpleIntegerProperty ordrenr;
    private transient SimpleStringProperty kundeNavn;
    private transient SimpleStringProperty dato;

    private transient ObservableList<Vare> vareListe = FXCollections.observableArrayList();

    // Serializable backup fields
    private int ordrenrValue;
    private String kundeNavnValue;
    private String datoValue;
    private List<Vare> vareListeValue;

    public Ordre(int ordrenr, String kundeNavn, String dato) {
        this.ordrenr = new SimpleIntegerProperty(ordrenr);
        this.kundeNavn = new SimpleStringProperty(kundeNavn);
        this.dato = new SimpleStringProperty(dato);

        this.ordrenrValue = ordrenr;
        this.kundeNavnValue = kundeNavn;
        this.datoValue = dato;
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

    /**
     * Custom serialization logic
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // Backup values from transient fields
        ordrenrValue = getOrdrenr();
        kundeNavnValue = getKundeNavn();
        datoValue = getDato();

        // Convert ObservableList to a serializable List
        vareListeValue = new ArrayList<>(vareListe);

        // Serialize the object
        out.defaultWriteObject();
    }

    /**
     * Custom deserialization logic
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // Deserialize the object
        in.defaultReadObject();

        // Restore JavaFX properties
        this.ordrenr = new SimpleIntegerProperty(ordrenrValue);
        this.kundeNavn = new SimpleStringProperty(kundeNavnValue);
        this.dato = new SimpleStringProperty(datoValue);

        // Restore ObservableList
        this.vareListe = FXCollections.observableArrayList(vareListeValue);
    }
}
