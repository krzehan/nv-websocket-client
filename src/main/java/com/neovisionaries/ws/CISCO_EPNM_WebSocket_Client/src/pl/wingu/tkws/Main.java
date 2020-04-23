package pl.wingu.tkws;

import javax.net.ssl.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
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

        try {
            WebSocket ws = factory.createSocket("wss" +
                    "://" + epnmHostname + "//restconf//streams//v1//alarm.xml");
            ws.setUserInfo(args[1], args[2]);
            ws.connect();

            //OutputStream os = ws.getOutput();
            WebSocketInputStream is = ws.getInput();
            while(true){

                FileOutputStream outputStream = new FileOutputStream("EPNMwssAlarmOutput", true);
                byte[] strToBytes = is.readLine().getBytes();
                outputStream.write(strToBytes);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebSocket ws = new WebSocket();

      }

}