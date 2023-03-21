package fr.sae402.sharedpaint.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.sae402.sharedpaint.metier.Forme;
import fr.sae402.sharedpaint.networking.packets.Commande;
import fr.sae402.sharedpaint.networking.packets.ObjectPacket;

import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import java.util.UUID;

public class Client implements Runnable {
    private UUID idClient;
    private String pseudo;
    private Gson gsonBuilder;
    private JsonArray historique;
    private FileWriter file;

    private DatagramSocket ds;

    public Client(String pseudo) {
        this.idClient = UUID.randomUUID();
        this.pseudo = pseudo;
        this.gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        this.historique = new JsonArray();
        try {
            this.file = new FileWriter(this.idClient+".json");
            this.ds = new DatagramSocket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPseudo() {
        return pseudo;
    }

    @Override
    public void run() {
        DatagramPacket datagramPacket = null;
        try {
            byte[] data = NetworkUtil.conversionByte(new ObjectPacket(Commande.USER_CONNECT, this.idClient));
            datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName("localhost"), Serveur.PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Envoie UUID de la part de : " + this.pseudo);
        try {
            ds.send(datagramPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (true) {

        }
    }
}
