package fr.sae402.sharedpaint.ui.shape;

import fr.sae402.sharedpaint.metier.shape.Cercle;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CercleUI extends Circle {
    private Cercle cercle;

    public CercleUI(Cercle cercle) {
        this.cercle = cercle;
        this.setCenterX(cercle.getPosX());
        this.setCenterY(cercle.getPosY());
        this.setRadius(cercle.getRayon());
        if(cercle.getRempli() == false){
            this.setFill(Color.TRANSPARENT);
            this.setStroke(Color.web(cercle.getCouleur()));
        }else{
            this.setFill(Color.web(cercle.getCouleur()));
        }
    }

    public int getX() {
        return (int) this.getCenterX();
    }
    public int getY() {
        return (int) this.getCenterY();
    }

    public Point2D getCoordonnee(){
        return new Point2D(this.getCenterX(), this.getCenterY());
    }

    public Cercle getElementCercle() {
        return this.cercle;
    }
}
