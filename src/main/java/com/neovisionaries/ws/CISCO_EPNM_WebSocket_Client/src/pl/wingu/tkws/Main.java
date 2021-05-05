package pl.wingu.tkws;

import javax.net.ssl.*;
import java.io.FileOutputStream;

import java.security.NoSuchAlgorithmException;

public class Main {


    public static void main (String [] args){

        String epnmHostname = args[0];
        WebSocketFactory factory = new WebSocketFactory();

        try {
            SSLContext context = NaiveSSLContext.getInstance("TLS");
            factory.setSSLContext(context);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        factory.setVerifyHostname(false);
        WebSocket ws = null;
        try {
            ws = factory.createSocket("wss" +
                    "://" + epnmHostname + "//restconf//streams//v1//alarm.xml");
            ws.setUserInfo(args[1], args[2]);
            ws.connect();

            //OutputStream os = ws.getOutput();

            System.out.println(ws.getState().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        ReadingThread readingThread = new ReadingThread(ws);
        readingThread.runMain();

        WebSocketInputStream is = null;

        boolean listening = true;

        while (listening) {

            if (ws != null) {
                is = ws.getInput();
            }
            try {
                FileOutputStream outputStream = new FileOutputStream("EPNMwssAlarmOutput", true);
                byte[] strToBytes = is.readLine().getBytes();
                System.out.println("frame payload: " + is.readFrame());
                System.out.println("readLine: " + is.readLine());
                outputStream.write(strToBytes);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        //WebSocket ws = new WebSocket();

      }

}
