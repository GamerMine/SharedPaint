package fr.sae402.sharedpaint.metier;

import fr.sae402.sharedpaint.MainController;
import fr.sae402.sharedpaint.metier.shape.Forme;
import javafx.scene.paint.Color;

public class Metier {

    private MainController controller;
    private Color couleurActuel;
    private Class<? extends Forme> formeActuel;
    private boolean rempli;
    public Metier(MainController controller )
    {
        this.controller = controller;
    }

    public Color getCouleurActuel() {
        return couleurActuel;
    }

    public Class<? extends Forme> getFormeActuel() {
        return formeActuel;
    }

    public boolean isRempli() {
        return rempli;
    }

    public boolean setRempli(Boolean b){return rempli = b;}

    public void changerCouleur(Color couleur) {
        this.couleurActuel = couleur;
    }

    public void changerForme(Class<? extends Forme> forme) {
        this.formeActuel = forme;
    }
    public void chargerJSON() {

    }

    public void ecrireJSON() {

    }




}
