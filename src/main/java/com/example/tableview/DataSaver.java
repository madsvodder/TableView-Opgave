package com.example.tableview;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class DataSaver {

    private static final Logger logger = Logger.getLogger(DataSaver.class.getName());

    // Create ObjectMapper for serialization and deserialization
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Save all Ordre objects into a single JSON file
    public void saveAllOrdre(ArrayList<Ordre> ordreList) {
        try {
            // Specify the file where all orders will be saved
            File file = new File("all_orders.json");

            // Serialize the list of Ordre objects to JSON and write it to the file
            objectMapper.writeValue(file, ordreList);
            logger.info("All Ordre objects saved to all_orders.json");

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Load all Ordre objects from the single JSON file
    public ArrayList<Ordre> loadAllOrdre() {
        ArrayList<Ordre> ordresArray = new ArrayList<>();

        try {
            // Specify the file where all orders are saved
            File file = new File("all_orders.json");

            // Check if the file exists and is not empty
            if (file.exists() && file.length() > 0) {
                // Deserialize the JSON file into a list of Ordre objects
                ordresArray = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Ordre.class));
                System.out.println("All Ordre objects loaded successfully.");
            } else {
                System.out.println("No orders found to load.");
            }

        } catch (IOException e) {
            System.err.println("Error reading or deserializing the JSON file: " + e.getMessage());
        }

        return ordresArray;
    }

    // Save all Vare in lager into a single json file
    public void saveLager(ArrayList<Vare> vareListe) {
        try {
            // Specify the file where all varer will be saved
            File file = new File("lager.json");

            // Serialize the list of Ordre objects to JSON and write it to the file
            objectMapper.writeValue(file, vareListe);
            logger.info("All varer objects saved to lager.json");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Vare> loadLager() {
        ArrayList<Vare> vareArray = new ArrayList<>();

        try {
            // Specify the file where all orders are saved
            File file = new File("lager.json");

            // Check if the file exists and is not empty
            if (file.exists() && file.length() > 0) {
                // Deserialize the JSON file into a list of Ordre objects
                vareArray = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Vare.class));
                logger.info("All Ordre objects loaded successfully.");
            } else {
                logger.info("No orders found to load.");
            }

        } catch (IOException e) {
            System.err.println("Error reading or deserializing the JSON file: " + e.getMessage());
        }

        return vareArray;
    }
}
