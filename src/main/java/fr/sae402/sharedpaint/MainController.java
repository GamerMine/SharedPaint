package fr.sae402.sharedpaint;

import fr.sae402.sharedpaint.metier.Cercle;
import fr.sae402.sharedpaint.metier.Metier;
import fr.sae402.sharedpaint.metier.OutilForme;
import fr.sae402.sharedpaint.metier.Rectangle;
import fr.sae402.sharedpaint.networking.Client;
import fr.sae402.sharedpaint.networking.Serveur;
import fr.sae402.sharedpaint.metier.*;
import fr.sae402.sharedpaint.ui.CercleUI;
import fr.sae402.sharedpaint.ui.LigneUI;
import fr.sae402.sharedpaint.ui.RectangleUI;
import fr.sae402.sharedpaint.ui.TexteUI;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
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
    private AnchorPane paneDessin;

    @FXML
    public void initialize() {
        this.metier             = new Metier(this);
        this.toolToggleGroup    = new ToggleGroup();
        this.currentClient      = new Client("LOLILOL");
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

        new Thread(new Serveur()).start();
        new Thread(new Client("LOLILOL")).start();
    }

    public void dessiner(MouseEvent e) {
        System.out.println("DESSINER"); //ACCES
        int posX = 0;
        int posY = 0;
        if(e.getSource() == MouseEvent.MOUSE_PRESSED) {
            System.out.println("BOUTON PRESS"); //PAS ACCES
            posX = (int) e.getX();
            posY = (int) e.getY();
        }
        if(e.getSource() == MouseEvent.MOUSE_RELEASED) {
            Node node = null;
            System.out.println("BOUTON RELACHE"); // PAS ACCES
            if(this.metier.getFormeActuel() instanceof Rectangle) {
                node = new RectangleUI(posX, posY, this.metier.getCouleurActuel().toString(),  this.metier.isRempli(), (int) (e.getX()-posX), (int) (e.getY()-posY));
            }
            if(this.metier.getFormeActuel() instanceof Cercle) {
                //Calcul du Rayon
                double distanceX = e.getX()-posX;
                double distanceY = e.getY()-posY;
                double rayon = Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2));

                node = new CercleUI(posX, posY, this.metier.getCouleurActuel().toString(), this.metier.isRempli(), (int) rayon);
            }
            if(this.metier.getFormeActuel() instanceof Ligne) {
                node = new LigneUI(posX, posY, this.metier.getCouleurActuel().toString(), this.metier.isRempli(), (int) e.getX(), (int) e.getY());
            }
            if(this.metier.getFormeActuel() instanceof Texte) {
                node = new TexteUI(posX, posY, this.metier.getCouleurActuel().toString(), "TEXTE A EDIT");
            }
            this.paneDessin.getChildren().add(node);
        }
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