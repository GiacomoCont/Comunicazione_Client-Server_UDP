package com.mycompany.comunicazione_udp;

/**
 *
 * @author Giacomo Contini
 * 11/04/2024
 */

import java.io.*;
import java.net.*;

public class UDPServer {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(6789);
            boolean attivo = true; // per la ripetizione del servizio
            byte[] bufferIN = new byte[1024];  // buffer spedizione
            byte[] bufferOUT = new byte[1024]; // buffer per la ricezione
            System.out.println("SERVER avviato...");

            while (attivo) {
                // definizione del datagramma
                DatagramPacket receivePacket = new DatagramPacket(bufferIN, bufferIN.length);
                // attesa della ricezione dato dal client
                serverSocket.receive(receivePacket);
                // analisi del pacchetto ricevuto
                String ricevuto = new String(receivePacket.getData());
                int numCaratteri = receivePacket.getLength();
                ricevuto = ricevuto.substring(0, numCaratteri); // elimina i caratteri in ecceso
                System.out.println("RICEVUTO: " + ricevuto);
                // riconoscimento dei parametri dal socket al client
                InetAddress IPClient = receivePacket.getAddress();
                int portaClient = receivePacket.getPort();
                // preparo il dato da spedire 
                String daSpedire = ricevuto.toUpperCase();
                bufferOUT = daSpedire.getBytes();
                // invio del datagramma
                DatagramPacket sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, IPClient, portaClient);
                serverSocket.send(sendPacket);
                // controllo termine esecuzione del server
                if (ricevuto.equals("FINE")) {
                    System.out.println("SERVER IN CHIUSURA. Buona serata");
                    attivo = false;
                }
            }
            serverSocket.close();
        } catch (SocketException e) {
            System.err.println("Errore " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Errore " + e.getMessage());
        }
    }
}