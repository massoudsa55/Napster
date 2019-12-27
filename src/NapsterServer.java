
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NapsterServer {

    ArrayList<MP3File> mp3Files = new ArrayList<>();
    ArrayList<String> hosts = new ArrayList<>();

    public static void main(String[] args) {
        NapsterServer ns = new NapsterServer();
        int port = 9876;
        try {
            // Create ServerSocket
            ServerSocket serv=new ServerSocket(port);
            // Print "Napster Server Listening on port :9876"
            System.out.println("Napster Server Listening on port :"+port);
            while (true) {
                // Accept a socket 
               Socket s=serv.accept();
                //Print "New Client connected 192.168.1.3" ; you can use socket.getRemoteSocketAddress() method.                                
                System.out.println("new client"+s.getRemoteSocketAddress());
                // Create a new instance of NapsterServerThread class and run it by calling start() method.
                NapsterServerThread lop= new NapsterServerThread(ns,s);
                lop.start();
                

            }

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    // Insert host ("192.168.1.4") in ArrayList hosts. 
    // Insert dom ("Roar;Katy Perry;PRISM;2013;03m:42s") in ArrayList mp3Files.
    public synchronized void updateDatabase(String host, String dom) {
      mp3Files.add(new MP3File(dom));
      hosts.add(host);

    }

    // This method print all files as : 
    // IP Title Artist Album Year Duration
    // 127.0.0.1 Roar	Katy Perry PRISM	2013	03m:42s
    public void showDatabase() {
        for (int i = 0; i < hosts.size(); i++) {
            System.out.println(mp3Files.get(i).getDom());
        }
    }

    /**
     * a method that search 'Title' on Database and return a String
     * "200:192.168.1.5" if found or return "404:Your file not found!" else.
     */
    public String Action(){
        return "Down";
    }
    public String search(String title) {
        
        for (int i = 0; i < hosts.size(); i++) {
            if (title.equalsIgnoreCase(mp3Files.get(i).getTitle()) ) {
                return "200:"+hosts.get(i);
            }
        }
        return "404:File not found";
    }

}
