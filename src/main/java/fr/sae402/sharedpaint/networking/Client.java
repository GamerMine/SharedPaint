package fr.sae402.sharedpaint.networking;

import fr.sae402.sharedpaint.MainController;
import fr.sae402.sharedpaint.metier.Forme;
import fr.sae402.sharedpaint.metier.Metier;
import fr.sae402.sharedpaint.networking.packets.Commande;
import fr.sae402.sharedpaint.networking.packets.ObjectPacket;
import fr.sae402.sharedpaint.networking.packets.Packet;
import javafx.application.Platform;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.UUID;

public class Client implements Runnable {
    private UUID idClient;
    private String pseudo;
    private boolean stop;
    private DatagramSocket ds;
    private MainController ctrl;

    private static final int TAILLE_BUFFER = 1024;
    private static final byte[] buffer = new byte[TAILLE_BUFFER];

    public Client(String pseudo, MainController ctrl) {
        this.idClient = UUID.randomUUID();
        this.ctrl = ctrl;
        this.pseudo = pseudo;

        try {
            this.ds = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        this.stop = false;
    }

    public String getPseudo() {
        return pseudo;
    }

    @Override
    public void run() {
        DatagramPacket datagramPacket;
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

        DatagramPacket data = new DatagramPacket(buffer, TAILLE_BUFFER);
        while (!stop) {
            try {
                ds.receive(data);
                traiter(data);
            } catch (IOException | ClassNotFoundException ignored) {}
        }
        System.out.println("Client connection lost");
    }

    public void envoyerForme(Forme forme) {
        byte[] data = NetworkUtil.conversionByte(new ObjectPacket(Commande.SEND_SHAPE, forme));
        DatagramPacket datagramPacket;
        try {
            datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName("localhost"), Serveur.PORT); // TODO: localhost a modifier
            this.ds.send(datagramPacket);
        } catch (Exception ignored) {}
    }

    public void traiter(DatagramPacket data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data.getData());
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        Packet packet = (Packet) objectStream.readObject();

        if (packet instanceof ObjectPacket objectPacket) {
            switch (packet.getCommande()) {
                case UPDATE_SHAPE -> {
                    Forme forme = (Forme) objectPacket.getObject();
                    System.out.println(this.idClient + " received shape : " + forme.getClass().getTypeName());
                    Platform.runLater(() -> this.ctrl.ajouterElement(forme));
                }
            }
        }
    }

    public void stop() {
        ds.close();
        this.stop = true;
    }
}