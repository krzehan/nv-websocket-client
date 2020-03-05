package pl.wingu.tkws;

import javax.net.ssl.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;

public class Main {


    public static void main (String [] args){

        String epnmHostname = "198.18.134.7";
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
            ws.setUserInfo("root", "Public123#");
            ws.connect();

            OutputStream os = ws.getOutput();
            InputStream is = ws.getInput();
            while(true){
                System.out.println(((WebSocketInputStream) is).readFrame());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebSocket ws = new WebSocket();

      }

}
