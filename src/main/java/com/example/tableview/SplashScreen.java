package com.example.tableview;

import atlantafx.base.theme.NordDark;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SplashScreen extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Create the splash screen elements
        StackPane splashPane = new StackPane();

        // Load the logo image
        Image logoImage = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        ImageView logoImageView = new ImageView(logoImage);

        // Optionally, resize the logo image to fit your splash screen
        logoImageView.setFitWidth(200); // Adjust width as needed
        logoImageView.setFitHeight(150); // Adjust height as needed

        splashPane.getChildren().add(logoImageView);

        // Create a fade transition for the splash screen
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), splashPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        // Set up the splash screen scene
        Scene splashScene = new Scene(splashPane, 300, 200);
        stage.setTitle("My Application - Splash Screen");
        stage.setScene(splashScene);
        stage.show();

        // Start the fade-in effect
        fadeIn.play();

        // Simulate loading by waiting a few seconds before showing the main window
        new Thread(() -> {
            try {
                Thread.sleep(3000); // Simulate loading time (3 seconds)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // After the splash screen, show the main application window
            javafx.application.Platform.runLater(() -> {
                try {
                    showMainApp(stage);  // Show the main app window after the splash screen
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    private void showMainApp(Stage splashStage) throws IOException {
        // Load the FXML file for the main app window (hello-view.fxml)
        FXMLLoader fxmlLoader = new FXMLLoader(TableViewApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Apply the desired theme (NordDark or others)
        Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());

        // Set up the main application window
        splashStage.setTitle("Ordre");
        splashStage.setScene(scene);

        // Center the main application window on the screen
        centerStageOnScreen(splashStage);

        splashStage.show();
    }

    private void centerStageOnScreen(Stage stage) {
        // Get the screen's visual bounds (screen size)
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();

        // Get the size of the stage (main window)
        double stageWidth = stage.getWidth();
        double stageHeight = stage.getHeight();

        // Calculate the center position for the window
        double centerX = (screenWidth - stageWidth) / 2;
        double centerY = (screenHeight - stageHeight) / 2;

        // Set the position of the stage to the center of the screen
        stage.setX(centerX);
        stage.setY(centerY);
    }

    public static void main(String[] args) {
        launch();
    }
}
