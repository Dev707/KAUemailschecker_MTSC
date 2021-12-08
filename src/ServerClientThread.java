
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ServerClientThread extends Thread {

    int userID;
    Socket serverClient;
    SimpleDateFormat formatter = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]");

    ServerClientThread(Socket serverClient, int userID) {
        this.serverClient = serverClient;
        this.userID = userID;
    }

    @Override
    public void run() {
        try {
            DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
            String clientMessage, serverMessage;

            while (true) {
                serverMessage = "";
                serverMessage = "Server>> Enter your command: ";
                outStream.writeUTF(serverMessage);
                outStream.flush();
                clientMessage = inStream.readUTF().trim();
                try {
                    serverMessage = emailChecker(clientMessage);
                } catch (Exception e) {
                    serverMessage = "Server>> Error: enter correct format of email like: kalghamdi0324@stu.kau.edu.sa or enter exit\n";
                }
                outStream.writeUTF(serverMessage);
                outStream.flush();
                serverMessage = "";
                clientMessage = "";
            }
        } catch (IOException e) {
            System.out.println(formatter.format(new Date()) + " User No: " + userID + " on port NO. " + serverClient.getPort() + " Closed Connection!");
        }
    }

    public String emailChecker(String email) {
        String serverMessage;
        String regex = "(^[a-zA-Z0-9]+)([\\.{1}])?([a-zA-Z0-9]+)\\@stu([\\.])kau([\\.])edu([\\.])sa$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        boolean matchFound = matcher.find();
        if (matchFound) {
            serverMessage = "Server>> Match found\n";
            System.out.println(formatter.format(new Date()) + " User No: " + userID + " on port NO. " + serverClient.getPort() + " got Matech for \"" + email + "\"");
        } else {
            serverMessage = "Server>> Match not found\n";
            System.out.println(formatter.format(new Date()) + " User No: " + userID + " on port NO. " + serverClient.getPort() + " got Unmatch for \"" + email + "\"");

        }
        return serverMessage;
    }

}
