package pl.wingu;

import java.util.Scanner;
import org.atmosphere.wasync.ClientFactory;
import org.atmosphere.wasync.Event;
import org.atmosphere.wasync.Function;
import org.atmosphere.wasync.Request;
import org.atmosphere.wasync.RequestBuilder;
import org.atmosphere.wasync.Socket;
import org.atmosphere.wasync.impl.AtmosphereClient;


public class NotificationsClient {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(NotificationsClient.class);

    final static String NOTIFICATIONS_CONTEXT = "/restconf/streams/v1/";

    static String url = "https://";
    static String ip = "";

    public static void main(String[] args) throws Exception {

        System.out.println();
        System.out.println("\t\t######################################");
        System.out.println("\t\t#            CISCO EPN-M             #");
        System.out.println("\t\t#    RESTCONF NOTIFICATIONS CLIENT   #");
        System.out.println("\t\t######################################");
        System.out.println();

        try {

            LOG.debug("Obtained {} arguments", args.length);

            if (args.length == 0) {
                LOG.debug("No arguments were passed to the application. Prompting user for information");
                url = promptUserForUrl();
                args = new String[] { url };
            } else if (args.length == 1) {
                url = args[0];
                LOG.debug("One argument was passed to the application. Expecting it to be the entire subscription url");
                LOG.debug("URL: {}" + url);
            }

            if (url.startsWith("https")) {
                SslCertificateInstaller certificateInstaller = new SslCertificateInstaller();
                certificateInstaller.installCertificate(ip);
            }

            AtmosphereClient client = ClientFactory.getDefault().newClient(AtmosphereClient.class);
            if (client == null) {
                throw new Exception("Atmosphere client could not be initialized");
            }

            @SuppressWarnings("rawtypes")
            RequestBuilder request = client.newRequestBuilder().method(Request.METHOD.GET).uri(args[0] + ".xml").header("Authorization", "Basic cm9vdDpQdWJsaWMxMjM=").trackMessageLength(true)
                    .transport(Request.TRANSPORT.WEBSOCKET).transport(Request.TRANSPORT.SSE).transport(Request.TRANSPORT.LONG_POLLING);

            if (request == null) {
                throw new Exception ("The required request object could not be built");
            } else {
                LOG.debug ("request object built successfully with the following options:");
                LOG.debug("URL: "+ request.uri());
                for (Object transport: request.transports()) {
                    LOG.debug(transport.toString());
                }

            }

            Socket socket = client.create();

            System.out.print("\nConnecting to " + url);
            System.out.print(" ... ");

            socket.on("message", new Function<String>() {
                @Override
                public void on(String notificationMessage) {
                    LOG.debug("Received notification: " + notificationMessage);
                    System.out.println("Notification: \n\n" + notificationMessage);
                }
            }).on(new Function<Throwable>() {

                @Override
                public void on(Throwable t) {
                    t.printStackTrace();
                    System.exit(1);
                }

            }).on(Event.CLOSE.name(), new Function<String>() {
                @Override
                public void on(String t) {
                    System.out.println("Connection closed");
                    System.exit(1);
                }
            }).on(Event.OPEN.name(), new Function<String>() {
                @Override
                public void on(String t) {
                    System.out.println("done");
                }
            }).open(request.build());

            System.out.println("\nSubscription URL: " + url);
            System.out.println("\nListening for notifications\n");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private static String promptUserForUrl() {
        Scanner reader = new Scanner(System.in);
        try {
            System.out.println("The client can be run either iteractively or supplying the subscription url as an argument");
            System.out.println("For ex.");
            System.out.println("1. java -jar epnm-restconf-nbi-notifications-client-*.jar https://<server_name>/restconf/streams/v1/inventory");
            System.out.println("2. java -jar epnm-restconf-nbi-notifications-client-*.jar https://<server_name>/restconf/streams/v1/service-activation");

            System.out.print("\nEnter the EPNM Server's FQDN (IP address will not work): ");
            ip = reader.next();
            System.out.println();
            System.out.println("The following types of notifications are supported: ");
            System.out.println("\t1. INVENTORY");
            System.out.println("\t2. ALARM");
            System.out.println("\t3. SERVICE ACTIVATION");
            System.out.println("\t4. TEMPLATE EXECUTION");
            System.out.println("\t5. All");
            System.out.println();
            System.out.print("Choose the type of notifications to listen for: ");
            int option = reader.nextInt();

            if (option == 1) {
                url = url.concat(ip).concat(NOTIFICATIONS_CONTEXT).concat("inventory");
            } else if (option == 2) {
                url = url.concat(ip).concat(NOTIFICATIONS_CONTEXT).concat("service-activation");
            }
            // Other options as required...
            else {
                System.err.println("Invalid option");
                System.exit(-1);
            }

            System.out.println("(Optional Filters, press enter to skip)");
            System.out.println("Enter filters by following the format:");
            System.out.println("\tFILTER_TYPE1=FILTER_VALUE1&FILTER_TYPE2=FILTER_VALUE2");
            System.out.println("\tExample: productType=Cisco ASR 4000&productFamily=Routers");

            if (!StringUtils.isEmpty(inputFilters)) {
                if (!inputFilters.contains("=")) {
                    System.err.println("Invalid filter.");
                    System.err.println("Must be of format: FILTERKEY=FILTERVALUE");
                    System.exit(-1);
                } else {
                    System.out.println();
                    System.out.println("Filters entered:");
                    System.out.println(inputFilters);
                    filters = "?" + inputFilters;
                }
            }
            return url;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
