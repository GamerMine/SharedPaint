package fr.sae402.sharedpaint;

import fr.sae402.sharedpaint.metier.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MainController {
    private ToggleGroup toolToggleGroup;
    private final int ICON_SIZE = 32;
    private Client currentClient;

    private static final ArrayList<String> couleur = new ArrayList<>();

    private Metier metier;

    @FXML
    private FlowPane shapeTools;

    @FXML
    public void initialize() {
        this.metier             = new Metier(this);
        this.toolToggleGroup    = new ToggleGroup();
        this.currentClient      = new Client();
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
        couleur.add("black");
        couleur.add("white");
        couleur.add("red");
        couleur.add("blue");
        couleur.add("yellow");
        couleur.add("green");



        ToggleButton fillBtn = new ToggleButton("");
        ImageView imgFill = new ImageView(new Image(SharedPaint.class.getResourceAsStream("icons/fill.png")));
        imgFill.setFitHeight(ICON_SIZE - 5);
        imgFill.setFitWidth(ICON_SIZE - 5);
        fillBtn.setGraphic(imgFill);
        fillBtn.setMaxWidth(ICON_SIZE);
        fillBtn.setMaxHeight(ICON_SIZE);
        fillBtn.setMinWidth(ICON_SIZE);
        fillBtn.setMinHeight(ICON_SIZE);
        fillBtn.setPadding(new Insets(2, 2, 2, 2));
        shapeTools.getChildren().add(fillBtn);

        ToggleButton undoBtn = new ToggleButton("");
        ImageView imgUndo = new ImageView(new Image(SharedPaint.class.getResourceAsStream("icons/undo.png")));
        imgUndo.setFitHeight(ICON_SIZE - 5);
        imgUndo.setFitWidth(ICON_SIZE - 5);
        undoBtn.setGraphic(imgUndo);
        undoBtn.setMaxWidth(ICON_SIZE);
        undoBtn.setMaxHeight(ICON_SIZE);
        undoBtn.setMinWidth(ICON_SIZE);
        undoBtn.setMinHeight(ICON_SIZE);
        undoBtn.setPadding(new Insets(2, 2, 2, 2));
        shapeTools.getChildren().add(undoBtn);

        for(int i=0; i < couleur.size(); i++){
            ToggleButton colBtn = new ToggleButton("");
            ImageView imageView = new ImageView(new Image(SharedPaint.class.getResourceAsStream("icons/" + couleur.get(i) + ".png")));
            imageView.setFitHeight(ICON_SIZE - 5);
            imageView.setFitWidth(ICON_SIZE - 5);
            colBtn.setGraphic(imageView);
            colBtn.setMaxWidth(ICON_SIZE);
            colBtn.setMaxHeight(ICON_SIZE);
            colBtn.setMinWidth(ICON_SIZE);
            colBtn.setMinHeight(ICON_SIZE);
            colBtn.setPadding(new Insets(2, 2, 2, 2));
            shapeTools.getChildren().add(colBtn);
        }

        ColorPicker colPick = new ColorPicker();
        colPick.setPrefHeight(32);
        colPick.setPrefWidth(100);
        colPick.setPadding(new Insets(2, 2, 2, 2));

        shapeTools.getChildren().add(colPick);

        this.currentClient.write(new Rectangle(0, 0, "#FFFFFF", true, 20, 20));
        this.currentClient.write(new Cercle(1, 1, "#FFFFFF", false, 5));
        this.currentClient.write(new Rectangle(20, 100, "#FFCCFF", false, 30, 30));

        this.currentClient.close();
    }

    public void dessiner() {
        this.metier.dessiner();
    }

    public void changerCouleur() {
        this.metier.changerCouleur();
    }

    public void changerForme() {
        this.metier.changerForme();
    }
    public void chargerJSON() {
        this.metier.chargerJSON();
    }

    public void ecrireJSON() {
        this.metier.ecrireJSON();
    }
}