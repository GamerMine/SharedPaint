package fr.sae402.sharedpaint.ui;

import fr.sae402.sharedpaint.metier.Ligne;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public class LigneUI extends Line {
    private Ligne ligne;

    public LigneUI(int posX, int posY, String couleur, boolean rempli, int posXFin, int posYFin) {
        this.ligne = new Ligne( posX,  posY,  couleur,  rempli,  posXFin,  posYFin);
        this.setStartX(posX);
        this.setStartY(posY);
        this.setEndX(posXFin);
        this.setEndY(posYFin);
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
