package fr.sae402.sharedpaint.metier;

import java.net.URL;
import java.util.ArrayList;

public class OutilForme {
    private Class<? extends Forme> forme;
    private URL icon;
    private static ArrayList<OutilForme> outilFormes = new ArrayList<>();

    // Add any tools that you want here
    public static final OutilForme RECTANGLE = new OutilForme(Rectangle.class, OutilForme.class.getResource("icons/rectangle.png"));
    public static final OutilForme CERCLE    = new OutilForme(Cercle.class, OutilForme.class.getResource("icons/circle.png"));
    public static final OutilForme LIGNE     = new OutilForme(Ligne.class, OutilForme.class.getResource("icons/line.png"));

    public OutilForme(Class<? extends Forme> forme, URL icon) {
        this.forme = forme;
        this.icon = icon;
        System.out.println(icon);
        OutilForme.outilFormes.add(this);
    }

    public URL getIcon() {
        return this.icon;
    }

    public static ArrayList<OutilForme> getOutils() {
        return new ArrayList<>(OutilForme.outilFormes);
    }
}
