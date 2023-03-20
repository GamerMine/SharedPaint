package fr.sae402.sharedpaint;

import fr.sae402.sharedpaint.metier.Client;
import fr.sae402.sharedpaint.metier.OutilForme;
import fr.sae402.sharedpaint.metier.Rectangle;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MainController {
    private ToggleGroup toolToggleGroup;
    private final int ICON_SIZE = 32;
    private Client currentClient;

    @FXML
    private FlowPane shapeTools;

    @FXML
    public void initialize() {
        this.toolToggleGroup = new ToggleGroup();
        this.currentClient = new Client();
        for (OutilForme outil : OutilForme.getOutils()) {
            ToggleButton toggleButton = new ToggleButton("");
            ImageView imageView = new ImageView(new Image(outil.getIcon()));
            imageView.setFitHeight(ICON_SIZE - 5);
            imageView.setFitWidth(ICON_SIZE - 5);
            toggleButton.setGraphic(imageView);
            toggleButton.setToggleGroup(this.toolToggleGroup);
            toggleButton.setMaxWidth(ICON_SIZE);
            toggleButton.setMaxHeight(ICON_SIZE);
            toggleButton.setMinWidth(ICON_SIZE);
            toggleButton.setMinHeight(ICON_SIZE);
            toggleButton.setPadding(new Insets(2, 2, 2, 2));
            shapeTools.getChildren().add(toggleButton);
        }

        this.currentClient.write(new Rectangle(0, 0, "#FFFFFF", true, 20, 20));

        this.currentClient.close();
    }
}