package fr.sae402.sharedpaint;

import fr.sae402.sharedpaint.metier.OutilForme;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainController {
    @FXML
    private AnchorPane shapeTools;

    @FXML
    public void initialize() {
        for (OutilForme outil : OutilForme.getOutils()) {
            RadioButton radioButton = new RadioButton("");
            radioButton.setGraphic(new ImageView(new Image(outil.getIcon().getFile())));
            shapeTools.getChildren().add(radioButton);
        }
    }
}