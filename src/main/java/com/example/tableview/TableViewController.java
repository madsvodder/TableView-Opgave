package com.example.tableview;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TableViewController {

    // Table Views
    @FXML
    private TableView<Vare> vareTableView;
    @FXML
    private TableView<Ordre> ordreTableView;
    @FXML
    private TableView<Vare> lagerTableView;

    // Table Columns
    @FXML
    private TableColumn<Vare, Integer> kolonneVarenr;
    @FXML
    private TableColumn<Vare, String> kolonneVarebeskrivelse;
    @FXML
    private TableColumn<Ordre, Integer> kolonneOrdrenr;
    @FXML
    private TableColumn<Ordre, String> kolonneKundeNavn;
    @FXML
    private TableColumn<Ordre, String> kolonneOrdreDato;
    @FXML
    private TableColumn<Vare, Integer> kolonneLagerVarenr;
    @FXML
    private TableColumn<Vare, String> kolonneLagerVareBeskrivelse;
    @FXML
    private TableColumn<Vare, Integer> kolonneLagerVareAmount;
    @FXML
    private TableColumn<Vare, Integer> kolonneVareAmount;

    private ObservableList<Vare> vareTabeldata = FXCollections.observableArrayList();
    private ObservableList<Ordre> ordreTabelData = FXCollections.observableArrayList();
    private ObservableList<Vare> lagerTabelData = FXCollections.observableArrayList();

    public void initialize() {
        vareTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ordreTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lagerTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Kolonnerne forbindes
        kolonneVarenr.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getVarenr()));
        kolonneVarebeskrivelse.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVarebeskrivelse()));
        kolonneLagerVarenr.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getVarenr()));
        kolonneLagerVareBeskrivelse.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVarebeskrivelse()));
        kolonneKundeNavn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKundeNavn()));
        kolonneOrdreDato.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDato()));
        kolonneOrdrenr.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getOrdrenr()));
        kolonneLagerVareAmount.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAmount()));
        kolonneVareAmount.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAmount()));

        // Sæt data i tabellerne
        vareTableView.setItems(vareTabeldata);
        ordreTableView.setItems(ordreTabelData);
        lagerTableView.setItems(lagerTabelData);

        // Sorter vareTableView
        kolonneVarenr.setSortType(TableColumn.SortType.ASCENDING);
        vareTableView.getSortOrder().add(kolonneVarenr);
        vareTableView.sort();

        // Skift varelisten efter hvilken ordre man trykker på
        ordreTableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                updateVarer();
            }
        });

        vareTableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            }
        });

        lagerTableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            }
        });

        loadAllFromJSON();
    }

    // Det her skrald virker ikke
    private void redigerLinje(Vare vare) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Rediger vare");
        dialog.setHeaderText("Rediger vare");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField varenr = new TextField();
        TextField varebeskrivelse = new TextField();
        VBox box = new VBox(varenr, varebeskrivelse);
        dialog.getDialogPane().setContent(box);

        varenr.setText(String.valueOf(vare.getVarenr()));
        varebeskrivelse.setText(vare.getVarebeskrivelse());

        Optional<ButtonType> knap = dialog.showAndWait();

        if (knap.isPresent() && knap.get() == ButtonType.OK) {
            vare.setVarenr(Integer.parseInt(varenr.getText()));
            vare.setVarebeskrivelse(varebeskrivelse.getText());
            vareTableView.refresh();
            vareTableView.sort();
        }
    }


    // Lager Metoder

    // Remove Line
    @FXML
    private void removeLineFromLagerVare(ActionEvent actionEvent) {
        Vare selectedLagerVare = lagerTableView.getSelectionModel().getSelectedItem();
        // Fjern vare fra lagerTabelData
        if (selectedLagerVare != null) {
            lagerTabelData.remove(selectedLagerVare);
            lagerTableView.refresh();
        }
    }

    // Ordre metoder //

    // Helper method to set up and show a dialog with custom content
    private Optional<ButtonType> showDialogWithContent(String title, String headerText, Node content) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(content);
        return dialog.showAndWait();
    }

    // Method to create a new "Ordre"
    @FXML
    private void addNewOrdre() {
        Label ordreid = new Label("Ordre ID: " + (ordreTabelData.size() + 1));
        String currentDate = LocalDate.now().toString();
        Label ordreDato = new Label("OrdreDato: " + currentDate);
        Label navn = new Label("Indtast Navn: ");
        TextField kundeNavn = new TextField();

        VBox box = new VBox(ordreid, ordreDato, navn, kundeNavn);
        Optional<ButtonType> result = showDialogWithContent("Ny ordre", "Ny ordre", box);

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ordreTabelData.add(new Ordre(ordreTabelData.size() + 1, kundeNavn.getText(), currentDate));
            ordreTableView.refresh();
            ordreTableView.sort();
        }
    }

    // Method to add a new "Vare" to a selected "Ordre" & Helper method to handle adding "Vare" to the selected "Ordre"
    @FXML
    private void addNewVareToOrdre() {
        ordreTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Ordre selectedOrdre = ordreTableView.getSelectionModel().getSelectedItem();
        if (selectedOrdre == null) return;

        Label amountLabel = new Label("Amount: ");
        Slider amountSlider = new Slider(0, 0, 0);
        amountSlider.setShowTickLabels(true);
        amountSlider.setShowTickMarks(true);
        amountSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                amountLabel.setText("Amount: " + newValue.intValue())
        );

        Label label = new Label("Vælg en vare:");
        ChoiceBox<Vare> selectedVare = new ChoiceBox<>();
        selectedVare.getItems().addAll(lagerTabelData);

        selectedVare.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                amountSlider.setMax(newValue.getAmount());
            }
        });

        VBox box = new VBox(label, selectedVare, amountLabel, amountSlider);
        Optional<ButtonType> result = showDialogWithContent("Tilføj ny vare", "Tilføj ny vare", box);

        if (result.isPresent() && result.get() == ButtonType.OK) {
            handleAddVareToOrdre(selectedOrdre, selectedVare.getValue(), (int) amountSlider.getValue());
        }
    }
    private void handleAddVareToOrdre(Ordre ordre, Vare selectedVare, int amountToAdd) {
        if (selectedVare != null && selectedVare.getAmount() >= amountToAdd) {
            Vare existingVare = getVareFromOrdre(selectedVare, ordre);

            if (existingVare != null) {
                existingVare.setAmount(existingVare.getAmount() + amountToAdd);
            } else {
                ordre.tilføjVare(new Vare(selectedVare.getVarenr(), selectedVare.getVarebeskrivelse(), amountToAdd));
            }

            selectedVare.setAmount(selectedVare.getAmount() - amountToAdd);

            // Avoid adding duplicates to the vareTableView using the utility method
            for (Vare vare : ordre.getVareListe()) {
                if (!isVareInTable(vare)) {
                    vareTableView.getItems().add(vare);
                }
            }

            lagerTableView.refresh();
            vareTableView.refresh();
        } else {
            showAlert("Ikke nok antal i lager?");
        }
    }
    // Utility method to check if a Vare is already in the vareTableView
    private boolean isVareInTable(Vare vare) {
        return vareTableView.getItems().contains(vare);
    }

    public void showAlert(String popupText) {
        // Opretter en advarsels-popup
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advarsel");
        alert.setHeaderText("Fejl!");
        alert.setContentText(popupText);

        // Viser popup'en
        alert.showAndWait();
    }

    // Remove lines
    @FXML
    private void removeLineFromOrdre(ActionEvent actionEvent) {
        Ordre selectedOrdre = ordreTableView.getSelectionModel().getSelectedItem();
        // Fjern ordre
        if (selectedOrdre != null) {
            ordreTabelData.remove(selectedOrdre);
        }
    }

    // Vare Metoder //

    // Check if a "Vare" is already in out "Lager", so we cant add the same item.
    private boolean isVareAlreadyInLager(Vare vare) {
        for (Vare lagerVare : lagerTabelData) {
            if (vare.getVarebeskrivelse().equals(lagerVare.getVarebeskrivelse())) {
                System.out.println("True");
                return true;
            }
        }
        System.out.println("False");
        return false;
    }

    // Find a specific "Vare" in "Lager"
    private Vare getVareFromLager(Vare vare) {
        for (Vare v : lagerTabelData) {
            if (v.getVarebeskrivelse().equals(vare.getVarebeskrivelse())) {
                System.out.println("Found Same");
                return v;
            }
        }
        return null;
    }

    private Vare getVareFromOrdre(Vare vare, Ordre ordre) {
        if (ordre == null || ordre.getVareListe() == null) {
            return null;
        }

        for (Vare ordreVare : ordre.getVareListe()) {
            if (ordreVare.getVarenr() == vare.getVarenr()) {
                return ordreVare; // Returner varen, hvis den findes i ordren
            }
        }
        return null; // Returner null, hvis varen ikke findes i ordren
    }

    // This method adds a new "Vare" to "Lager"
    @FXML
    private void addNewVareToLager() {
        // Setup Dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Ny vare");
        dialog.setHeaderText("Ny vare");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Setup labels and text fields
        Label vareid = new Label("Vare ID: " + (lagerTabelData.size() + 1));
        Label vareBeskrivelse = new Label("Indtast Beskrivelse: ");
        TextField vareBeskrivelseField = new TextField();

        Label amountLabel = new Label("Amount");
        TextField amountField = new TextField();
        Slider amountSlider = new Slider();
        amountSlider.setMin(1);
        amountSlider.setMax(100);
        amountSlider.setShowTickLabels(true);
        amountSlider.setShowTickMarks(true);

        // Link Slider and TextField values
        // When the slider value changes, update the text field
        amountSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            amountField.setText(String.valueOf(newValue.intValue()));
        });

        // Restrict TextField to numeric input and clamp to slider range
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Allows only digits
            if (!newValue.matches("\\d*")) {
                amountField.setText(newValue.replaceAll("\\D", ""));
            } else {
                try {
                    int value = Integer.parseInt(newValue);
                    if (value > amountSlider.getMax()) {
                        // Clamp to max value
                        value = (int) amountSlider.getMax();
                        // Update field
                        amountField.setText(String.valueOf(value));
                    }
                    if (value >= amountSlider.getMin()) {
                        amountSlider.setValue(value);
                    }
                } catch (NumberFormatException e) {
                    // Ignore invalid input
                }
            }
        });

        // Layout setup
        HBox hbox = new HBox(amountLabel, amountField);
        VBox box = new VBox(vareid, vareBeskrivelse, vareBeskrivelseField, hbox, amountSlider);
        dialog.getDialogPane().setContent(box);

        // Show the dialog and wait for action
        Optional<ButtonType> knap = dialog.showAndWait();

        // If OK is pressed
        if (knap.isPresent() && knap.get() == ButtonType.OK) {
            // Get the amount based on the final slider value
            int amountToAdd = (int) amountSlider.getValue();

            // Create object "Vare" which the user wants to add
            Vare vareToAdd = new Vare(lagerTabelData.size() + 1, vareBeskrivelseField.getText(), amountToAdd);

            // Check if the "Vare" is already in "Lager". If not, add it.
            if (!isVareAlreadyInLager(vareToAdd)) {
                vareToAdd.setAmount(amountToAdd);
                lagerTabelData.add(vareToAdd);
                System.out.println(vareToAdd.getAmount());
            } else {
                // If "Vare" already exists, update amount
                Vare existingVare = getVareFromLager(vareToAdd);
                if (existingVare != null) {
                    // Update amount
                    existingVare.setAmount(existingVare.getAmount() + amountToAdd);
                    lagerTableView.refresh();
                    System.out.println(existingVare.getAmount());
                }
            }
        }
    }


    // Remove Line
    @FXML
    private void removeLineFromVare(ActionEvent actionEvent) {
        Ordre selectedOrdre = ordreTableView.getSelectionModel().getSelectedItem();
        Vare selectedVare = vareTableView.getSelectionModel().getSelectedItem();
        // Fjern vare fra den aktive liste i vareTableView
        if (selectedVare != null) {
            if (selectedOrdre != null && vareTableView.getItems() == selectedOrdre.getVareListe()) {
                // Fjern fra ordrespecifik liste
                selectedOrdre.getVareListe().remove(selectedVare);
            } else {
                // Fjern fra generel vareTabeldata
                vareTabeldata.remove(selectedVare);
            }
            // Opdater visning
            vareTableView.refresh();
            System.out.println("Fjerner: " + selectedVare.getVarebeskrivelse());
        }
    }

    // Update "Varer" table for "Ordrer"
    private void updateVarer() {
        Ordre selectedOrdre = ordreTableView.getSelectionModel().getSelectedItem();
        if (selectedOrdre != null) {
            //vareTableView.setItems(selectedOrdre.getVareListe());
            vareTableView.getItems().clear();
            for (Vare vare : selectedOrdre.getVareListe()) {
                vareTableView.getItems().add(vare);
            }
        }
        else {
            vareTabeldata.clear();
        }
    }

    @FXML
    private void luk() {
        System.exit(0);
    }

    // Metode til at åbne About-vinduet
    @FXML
    public void openAboutWindow() {
        // Opretter et "About"-vindue med en besked
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("About");
        aboutAlert.setHeaderText("Om dette program");

        // Beskrivelse af programmet macrohard Excel
        aboutAlert.setContentText("Macrohard Excel, "
                + "udviklet til at give en simpel regnearksoplevelse.\n\n"
                + "Version 1.0\n"
                + "Udviklet af Gates Bill\n\n"
                + "Macrohard Excel og andre mærker nævnt heri er "
                + "ejendom af deres respektive ejere.");

        // Vist som en modal dialog
        aboutAlert.showAndWait();
    }

    @FXML
    // Metode til at åbne Terms of Use-vinduet
    public void openTermsOfUseWindow() {
        // Opretter et "About"-vindue med en besked
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("About");
        aboutAlert.setHeaderText("Terms of Use");

        // Beskrivelse af programmet macrohard Excel
        aboutAlert.setContentText("Brugsbetingelser for Macrohard Excel\n\n"
                + "1. **Generelle betingelser**\n"
                + "Macrohard Excel er et regnearksprogram udviklet for at give brugeren "
                + "en grundlæggende regnearksoplevelse. Ved at bruge programmet accepterer du disse brugsbetingelser.\n\n"
                + "2. **Brug af programmet**\n"
                + "Du må bruge Macrohard Excel til personlig, ikke-kommerciel brug. "
                + "Programmet må ikke kopieres, distribueres eller sælges uden forudgående tilladelse.\n\n"
                + "3. **Ansvarsfraskrivelse**\n"
                + "Macrohard Excel leveres 'som det er' uden nogen form for garanti. "
                + "Udvikleren påtager sig intet ansvar for tab af data eller skader, der måtte opstå "
                + "ved brug af programmet.\n\n"
                + "4. **Intellektuel ejendom**\n"
                + "Alle rettigheder til Macrohard Excel, herunder koder og design, tilhører udvikleren.\n\n"
                + "5. **Ændringer i vilkårene**\n"
                + "Udvikleren forbeholder sig retten til at ændre disse brugsbetingelser når som helst.\n\n"
                + "6. **Kontakt**\n"
                + "Hvis du har spørgsmål om disse brugsbetingelser, kan du kontakte udvikleren på [din email].\n\n"
                + "Ved at bruge Macrohard Excel accepterer du de ovenstående betingelser.");

        // Vist som en modal dialog
        aboutAlert.showAndWait();
    }


    // Saving and loading
    @FXML
    public void saveData() {
        try {
            // Create output stream to save data
            FileOutputStream file = new FileOutputStream("data.txt");
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Convert ObservableList to ArrayList for serialization
            List<Ordre> serializableOrdreList = new ArrayList<>(ordreTabelData);
            List<Vare> serializableLagerList = new ArrayList<>(lagerTabelData);

            // Serialize the entire lists
            out.writeObject(serializableOrdreList);
            out.writeObject(serializableLagerList);

            System.out.println("Data has been serialized");
            // Close streams
            out.close();
            file.close();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadData() {
        try {
            // Create input stream to load data
            FileInputStream file = new FileInputStream("data.txt");
            ObjectInputStream in = new ObjectInputStream(file);

            // Deserialize the list of Ordre objects
            List<Ordre> deserializedList = (List<Ordre>) in.readObject();
            List<Vare> deserializedLagerList = (List<Vare>) in.readObject();

            // Clear existing data and repopulate ObservableList
            ordreTabelData.clear();
            ordreTabelData.addAll(deserializedList);
            lagerTabelData.clear();
            lagerTabelData.addAll(deserializedLagerList);

            System.out.println("Data has been deserialized");

            // Close streams
            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveAllToJSON() {
        DataSaver dataSaver = new DataSaver();

        // Ordre //

        // Convert your data list to an ArrayList to pass to the save method
        ArrayList<Ordre> ordreList = new ArrayList<>(ordreTabelData);

        // Save all orders to the JSON file
        dataSaver.saveAllOrdre(ordreList);

        // Varer //

        // Convert your data list to an ArrayList to pass to the save method
        ArrayList<Vare> vareList = new ArrayList<>(lagerTabelData);

        // Save
        dataSaver.saveLager(vareList);
    }

    @FXML
    public void loadAllFromJSON() {
        DataSaver dataSaver = new DataSaver();

        // Ordre //

        // Load all Ordre objects from the JSON file
        ArrayList<Ordre> ordreList = dataSaver.loadAllOrdre();

        // Populate table view with the loaded data:
        ordreTabelData.clear(); // Clear existing data
        ordreTabelData.addAll(ordreList); // Add the loaded orders

        // Lager Vare //

        // Load all lager Vare objects from the JSON file
        ArrayList<Vare> vareList = dataSaver.loadLager();

        // Populate the table view with the loaded date
        lagerTabelData.clear();
        lagerTabelData.addAll(vareList);
    }
}
