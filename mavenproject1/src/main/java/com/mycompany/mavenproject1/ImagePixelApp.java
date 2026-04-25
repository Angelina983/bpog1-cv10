package com.mycompany.mavenproject1;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImagePixelApp {

    private BorderPane root;
    private Stage stage;

    private Image image;
    private ImageView imageView;
    private PixelReader pixelReader;

    public ImagePixelApp(Stage stage) {
        this.stage = stage;

        root = new BorderPane();

        Button openButton = new Button("Vybrat obrázek");

        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openImage();
            }
        });

        HBox topPanel = new HBox();
        topPanel.getChildren().add(openButton);
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.CENTER_LEFT);

        imageView = new ImageView();

        imageView.setPreserveRatio(true);
        imageView.setFitWidth(760);
        imageView.setFitHeight(520);

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    showPixelInfo(x, y);
                }
            }
        });

        root.setTop(topPanel);
        root.setCenter(imageView);
    }

    public BorderPane getRoot() {
        return root;
    }

    private void openImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Vyberte obrázek");

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter(
                        "Image files",
                        "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"
                );

        fileChooser.getExtensionFilters().add(extFilter);

        try {
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                image = new Image(file.toURI().toString());

                if (image.isError() || image == null) {
                    throw new Exception("Invalid picture file");
                }

                imageView.setImage(image);

                System.out.println("Image Width: " + image.getWidth());
                System.out.println("Image Height: " + image.getHeight());

                pixelReader = image.getPixelReader();

                System.out.println("Pixel Format: " + pixelReader.getPixelFormat());
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid picture format or invalid file.");
            alert.showAndWait();
        }
    }

    private void showPixelInfo(int x, int y) {
        if (image == null) {
            return;
        }

        if (pixelReader == null) {
            return;
        }

        if (x < 0 || y < 0 || x >= image.getWidth() || y >= image.getHeight()) {
            return;
        }

        Color color = pixelReader.getColor(x, y);

        String message = "";

        message = String.format("Pixel color at coordinates [%d,%d] %s%n",
                x, y, color);

        message = message + String.format("R = %.3f%n", color.getRed());
        message = message + String.format("G = %.3f%n", color.getGreen());
        message = message + String.format("B = %.3f%n", color.getBlue());
        message = message + String.format("Opacity = %.3f%n", color.getOpacity());
        message = message + String.format("Saturation = %.3f%n", color.getSaturation());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Pixel Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}