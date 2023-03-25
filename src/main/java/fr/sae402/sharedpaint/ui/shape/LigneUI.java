package fr.sae402.sharedpaint.ui.shape;

import fr.sae402.sharedpaint.metier.shape.Ligne;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LigneUI extends Line {
    private Ligne ligne;

    public LigneUI(Ligne ligne) {
        this.ligne = ligne;
        this.setStartX(ligne.getPosX());
        this.setStartY(ligne.getPosY());
        this.setEndX(ligne.getPosXFin());
        this.setEndY(ligne.getPosYFin());
        this.setStroke(Color.web(ligne.getCouleur()));
    }

    public int getDebPosX() {
        return (int) this.getStartX();
    }
    public int getDebPosY() {
        return (int) this.getStartY();
    }
    public int getFinPosX() {
        return (int) this.getEndX();
    }
    public int getFinPosY() {
        return (int) this.getEndY();
    }

    public Point2D getDeb() {
        return new Point2D(this.getStartX(),this.getStartY());
    }

    public Point2D getFin() {
        return new Point2D(this.getEndX(), this.getEndY());
    }

    public Ligne getElementLigne() {
        return this.ligne;
    }
}
