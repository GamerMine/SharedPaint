package fr.sae402.sharedpaint.networking.packets;

public class ObjectPacket extends Packet{
    private final Object o;

    public ObjectPacket(Commande commande, Object o) {
        super(commande);
        this.o = o;
    }

    public Object getObject() {
        return this.o;
    }
}
