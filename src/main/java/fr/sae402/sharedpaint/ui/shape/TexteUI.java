package fr.sae402.sharedpaint.ui.shape;

import fr.sae402.sharedpaint.metier.shape.Texte;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TexteUI extends Text {
    private Texte texte;

    public TexteUI(Texte texte) {
        this.texte = texte;
        this.setX(texte.getPosX());
        this.setY(texte.getPosY());
        this.setText(texte.getTexte());
        this.setFill(Color.web(texte.getCouleur()));
    }

    public Point2D getCoordonnee() {
        return new Point2D(this.getX(), this.getY());
    }

    public String getTexte() {
        return this.getText();
    }

    public Texte getElementTexte() {
        return this.texte;
    }
}
