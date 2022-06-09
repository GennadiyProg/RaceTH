package com.lifehouse.raceth.rfid;

import com.lifehouse.raceth.logic.marksmonitorpage.MarksMonitorCompetitionController;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class RFID implements Runnable {

    private String threadName;
    public static Thread getTagThread;
    private boolean suspendFlag;
    public DatagramSocket serverSocket;
    private final MarksMonitorCompetitionController guiController;
    public static final int SERVICE_PORT = 27069;
    public static boolean threadFlag = true;

    public RFID(String threadName, MarksMonitorCompetitionController controller) {
        this.threadName = threadName;
        getTagThread = new Thread(this, threadName);
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

        try {
            /* Буфер для хранения получаемых данных,он временно хранит данные в случае задержек связи */
            byte[] receivingDataBuffer = new byte[1024];


            /* Создайте экземпляр UDP-пакета для хранения получаемых данных с использованием буфера для полученных данных */
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            System.out.println("Waiting for a client to connect...");

            // Получите данные от клиента и сохраните их в inputPacket
            serverSocket.receive(inputPacket);

            // Выведите на экран отправленные клиентом данные
            String receivedData = new String(inputPacket.getData());
            receivedData = receivedData.substring(receivedData.indexOf("Tag:") + 4, receivedData.indexOf(0x0)).trim();
            System.out.println("SoutFromClass: " + receivedData);
//            int delZeros = Integer.parseInt(receivedData);
//            receivedData = Integer.toString(delZeros);

            guiController.addNewCheckpoint(receivedData);

        } catch (Exception e) {
//            System.out.println("ups in rfid");
            e.printStackTrace();
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
