package fr.sae402.sharedpaint;

import fr.sae402.sharedpaint.metier.Cercle;
import fr.sae402.sharedpaint.metier.Metier;
import fr.sae402.sharedpaint.metier.OutilForme;
import fr.sae402.sharedpaint.metier.Rectangle;
import fr.sae402.sharedpaint.networking.Client;
import fr.sae402.sharedpaint.networking.Serveur;
import fr.sae402.sharedpaint.metier.*;
import fr.sae402.sharedpaint.ui.*;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Optional;

public class MainController {
    private static final String[] COLORS = {"black", "white", "red", "blue", "yellow", "green"};

    private Metier metier;
    private Serveur serveur;
    private Client client;
    private Node elementFantome;

    private int posX, posY;
    private ArrayList<Forme> uiElements;

    @FXML
    private FlowPane shapeTools;
    @FXML
    private AnchorPane paneDessin;
    @FXML
    private MenuItem menuHerbergement;
    @FXML
    private MenuItem menuRejoindre;

    @FXML
    public void initialize() {
        this.metier     = new Metier(this);
        this.uiElements = new ArrayList<>();
        this.elementFantome = null;

        // Création des outils
        for (OutilForme outil : OutilForme.getOutils()) {
            outil.setOnAction(this::toolButtonClick);
            shapeTools.getChildren().add(outil);
        }
        OutilForme.getOutils().get(0).setSelected(true);
        this.metier.changerForme(OutilForme.getOutils().get(0).getForme());

        ImageToggleButton fillBtn = new ImageToggleButton(SharedPaint.class.getResourceAsStream("icons/fill.png"), null);
        ImageToggleButton undoBtn = new ImageToggleButton(SharedPaint.class.getResourceAsStream("icons/undo.png"), null);
        undoBtn.setOnAction(this::undo);

        fillBtn.setId("Fill");
        fillBtn.setOnAction(this::btnFill);

        shapeTools.getChildren().add(fillBtn);
        shapeTools.getChildren().add(undoBtn);

        // Création des couleur par défaut
        ToggleGroup groupeCouleur = new ToggleGroup();
        for(int i=0; i < COLORS.length; i++){
            ImageToggleButton colBtn = new ImageToggleButton(SharedPaint.class.getResourceAsStream("icons/" + COLORS[i] + ".png"), groupeCouleur);
            colBtn.setId(COLORS[i]);
            colBtn.setOnAction(this::colBtnClick);
            shapeTools.getChildren().add(colBtn);
        }
        groupeCouleur.getToggles().get(0).setSelected(true);
        this.metier.changerCouleur(Color.valueOf(((ImageToggleButton)groupeCouleur.getToggles().get(0)).getId()));

        // Création du Color Picker
        ColorPicker colPick = new ColorPicker();
        colPick.setPrefHeight(32);
        colPick.setPrefWidth(100);
        colPick.setPadding(new Insets(2, 2, 2, 2));
        colPick.setOnAction(this::colorPicker);

        shapeTools.getChildren().add(colPick);

    }

    private void colorPicker(ActionEvent e) {
        ColorPicker colPick = (ColorPicker) e.getSource();
        Color c = colPick.getValue();
        this.metier.changerCouleur(c);
    }

    private void btnFill(ActionEvent e) {
        ImageToggleButton btnFill = (ImageToggleButton) e.getSource();
        if(btnFill.isSelected()) {
            ImageView img = new ImageView(new Image(SharedPaint.class.getResourceAsStream("icons/circle.png")));
            btnFill.setGraphic(img);
        }else{
            ImageView img = new ImageView(new Image(SharedPaint.class.getResourceAsStream("icons/fill.png")));
            btnFill.setGraphic(img);

        }
    }

    private void undo(ActionEvent e) {

        Forme suppForme = this.client.undoClientForme();
        if (suppForme == null) return;
        System.out.println(this.uiElements.size());
        this.uiElements.removeIf(forme -> forme.equals(suppForme));
        System.out.println(this.uiElements.size());
        this.uiMaj();
    }

    private void colBtnClick(ActionEvent e) {
        ImageToggleButton colBtn = (ImageToggleButton) e.getSource();
        System.out.println(Color.valueOf(colBtn.getId()));
        this.metier.changerCouleur(Color.valueOf(colBtn.getId()));
    }

    private void toolButtonClick(ActionEvent e) {
        OutilForme toggleButton = (OutilForme) e.getSource();
        this.metier.changerForme(toggleButton.getForme());
    }

