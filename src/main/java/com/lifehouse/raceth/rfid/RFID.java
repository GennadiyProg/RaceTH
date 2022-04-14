package com.lifehouse.raceth.rfid;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class RFID {

    public static class RFIDThread extends Thread {
        @Override
        public void run() {
            final int SERVICE_PORT=50001;

            try{
                // Создайте новый экземпляр DatagramSocket, чтобы получать ответы от клиента
                DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);


                while (true) {
                    /* Буфер для хранения получаемых данных,он временно хранит данные в случае задержек связи */
                    byte[] receivingDataBuffer = new byte[1024];

                    /* Создайте экземпляр UDP-пакета для хранения получаемых данных с использованием буфера для полученных данных */
                    DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
                    System.out.println("Waiting for a client to connect...");

                    // Получите данные от клиента и сохраните их в inputPacket
                    serverSocket.receive(inputPacket);

                    // Выведите на экран отправленные клиентом данные
                    String receivedData = new String(inputPacket.getData());
                    receivedData = receivedData.substring(receivedData.indexOf("Tag:")+4,receivedData.indexOf(0x0));
                    System.out.println(">>: "+receivedData);


                    if (false) {
                    serverSocket.close();
                    }
                }

                // Закройте соединение сокетов
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public HashMap<Integer, List<String>>[] RFIDMarkers = new HashMap[5];

    public static void main(String[] args) {
        Thread potok = new RFIDThread();
        potok.start();
    }



}
