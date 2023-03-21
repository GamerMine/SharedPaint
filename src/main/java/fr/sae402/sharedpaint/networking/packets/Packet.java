package fr.sae402.sharedpaint.networking.packets;

import java.io.Serializable;

public class Packet implements Serializable {
    private final Commande commande;

    public Packet(Commande commande) {
        this.commande = commande;
    }

    public Commande getCommande() {
        return this.commande;
    }
}