    public void dessiner(MouseEvent e) {
        if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            if (this.metier.getFormeActuel() == Rectangle.class) {
                int minX = Math.min(posX, (int)e.getX());
                int minY = Math.min(posY, (int)e.getY());
                int finX = Math.abs(posX - (int)e.getX());
                int finY = Math.abs(posY - (int)e.getY());

                if (this.elementFantome == null) {
                    this.elementFantome = new RectangleUI(new Rectangle(minX, minY, this.metier.getCouleurActuel().toString(),this.metier.isRempli(), finX, finY));
                } else {
                    ((RectangleUI)this.elementFantome).setX(minX);
                    ((RectangleUI)this.elementFantome).setY(minY);
                    ((RectangleUI)this.elementFantome).setWidth(finX);
                    ((RectangleUI)this.elementFantome).setHeight(finY);
                }
            }
            if (this.metier.getFormeActuel() == Cercle.class) {
                double distanceX = e.getX()-posX;
                double distanceY = e.getY()-posY;
                double rayon = Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2));

                if (this.elementFantome == null) {
                    this.elementFantome = new CercleUI(new Cercle(posX, posY, this.metier.getCouleurActuel().toString(), this.metier.isRempli(), (int) rayon));
                } else {
                    ((CercleUI)this.elementFantome).setRadius(rayon);
                }
            }
            if(this.metier.getFormeActuel() == Ellipse.class) {
                double rayonX = Math.abs(e.getX() - posX) / 2.0;
                double rayonY = Math.abs(e.getY() - posY) / 2.0;

                if(this.elementFantome == null ) {
                    this.elementFantome = new EllipseUI(new Ellipse(posX,posY,this.metier.getCouleurActuel().toString(), this.metier.isRempli(), (int) rayonX, (int) rayonY));
                } else {
                    ((EllipseUI)this.elementFantome).setRadiusY(rayonY);
                    ((EllipseUI)this.elementFantome).setRadiusX(rayonX);
                }

            }
            if (this.metier.getFormeActuel() == Ligne.class) {
                if (this.elementFantome == null) {
                    this.elementFantome = new LigneUI(new Ligne(posX, posY, this.metier.getCouleurActuel().toString(), this.metier.isRempli(), (int)e.getX(), (int)e.getY()));
                } else {
                    ((LigneUI)this.elementFantome).setEndX((int)e.getX());
                    ((LigneUI)this.elementFantome).setEndY((int)e.getY());
                }
            }
            this.paneDessin.getChildren().remove(this.elementFantome);
            this.paneDessin.getChildren().add(this.elementFantome);
        } else {
            this.paneDessin.getChildren().remove(this.elementFantome);
            this.elementFantome = null;
        }
        if(e.getEventType() == MouseEvent.MOUSE_PRESSED) {
            posX = (int) e.getX();
            posY = (int) e.getY();
        }

        if(e.getEventType() == MouseEvent.MOUSE_RELEASED) {
            Forme forme = null;

            if(this.metier.getFormeActuel() == Rectangle.class) {
                int minX = Math.min(posX, (int)e.getX());
                int minY = Math.min(posY, (int)e.getY());
                int finX = Math.abs(posX - (int)e.getX());
                int finY = Math.abs(posY - (int)e.getY());

                forme = new Rectangle(minX, minY, this.metier.getCouleurActuel().toString(), this.metier.isRempli(), finX, finY);
            }

            if(this.metier.getFormeActuel() == Cercle.class) {
                //Calcul du Rayon
                double distanceX = e.getX()-posX;
                double distanceY = e.getY()-posY;
                double rayon = Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2));

                forme = new Cercle(posX, posY, this.metier.getCouleurActuel().toString(), this.metier.isRempli(), (int) rayon);
            }

            if(this.metier.getFormeActuel() == Ligne.class) {
                forme = new Ligne(posX, posY, this.metier.getCouleurActuel().toString(), this.metier.isRempli(), (int) e.getX(), (int) e.getY());
            }

            if(this.metier.getFormeActuel() == Texte.class) {
                forme = new Texte(posX, posY, this.metier.getCouleurActuel().toString(), "TEXTE A EDIT");
            }

            if(this.metier.getFormeActuel() == Ellipse.class) {
                double rayonX = Math.abs(e.getX() - posX) / 2.0;
                double rayonY = Math.abs(e.getY() - posY) / 2.0;

                forme = new Ellipse(posX, posY,this.metier.getCouleurActuel().toString(),this.metier.isRempli(), (int) rayonX, (int) rayonY);
            }

            this.uiElements.add(forme);
            this.client.envoyerForme(forme);
            this.client.ajouterClientForme(forme);
            this.uiMaj();
        }
    }

    public void ajouterElement(Forme forme) {
        for (Forme f : this.uiElements) {
            if (f.equals(forme)) {
                return;
            }
        }
        this.uiElements.add(forme);
        this.uiMaj();
    }

    private void uiMaj() {
        // Supression des éléments existants
        this.paneDessin.getChildren().clear();

        // Mise à jour des nodes sur l'affichage
        for (Forme forme : this.uiElements) {
            Node node = null;

            if (forme instanceof Rectangle shape) {
                node = new RectangleUI(shape);
            } else if (forme instanceof Cercle shape) {
                node = new CercleUI(shape);
            } else if (forme instanceof Ligne shape) {
                node = new LigneUI(shape);
            } else if (forme instanceof Texte shape) {
                node = new TexteUI(shape);
            } else if (forme instanceof  Ellipse shape) {
                node = new EllipseUI(shape);
            }

            this.paneDessin.getChildren().add(node);
        }
    }

    public void hebergement() throws InterruptedException {
        if (serveur == null) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Hébergement");
            dialog.setHeaderText("Veuillez entrer un pseudo");

            ButtonType btnHost = new ButtonType("Héberger", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(btnHost, ButtonType.CANCEL);

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 100, 10, 10));

            TextField pseudo = new TextField();
            pseudo.setPromptText("Pseudo");

            gridPane.add(new Label("Pseudo:"), 0, 0);
            gridPane.add(pseudo, 1, 0);

            Node hostBtn = dialog.getDialogPane().lookupButton(btnHost);
            hostBtn.setDisable(true);

            pseudo.textProperty().addListener(((observable, oldValue, newValue) -> hostBtn.setDisable(newValue.trim().isEmpty())));

            dialog.getDialogPane().setContent(gridPane);

            dialog.setResultConverter(dialogBtn -> {
                if (dialogBtn == btnHost) {
                    return pseudo.getText();
                }
                return null;
            });

            Optional<String> result = dialog.showAndWait();

            if (result.isEmpty()) return;
            this.client = new Client(result.get(), "localhost", this);
            this.menuHerbergement.setText("Arrêter le partage");
            this.menuRejoindre.setDisable(true);
            this.serveur = new Serveur();
            Thread serveurThread = new Thread(this.serveur);
            Thread clientThread = new Thread(this.client);
            clientThread.start();
            Thread.sleep(20);
            serveurThread.start();
        } else {
            this.menuHerbergement.setText("Partager");
            this.menuRejoindre.setDisable(false);
            this.client.stop();
            this.client = null;
            this.serveur.stop();
            this.serveur = null;
            this.uiElements.clear();
            this.paneDessin.getChildren().clear();
            uiMaj();
        }
    }

    public void rejoindre() {
        if (this.client == null) {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Connexion");
            dialog.setHeaderText("Veuillez entrer les informations de connexion");

            ButtonType btnConnexion = new ButtonType("Connexion", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(btnConnexion, ButtonType.CANCEL);

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 100, 10, 10));

            TextField pseudo = new TextField();
            pseudo.setPromptText("Pseudo");
            TextField adresse = new TextField();
            adresse.setPromptText("Adresse");

            gridPane.add(new Label("Pseudo:"), 0, 0);
            gridPane.add(pseudo, 1, 0);
            gridPane.add(new Label("Adresse:"), 0, 1);
            gridPane.add(adresse, 1, 1);

            Node connectBtn = dialog.getDialogPane().lookupButton(btnConnexion);
            connectBtn.setDisable(true);

            pseudo.textProperty().addListener(((observable, oldValue, newValue) -> {
                connectBtn.setDisable(newValue.trim().isEmpty() && adresse.getText().isEmpty());
            }));

            dialog.getDialogPane().setContent(gridPane);

            dialog.setResultConverter(dialogBtn -> {
                if (dialogBtn == btnConnexion) {
                    return new Pair<>(pseudo.getText(), adresse.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();
            if (result.isEmpty()) return;
            this.menuRejoindre.setText("Déconnexion");
            this.menuHerbergement.setDisable(true);
            this.client = new Client(result.get().getKey(), result.get().getValue(), this);
            Thread clientThread = new Thread(this.client);
            clientThread.start();
        } else {
            deconnexion();
        }
    }

    public void deconnexion() {
        this.menuRejoindre.setText("Rejoindre");
        this.menuHerbergement.setDisable(false);
        this.client.stop();
        this.client = null;
        this.uiElements.clear();
        this.paneDessin.getChildren().clear();
        uiMaj();
    }

    public void removeElement(Forme forme) {
        this.uiElements.removeIf(f -> f.equals(forme));
        this.uiMaj();
    }

    public void fermer() {
        if (this.client != null) {
            this.client.stop();
        }
        if (this.serveur != null) {
            this.serveur.stop();
        }
        Stage stage = (Stage) this.paneDessin.getScene().getWindow();
        stage.close();
    }
}