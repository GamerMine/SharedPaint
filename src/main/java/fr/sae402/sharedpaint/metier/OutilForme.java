package fr.sae402.sharedpaint.metier;

import fr.sae402.sharedpaint.SharedPaint;
import fr.sae402.sharedpaint.metier.shape.*;
import fr.sae402.sharedpaint.ui.ImageToggleButton;
import javafx.scene.control.ToggleGroup;

import java.io.InputStream;
import java.util.ArrayList;

public class OutilForme extends ImageToggleButton {
    private Class<? extends Forme> forme;
    private final InputStream icon;
    private static final ArrayList<OutilForme> outilFormes = new ArrayList<>();

    private static final ToggleGroup groupeOutil = new ToggleGroup();

    // Add any tools that you want here
    public static final OutilForme RECTANGLE = new OutilForme(Rectangle.class, SharedPaint.class.getResourceAsStream("icons/rectangle.png"));
    public static final OutilForme CERCLE    = new OutilForme(Cercle.class, SharedPaint.class.getResourceAsStream("icons/circle.png"));
    public static final OutilForme LIGNE     = new OutilForme(Ligne.class, SharedPaint.class.getResourceAsStream("icons/line.png"));
    public static final OutilForme TEXTE     = new OutilForme(Texte.class, SharedPaint.class.getResourceAsStream("icons/text.png"));
    public static final OutilForme ELLIPSE   = new OutilForme(Ellipse.class, SharedPaint.class.getResourceAsStream("icons/ellipse.png"));

    public OutilForme(Class<? extends Forme> forme, InputStream icon) {
        super(icon, groupeOutil);
        this.forme = forme;
        this.icon = icon;
        OutilForme.outilFormes.add(this);
    }

    public InputStream getIcon() {
        return this.icon;
    }

    public Class<? extends Forme> getForme() {
        return this.forme;
    }

    public static ArrayList<OutilForme> getOutils() {
        return new ArrayList<>(OutilForme.outilFormes);
    }
}
