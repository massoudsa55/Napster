
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import static org.bouncycastle.crypto.tls.ConnectionEnd.client;

public class NapsterServerThread extends Thread {

    private Socket socket;
    private NapsterServer napsterServer;
    private  BufferedReader cin;
    private PrintStream cout;
    public NapsterServerThread(NapsterServer napsterServer,Socket socket) throws IOException {
        this.napsterServer = napsterServer;
        this.socket = socket;
        cin=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        cout=new PrintStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
         
             
            String command;

            do {
                if ((command = cin.readLine()) == null) {
                    command = "";
                    continue;
                }

                switch (command.charAt(0)) {
                    case '0':
                        command = "exit";
                        break;
                    case '1': // Join
                        // When a command starts with 1 --> a join message with the pseudoname of client
                        // Print "Welcome : <TheNameOfClient>"
                        System.out.println("Welcome : "+command.substring(2));
                        break;
                    case '2': // Update DataBase
                        // Print "Database updated : <Title;Artist;Album;Year;Duration>"
                        System.out.println("Database Updated : "+command.substring(2));

                        // Call updateDatabase() method to update database
                        napsterServer.updateDatabase(socket.getRemoteSocketAddress().toString(), command.substring(2));
                        // Call showDatabase() to print all Database Items

                        break;
                    case '3': // Search 
                        // Print "Search request for: <Title>"
                        System.out.println("Search request for : "+command.substring(2));

                        // writer print the result of search() method
                        cout.println(napsterServer.search(command.substring(2)));

                        break;
                }

            } while (!command.equals("exit"));

            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
