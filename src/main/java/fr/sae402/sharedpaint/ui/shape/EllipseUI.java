package fr.sae402.sharedpaint.ui.shape;

import fr.sae402.sharedpaint.metier.shape.Ellipse;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class EllipseUI extends javafx.scene.shape.Ellipse {
    private Ellipse ellipse;

    public EllipseUI(Ellipse ellipse) {
        this.ellipse = ellipse;
        this.setCenterX(ellipse.getPosX());
        this.setCenterY(ellipse.getPosY());
        this.setRadiusX(ellipse.getRayonX());
        this.setRadiusY(ellipse.getRayonY());

        // Simplification
        if(!ellipse.getRempli()){
            this.setFill(Color.TRANSPARENT);
            this.setStroke(Color.web(ellipse.getCouleur()));
        }else{
            this.setFill(Color.web(ellipse.getCouleur()));
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

    public Ellipse getElementEllipse() {
        return this.ellipse;
    }
}
