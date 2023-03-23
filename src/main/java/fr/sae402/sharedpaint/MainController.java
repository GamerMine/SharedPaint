package fr.sae402.sharedpaint;

import fr.sae402.sharedpaint.metier.Cercle;
import fr.sae402.sharedpaint.metier.Metier;
import fr.sae402.sharedpaint.metier.OutilForme;
import fr.sae402.sharedpaint.metier.Rectangle;
import fr.sae402.sharedpaint.networking.Client;
import fr.sae402.sharedpaint.networking.Serveur;
import fr.sae402.sharedpaint.metier.*;
import fr.sae402.sharedpaint.ui.*;
import javafx.event.ActionEvent;
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
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Optional;

public class MainController {
    private ToggleGroup toolToggleGroup;
    private final int ICON_SIZE = 32;
    private Client currentClient;

    private static final String[] COLORS = {"black", "white", "red", "blue", "yellow", "green"};

    private Metier metier;
    private Serveur serveur;
    private Client client;


    @FXML
    private FlowPane shapeTools;
    @FXML
    private AnchorPane paneDessin;
    @FXML
    private MenuItem menuHerbergement;

    @FXML
    public void initialize() {
        this.metier             = new Metier(this);
        this.toolToggleGroup    = new ToggleGroup();
        this.currentClient      = new Client("LOLILOL");

        for (OutilForme outil : OutilForme.getOutils()) {
            outil.setOnAction(this::toolButtonClick);
            shapeTools.getChildren().add(outil);
        }
        OutilForme.getOutils().get(0).setSelected(true);
        this.metier.changerForme(OutilForme.getOutils().get(0).getForme());

        ImageToggleButton fillBtn = new ImageToggleButton(SharedPaint.class.getResourceAsStream("icons/fill.png"), null);
        ImageToggleButton undoBtn = new ImageToggleButton(SharedPaint.class.getResourceAsStream("icons/undo.png"), null);

        shapeTools.getChildren().add(fillBtn);
        shapeTools.getChildren().add(undoBtn);

        ToggleGroup groupeCouleur = new ToggleGroup();
        for(int i=0; i < COLORS.length; i++){
            ImageToggleButton colBtn = new ImageToggleButton(SharedPaint.class.getResourceAsStream("icons/" + COLORS[i] + ".png"), groupeCouleur);
            colBtn.setId(COLORS[i]);
            shapeTools.getChildren().add(colBtn);
        }
        groupeCouleur.getToggles().get(0).setSelected(true);
        this.metier.changerCouleur(Color.valueOf(((ImageToggleButton)groupeCouleur.getToggles().get(0)).getId()));


        ColorPicker colPick = new ColorPicker();
        colPick.setPrefHeight(32);
        colPick.setPrefWidth(100);
        colPick.setPadding(new Insets(2, 2, 2, 2));

        shapeTools.getChildren().add(colPick);
    }

    private void toolButtonClick(ActionEvent e) {
        OutilForme toggleButton = (OutilForme) e.getSource();
        this.metier.changerForme(toggleButton.getForme());
    }

    public void dessiner(MouseEvent e) throws InstantiationException, IllegalAccessException {
        System.out.println("DESSINER"); //ACCES
        int posX = 0;
        int posY = 0;
        if(e.getEventType() == MouseEvent.MOUSE_PRESSED) {
            System.out.println("BOUTON PRESS"); //PAS ACCES
            posX = (int) e.getX();
            posY = (int) e.getY();
        }
        if(e.getEventType() == MouseEvent.MOUSE_RELEASED) {
            Node node = null;
            Forme forme = null;

            System.out.println("BOUTON RELACHE"); // PAS ACCES
            if(this.metier.getFormeActuel() == Rectangle.class) {
                RectangleUI rectangleUI = new RectangleUI(posX, posY, this.metier.getCouleurActuel().toString(),  this.metier.isRempli(), (int) (e.getX()-posX), (int) (e.getY()-posY));
                forme = rectangleUI.getElementRectangle();
                node = rectangleUI;
            }
            if(this.metier.getFormeActuel() == Cercle.class) {
                //Calcul du Rayon
                double distanceX = e.getX()-posX;
                double distanceY = e.getY()-posY;
                double rayon = Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2));
                CercleUI cercleUI = new CercleUI(posX, posY, this.metier.getCouleurActuel().toString(), this.metier.isRempli(), (int) rayon);

                forme = cercleUI.getElementCercle();
                node = cercleUI;
            }
            if(this.metier.getFormeActuel() == Ligne.class) {
                LigneUI ligneUI = new LigneUI(posX, posY, this.metier.getCouleurActuel().toString(), this.metier.isRempli(), (int) e.getX(), (int) e.getY());

                forme = ligneUI.getElementLigne();
                node = ligneUI;
            }
            if(this.metier.getFormeActuel() == Texte.class) {
                TexteUI texteUI = new TexteUI(posX, posY, this.metier.getCouleurActuel().toString(), "TEXTE A EDIT");

                forme = texteUI.getElementTexte();
                node = texteUI;
            }
            //this.paneDessin.getChildren().add(node);
            this.client.envoyerForme(forme);
        }
    }

    public void hebergement() throws InterruptedException {
        if (serveur == null) {
            this.menuHerbergement.setText("Arrêter le partage");
            this.serveur = new Serveur();
            Thread serveurThread = new Thread(this.serveur);
            TextInputDialog inputDialog = new TextInputDialog("Pseudo : ");
            inputDialog.setTitle("Pseudo");
            inputDialog.setHeaderText("Veuillez entrer votre pseudo.");
            Optional<String> result = inputDialog.showAndWait();
            this.client = new Client(result.get());
            Thread clientThread = new Thread(this.client); // TODO: On doit vérifier que le pseudo entré est valide (donc pas vide)
            clientThread.start();
            Thread.sleep(20);
            serveurThread.start();
        } else {
            this.menuHerbergement.setText("Partager");
            this.serveur.stop();
            this.serveur = null;
            this.client.stop();
            this.client = null;
        }
    }

    public void chargerJSON() {
        this.metier.chargerJSON();
    }

    public void ecrireJSON() {
        this.metier.ecrireJSON();
    }
}