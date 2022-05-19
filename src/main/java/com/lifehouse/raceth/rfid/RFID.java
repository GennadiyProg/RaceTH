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

    private String threadName;
    public static Thread getTagThread;
    private boolean suspendFlag;
    public DatagramSocket serverSocket;
    private MarksMonitorCompetitionController guiController;
    public static final int SERVICE_PORT=50007;
    public static boolean threadFlag = true;

    public RFID(String threadName, MarksMonitorCompetitionController controller) {
        this.threadName = threadName;
        this.getTagThread = new Thread(this,threadName);
        this.suspendFlag = true;
        guiController = controller;
        getTagThread.start();
    }

    public void run() {
        try {
            while (threadFlag) {
                synchronized (this) {
                    if (suspendFlag) {
                        wait();
                    }
                }
                getTag(this.serverSocket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getTagThread.stop();
        }
    }

    public void threadSuspend() {
        this.suspendFlag = true;
    }

    public synchronized void threadResume() {
        this.suspendFlag = false;
        notify();
    }

    public void getTag(DatagramSocket serverSocket) throws SocketException {

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

            guiController.addNewCheakpoint(receivedData);

        } catch (IOException e){
            System.out.println("ups in rfid");
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
