package com.lifehouse.raceth.rfid;

import com.lifehouse.raceth.logic.marksmonitorpage.MarksMonitorCompetitionController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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

public class RFID implements Runnable{

    String threadName;
    Thread getTagThread;
    boolean suspendFlag;
    DatagramSocket serverSocket;
    TextField lastNumber;
    MarksMonitorCompetitionController guiController;

    public RFID(String threadName,DatagramSocket serverSocket, TextField lastNumber, MarksMonitorCompetitionController controller) {
        this.threadName = threadName;
        this.serverSocket = serverSocket;
        this.lastNumber = lastNumber;
        this.getTagThread = new Thread(this,threadName);
        this.suspendFlag = true;
        guiController = controller;
        getTagThread.start();
    }

    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    while (suspendFlag) {
                        wait();
                    }
                }
                getTag(this.serverSocket);
            }
        } catch (Exception e) {
            System.out.println(threadName + " prervan;");
            e.printStackTrace();
        }
        System.out.println("Vipolnenie " + threadName + " zaversheno;");
    }

    public void threadSuspend() {
        this.suspendFlag = true;
    }
     public synchronized void threadResume() {
        this.suspendFlag = false;
        notify();
    }

    public void getTag(DatagramSocket serverSocket) throws SocketException {
        final int SERVICE_PORT=50003;
        serverSocket = new DatagramSocket(SERVICE_PORT);

        try{
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
            System.out.println("SoutFromClass: "+ receivedData);
            int delZeros = Integer.parseInt(receivedData);
            receivedData = Integer.toString(delZeros);

            guiController.lastNumber.setText(receivedData);

        } catch (IOException e){
            System.out.println("ups in rfid");
//            return "UPS";
        } finally {
            serverSocket.close();
        }

    }


//    public HashMap<Integer, List<String>>[] RFIDMarkers = new HashMap[5];
//
//    public static void main(String[] args) {
//        Thread potok = new RFIDThread();
//        potok.start();
//    }



}
