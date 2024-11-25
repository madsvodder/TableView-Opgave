package com.example.tableview;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class DataSaver {

    // Create ObjectMapper for serialization and deserialization
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Serialize Ordre to JSON and save it to a file
    public void saveOrdre(Ordre ordreToSave) {
        try {
            // Specify the file where the JSON will be saved
            File file = new File("ordre.json");

            // Serialize the Ordre object to JSON and write it to the file
            objectMapper.writeValue(file, ordreToSave);
            System.out.println("Ordre saved to JSON file successfully.");

        } catch (JsonProcessingException e) {
            System.err.println("Error serializing Ordre object to JSON: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Deserialize JSON from a file to an Ordre object
    public Ordre loadOrdre() {
        try {
            // Specify the file from which the JSON will be read
            File file = new File("ordre.json");

            // Deserialize the JSON into an Ordre object
            return objectMapper.readValue(file, Ordre.class);

        } catch (IOException e) {
            System.err.println("Error reading or deserializing the JSON file: " + e.getMessage());
            return null; // Return null in case of an error
        }
    }
}
