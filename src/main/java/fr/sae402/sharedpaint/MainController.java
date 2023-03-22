package fr.sae402.sharedpaint;

import fr.sae402.sharedpaint.metier.Cercle;
import fr.sae402.sharedpaint.metier.Metier;
import fr.sae402.sharedpaint.metier.OutilForme;
import fr.sae402.sharedpaint.metier.Rectangle;
import fr.sae402.sharedpaint.networking.Client;
import fr.sae402.sharedpaint.networking.Serveur;
import fr.sae402.sharedpaint.metier.*;
import fr.sae402.sharedpaint.ui.*;
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
import java.util.Optional;

public class MainController {
    private ToggleGroup toolToggleGroup;
    private final int ICON_SIZE = 32;
    private Client currentClient;

    private static final ArrayList<String> couleur = new ArrayList<>();

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
            ImageToggleButton itb = new ImageToggleButton(outil.getIcon(), this.toolToggleGroup);
            shapeTools.getChildren().add(itb);
        }
        couleur.add("black");
        couleur.add("white");
        couleur.add("red");
        couleur.add("blue");
        couleur.add("yellow");
        couleur.add("green");



        ImageToggleButton fillBtn = new ImageToggleButton(SharedPaint.class.getResourceAsStream("icons/fill.png"), null);
        shapeTools.getChildren().add(fillBtn);

        ImageToggleButton undoBtn = new ImageToggleButton(SharedPaint.class.getResourceAsStream("icons/undo.png"), null);
        shapeTools.getChildren().add(undoBtn);

        for(int i=0; i < couleur.size(); i++){
            ImageToggleButton colBtn = new ImageToggleButton(SharedPaint.class.getResourceAsStream("icons/" + couleur.get(i) + ".png"), null);
            shapeTools.getChildren().add(colBtn);
        }

        ColorPicker colPick = new ColorPicker();
        colPick.setPrefHeight(32);
        colPick.setPrefWidth(100);
        colPick.setPadding(new Insets(2, 2, 2, 2));

        shapeTools.getChildren().add(colPick);
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