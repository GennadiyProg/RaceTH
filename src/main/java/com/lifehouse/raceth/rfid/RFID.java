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
import java.net.BindException;
import java.util.HashMap;
import java.util.List;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class RFID{



    public static String getTag() throws SocketException {

        final int SERVICE_PORT=50001;
        // Создайте новый экземпляр DatagramSocket, чтобы получать ответы от клиента
        DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);

        try{
//            final int SERVICE_PORT=50001;
//            // Создайте новый экземпляр DatagramSocket, чтобы получать ответы от клиента
//            DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);
            /* Буфер для хранения получаемых данных,он временно хранит данные в случае задержек связи */
            byte[] receivingDataBuffer = new byte[1024];

            /* Создайте экземпляр UDP-пакета для хранения получаемых данных с использованием буфера для полученных данных */
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            System.out.println("Waiting for a client to connect...");

            // Получите данные от клиента и сохраните их в inputPacket
            serverSocket.receive(inputPacket);

            // Выведите на экран отправленные клиентом данные
            String receivedData = new String(inputPacket.getData());
//          int lastNumber = Integer.parseInt(receivedData.substring(receivedData.indexOf("Tag:")+4,receivedData.indexOf(0x0)));
            receivedData = receivedData.substring(receivedData.indexOf("Tag:")+4,receivedData.indexOf(0x0));
            System.out.println("SoutFromClass: "+ receivedData);
            int delZeros = Integer.parseInt(receivedData);
            receivedData = Integer.toString(delZeros);


            // Закройте соединение сокетов
            serverSocket.close();
            return receivedData;

        } catch (IOException e){
//            e.printStackTrace();
        } finally {
            serverSocket.close();
            return "Упс";
        }

    }


//    public HashMap<Integer, List<String>>[] RFIDMarkers = new HashMap[5];
//
//    public static void main(String[] args) {
//        Thread potok = new RFIDThread();
//        potok.start();
//    }



}
