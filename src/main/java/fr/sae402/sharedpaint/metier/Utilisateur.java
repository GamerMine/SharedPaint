package fr.sae402.sharedpaint.metier;

import java.io.Serializable;
import java.util.UUID;

public class Utilisateur implements Serializable {
    private String pseudo;
    private UUID uuid;

    public Utilisateur(String pseudo, UUID uuid) {
        this.pseudo = pseudo;
        this.uuid = uuid;
    }

    public String getPseudo() {
        return pseudo;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Utilisateur user)) return false;
        return user.pseudo.equals(this.pseudo) && user.uuid.equals(this.uuid);
    }
}
