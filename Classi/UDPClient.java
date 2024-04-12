package com.mycompany.comunicazione_udp;

/**
 *
 * @author Giacomo Contini
 * 11/04/2024
 */

import java.io.*;
import java.net.*;

public class UDPClient {
  public static void main(String[] args) {
    try {
      int portaServer = 6789;  // porta del server
      InetAddress IPServer = InetAddress.getByName("localhost");

      boolean attivo = true;
      byte[] bufferOUT = new byte[1024]; // buffer per la ricezione
      byte[] bufferIN = new byte[1024]; // buffer per la spedizione
      while (attivo) {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        // creazione del socket
        DatagramSocket clientSocket = new DatagramSocket();
        System.out.println("Client pronto - inserisci un dato da inviare: ");
        // preparazione del messaggio da spedire 
        String daSpedire = input.readLine();
        bufferOUT = daSpedire.getBytes();
        // trasmissione del dato al server
        DatagramPacket sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, IPServer, portaServer);
        clientSocket.send(sendPacket);
        // ricezione del dato dal server
        DatagramPacket receivePacket = new DatagramPacket(bufferIN, bufferIN.length);
        clientSocket.receive(receivePacket);
        String ricevuto = new String(receivePacket.getData());
        // elaborazione dei dati ricevuti
        int numCaratteri = receivePacket.getLength();
        ricevuto = ricevuto.substring(0, numCaratteri);  // elimina i caratteri in eccesso
        System.out.println("dal SERVER:" + ricevuto);

        if (ricevuto.equals("FINE")) {
          // termina elaborazione
          attivo = false;
        }
        clientSocket.close();
      }
    } catch (SocketException e) {
      System.err.println("Errore " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Errore " + e.getMessage());
    }
  }
}