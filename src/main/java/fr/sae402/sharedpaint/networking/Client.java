package fr.sae402.sharedpaint.networking;

import fr.sae402.sharedpaint.MainController;
import fr.sae402.sharedpaint.metier.Forme;
import fr.sae402.sharedpaint.networking.packets.Commande;
import fr.sae402.sharedpaint.networking.packets.ObjectPacket;
import fr.sae402.sharedpaint.networking.packets.Packet;
import javafx.application.Platform;
import javafx.scene.Node;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.UUID;

public class Client implements Runnable {
    private UUID idClient;
    private String pseudo;
    private String adresseServeur;
    private boolean stop;
    private DatagramSocket ds;
    private MainController ctrl;
    private LinkedList<Forme> formesClient;

    private static final int TAILLE_BUFFER = 1024;
    private static final byte[] buffer = new byte[TAILLE_BUFFER];

    public Client(String pseudo, String adresseServeur, MainController ctrl) {
        this.idClient = UUID.randomUUID();
        this.ctrl = ctrl;
        this.pseudo = pseudo;
        this.adresseServeur = adresseServeur;
        this.formesClient = new LinkedList<>();

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
            datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(this.adresseServeur), Serveur.PORT);
            System.out.println("Envoie UUID de la part de : " + this.pseudo);
            ds.send(datagramPacket);
            data = NetworkUtil.conversionByte(new ObjectPacket(Commande.REQUEST_SHAPES, this.idClient));
            datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(this.adresseServeur), Serveur.PORT);
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
            datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(this.adresseServeur), Serveur.PORT);
            this.ds.send(datagramPacket);
        } catch (Exception ignored) {}
    }

    public void ajouterClientForme(Forme forme) {
        this.formesClient.add(forme);
    }

    public void traiter(DatagramPacket data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data.getData());
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        Packet packet = (Packet) objectStream.readObject();

        if (packet instanceof ObjectPacket objectPacket) {
            switch (packet.getCommande()) {
                case SEND_SHAPE -> {
                    Forme forme = (Forme) objectPacket.getObject();
                    System.out.println(this.idClient + " received shape : " + forme.getClass().getTypeName());
                    Platform.runLater(() -> this.ctrl.ajouterElement(forme));
                }
                case REMOVE_SHAPE -> {
                    Forme forme = (Forme) objectPacket.getObject();
                    Platform.runLater(() -> this.ctrl.removeElement(forme));
                }
                case STOP_CONNECTION -> {
                    Platform.runLater(() -> this.ctrl.deconnexion());
                }
            }
        }
    }

    public void stop() {
        ds.close();
        this.stop = true;
    }

    public Forme undoClientForme() {
        if (this.formesClient.size() == 0) return null;
        Forme undoForme = this.formesClient.removeLast();
        byte[] data = NetworkUtil.conversionByte(new ObjectPacket(Commande.REMOVE_SHAPE, undoForme));
        DatagramPacket datagramPacket;
        try {
            datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(this.adresseServeur), Serveur.PORT);
            this.ds.send(datagramPacket);
        } catch (Exception ignored) {}
        return undoForme;
    }
}