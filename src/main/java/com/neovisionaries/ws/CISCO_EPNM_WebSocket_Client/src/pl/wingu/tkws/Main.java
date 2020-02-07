package pl.wingu.tkws;

import javax.net.ssl.*;
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
                    "://" + epnmHostname + "//restconf//streams//v1//alarms.xml//");
            ws.connect();
            OutputStream os = ws.getOutput();


        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebSocket ws = new WebSocket();

      }

}
