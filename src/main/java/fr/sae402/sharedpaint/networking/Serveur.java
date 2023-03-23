package fr.sae402.sharedpaint.networking;

import fr.sae402.sharedpaint.metier.Forme;
import fr.sae402.sharedpaint.networking.packets.Commande;
import fr.sae402.sharedpaint.networking.packets.ObjectPacket;
import fr.sae402.sharedpaint.networking.packets.Packet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.HashMap;
import java.util.UUID;

public class Serveur implements Runnable {
    public static final int PORT = 7385;
    private static final int TAILLE_BUFFER = 1024;
    private static final byte[] buffer = new byte[TAILLE_BUFFER];

    private DatagramSocket ds;
    private HashMap<UUID, SocketAddress> clients;
    private boolean stop;

    public Serveur() {
        try {
            this.ds = new DatagramSocket(PORT);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        this.clients = new HashMap<>();
        this.stop = false;
    }

    private void traiter(DatagramPacket data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data.getData());
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        Packet packet = (Packet) objectStream.readObject();

        if (packet instanceof ObjectPacket objectPacket) {
            switch (packet.getCommande()) {
                case USER_CONNECT -> {
                    UUID clientUUID = (UUID) objectPacket.getObject();
                    System.out.println("Received client UUID : " + clientUUID);
                    this.clients.put(clientUUID, data.getSocketAddress());
                }
                case STOP_CONNECTION -> {
                    UUID clientUUID = (UUID) objectPacket.getObject();
                    this.clients.remove(clientUUID);
                }
                case SEND_SHAPE -> {
                    Forme forme = (Forme) objectPacket.getObject();
                    System.out.println("Received a shape : " + forme.getClass().getTypeName());
                    this.sendToAll(Commande.UPDATE_SHAPE, forme);
                }
            }
        }
    }

    private void sendToAll(Commande commande, Forme forme) {
        for (UUID uuid : this.clients.keySet()) {
            byte[] data = NetworkUtil.conversionByte(new ObjectPacket(Commande.UPDATE_SHAPE, forme));
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length,
                    this.clients.get(uuid));
            try {
                System.out.println("Sending " + forme.getClass().getTypeName() + " to " + uuid);
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
        System.out.println("Server closed");
    }

    public void stop() {
        ds.close();
        this.stop = true;
    }
}