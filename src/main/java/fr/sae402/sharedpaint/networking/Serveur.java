package fr.sae402.sharedpaint.networking;

import fr.sae402.sharedpaint.metier.shape.Forme;
import fr.sae402.sharedpaint.metier.Utilisateur;
import fr.sae402.sharedpaint.networking.packets.Commande;
import fr.sae402.sharedpaint.networking.packets.ObjectPacket;
import fr.sae402.sharedpaint.networking.packets.Packet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Serveur implements Runnable {
    public static final int PORT = 7385;
    private static final int TAILLE_BUFFER = 1024;
    private static final byte[] buffer = new byte[TAILLE_BUFFER];

    private DatagramSocket ds;
    private HashMap<Utilisateur, SocketAddress> clients;
    private ArrayList<Forme> listFormes;
    private boolean stop;

    public Serveur() {
        try {
            this.ds = new DatagramSocket(PORT);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        this.clients = new HashMap<>();
        this.listFormes = new ArrayList<>();
        this.stop = false;
    }

    private void traiter(DatagramPacket data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data.getData());
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        Packet packet = (Packet) objectStream.readObject();

        if (packet instanceof ObjectPacket objectPacket) {
            switch (packet.getCommande()) {
                case USER_CONNECT -> {
                    Utilisateur user = (Utilisateur) objectPacket.getObject();
                    System.out.println(user.getPseudo() + " connected!");
                    this.clients.put(user, data.getSocketAddress());
                }
                case STOP_CONNECTION -> {
                    Utilisateur user = (Utilisateur) objectPacket.getObject();
                    Iterator it = this.clients.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<Utilisateur, SocketAddress> item = (Map.Entry<Utilisateur, SocketAddress>) it.next();
                        if (item.getKey().equals(user)) {
                            it.remove();
                        }
                    }
                }
                case SEND_SHAPE -> {
                    Forme forme = (Forme) objectPacket.getObject();
                    this.listFormes.add(forme);
                    this.sendToAll(Commande.SEND_SHAPE, forme);
                }
                case REQUEST_SHAPES -> {
                    Utilisateur user = (Utilisateur) objectPacket.getObject();
                    for (Utilisateur utilisateur : this.clients.keySet()) {
                        if (utilisateur.equals(user)) {
                            this.sendTo(utilisateur, objectPacket.getCommande(), null);
                        }
                    }
                }
                case REMOVE_SHAPE -> {
                    Forme forme = (Forme) objectPacket.getObject();
                    this.listFormes.removeIf(f -> f.equals(forme));
                    this.sendToAll(Commande.REMOVE_SHAPE, forme);
                }
                case REQUEST_USERS -> {
                    Utilisateur utilisateur = (Utilisateur) objectPacket.getObject();
                    for (Utilisateur user : clients.keySet()) {
                        if (utilisateur.equals(user)) {
                            this.sendTo(user, Commande.REQUEST_USERS, this.clients.keySet().stream().toList());
                        }
                    }
                    System.out.println(this.clients.keySet().stream());
                }
            }
        }
    }

    private void sendTo(Utilisateur user, Commande commande, Object obj) {
        try {
            switch (commande) {
                case REQUEST_SHAPES -> {
                    for (Forme forme : this.listFormes) {
                        byte[] data = NetworkUtil.conversionByte(new ObjectPacket(Commande.SEND_SHAPE, forme));
                        DatagramPacket datagramPacket = new DatagramPacket(data, data.length,
                                this.clients.get(user));
                        try {
                            this.ds.send(datagramPacket);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                case REQUEST_USERS -> {
                    byte[] data = NetworkUtil.conversionByte(new ObjectPacket(Commande.REQUEST_USERS, obj));
                    DatagramPacket datagramPacket = new DatagramPacket(data, data.length,
                            this.clients.get(user));
                    try {
                        this.ds.send(datagramPacket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendToAll(Commande commande, Forme forme) {
        for (Utilisateur user : this.clients.keySet()) {
            byte[] data = NetworkUtil.conversionByte(new ObjectPacket(commande, forme));
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length,
                    this.clients.get(user));
            try {
                this.ds.send(datagramPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        DatagramPacket data = new DatagramPacket(buffer, TAILLE_BUFFER);
        while (!stop) {
            try {
                ds.receive(data);
                traiter(data);
            } catch (IOException | ClassNotFoundException ignored) {
            }
        }
    }

    public void stop() {
        this.sendToAll(Commande.STOP_CONNECTION, null);
        ds.close();
        this.stop = true;
    }
}