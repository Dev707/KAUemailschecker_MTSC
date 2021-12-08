
import java.net.*;
import java.io.*;
import java.util.logging.*;

public class TCPClient {

    static Socket socket;
    static DataOutputStream outStream;
    static BufferedReader br;
    static String clientMessage = "";
    static String serverMessage = "";
    static DataInputStream inStream;

    public static void main(String[] args) throws Exception {
        try {
            // creating client socket to connect to port 8888
            int port = 8888;
            socket = new Socket("127.0.0.1", port);
            System.out.println("Connecting to server with port number [" + port + "]");
        } catch (ConnectException e) {
            System.out.println("Server is not accepting connections because it "
                    + "is not running or stacked (Connection refused: connect)");
            System.out.println(e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected! [ You can exit the connection at any time using ('quit', 'exit') ]");
        System.out.println("Enter the email such as \"kalghamdi0324@stu.kau.edu.sa\"");
        try {
            inStream = new DataInputStream(socket.getInputStream());
            outStream = new DataOutputStream(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                final Thread outThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            reply();
                        } catch (IOException ex) {
                            Logger.getLogger(TCPClient.class.getName())
                                    .log(Level.SEVERE, null, ex);
                        }
                    }
                };
                outThread.start();
                serverMessage = inStream.readUTF();
                System.out.print(serverMessage);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public static void reply() throws IOException {
        clientMessage = br.readLine().toLowerCase();
        if (clientMessage.equalsIgnoreCase("exit")
                || clientMessage.equalsIgnoreCase("quit")) {
            System.out.print("Exitting...");
            socket.close();
            return;
        }
        outStream.writeUTF(clientMessage);
        outStream.flush();
    }

}
