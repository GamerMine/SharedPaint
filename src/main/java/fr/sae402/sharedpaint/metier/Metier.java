package fr.sae402.sharedpaint.metier;

import fr.sae402.sharedpaint.MainController;
import fr.sae402.sharedpaint.ui.RectangleUI;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Metier {

    private MainController controller;
    private Color couleurActuel;
    private Forme formeActuel;
    private boolean rempli;
    public Metier(MainController controller )
    {
        this.controller = controller;
    }

    public Color getCouleurActuel() {
        return couleurActuel;
    }

    public Forme getFormeActuel() {
        return formeActuel;
    }

    public boolean isRempli() {
        return rempli;
    }

    public void changerCouleur() {

    }

    public void changerForme() {

    }
    public void chargerJSON() {

    }

    public void ecrireJSON() {

    }




}
