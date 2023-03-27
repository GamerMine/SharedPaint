package fr.sae402.sharedpaint.networking;

import fr.sae402.sharedpaint.MainController;
import fr.sae402.sharedpaint.metier.shape.Forme;
import fr.sae402.sharedpaint.metier.Utilisateur;
import fr.sae402.sharedpaint.networking.packets.Commande;
import fr.sae402.sharedpaint.networking.packets.ObjectPacket;
import fr.sae402.sharedpaint.networking.packets.Packet;
import fr.sae402.sharedpaint.ui.window.InformationWindowController;
import javafx.application.Platform;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Client implements Runnable {
    private Utilisateur utilisateur;
    private String adresseServeur;
    private boolean stop;
    private DatagramSocket ds;
    private MainController ctrl;
    private LinkedList<Forme> formesClient;

    private static final int TAILLE_BUFFER = 1024;
    private static final byte[] buffer = new byte[TAILLE_BUFFER];

    public Client(String pseudo, String adresseServeur, MainController ctrl) {
        this.utilisateur = new Utilisateur(pseudo, UUID.randomUUID());
        this.ctrl = ctrl;
        this.adresseServeur = adresseServeur;
        this.formesClient = new LinkedList<>();

        try {
            this.ds = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        this.stop = false;
    }

    @Override
    public void run() {
        DatagramPacket datagramPacket;
        try {
            byte[] data = NetworkUtil.conversionByte(new ObjectPacket(Commande.USER_CONNECT, this.utilisateur));
            datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(this.adresseServeur), Serveur.PORT);
            ds.send(datagramPacket);
            data = NetworkUtil.conversionByte(new ObjectPacket(Commande.REQUEST_SHAPES, this.utilisateur));
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
                    Platform.runLater(() -> this.ctrl.ajouterElement(forme));
                }
                case REMOVE_SHAPE -> {
                    Forme forme = (Forme) objectPacket.getObject();
                    Platform.runLater(() -> this.ctrl.removeElement(forme));
                }
                case STOP_CONNECTION -> {
                    Platform.runLater(() -> this.ctrl.deconnexion());
                }
                case REQUEST_USERS -> {
                    List<Utilisateur> utilisateurs = (List<Utilisateur>) objectPacket.getObject();
                    InformationWindowController.setUsers(utilisateurs);
                }
            }
        }
    }

    public void stop() {
        byte[] data = NetworkUtil.conversionByte(new ObjectPacket(Commande.STOP_CONNECTION, utilisateur));
        DatagramPacket datagramPacket;
        try {
            datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(this.adresseServeur), Serveur.PORT);
            this.ds.send(datagramPacket);
        } catch (Exception ignored) {}
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

    public String getAdresseServeur() {
        return adresseServeur;
    }

    public void requestUtilisateurs() {
        byte[] data = NetworkUtil.conversionByte(new ObjectPacket(Commande.REQUEST_USERS, utilisateur));
        DatagramPacket datagramPacket;
        try {
            datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(this.adresseServeur), Serveur.PORT);
            this.ds.send(datagramPacket);
        } catch (Exception ignored) {}
    }
}