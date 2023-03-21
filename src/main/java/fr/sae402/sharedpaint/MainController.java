package fr.sae402.sharedpaint;

import fr.sae402.sharedpaint.metier.*;
import fr.sae402.sharedpaint.ui.CercleUI;
import fr.sae402.sharedpaint.ui.LigneUI;
import fr.sae402.sharedpaint.ui.RectangleUI;
import fr.sae402.sharedpaint.ui.TexteUI;
import fr.sae402.sharedpaint.metier.Cercle;
import fr.sae402.sharedpaint.metier.Metier;
import fr.sae402.sharedpaint.metier.OutilForme;
import fr.sae402.sharedpaint.metier.Rectangle;
import fr.sae402.sharedpaint.networking.Client;
import fr.sae402.sharedpaint.networking.Serveur;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class MainController {
    private ToggleGroup toolToggleGroup;
    private final int ICON_SIZE = 32;
    private Client currentClient;

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