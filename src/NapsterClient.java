
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class NapsterClient {

    private File[] sharedFiled;
    private String defaultPath = ".\\Music";
    private final String serverIp = "127.0.0.1";
    private final int serverPort = 9876;
    private InputStream input;
    private OutputStream output;
    private BufferedReader reader;
    private PrintWriter writer;

    private Socket socket;

    public NapsterClient() {
            // Print "Napster Client "
            System.out.println("Napster client");
    }

    public void connect() {
        try {
            // Create a new Socket instance (socket) with serverIp and serverPort parameters
            socket=new Socket(serverIp,serverPort);
            

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public String readFromServer() {
        String text = "";
        try {
          BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            text=sin.readLine();

        } catch (IOException ex) {
        }
        return text;
    }

    public void writeToServer(String text) {
        try {
           
            PrintStream sout = new PrintStream(socket.getOutputStream());
            sout.println(text);

        } catch (IOException ex) {
            Logger.getLogger(NapsterClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
  public File[] getSharedFiles() {

        File currentDir = new File(defaultPath);
        // if(currentDir not exists?) print "Default path Not exists!" and System.exit(-1);
        if (!currentDir.exists()) {
            System.out.println("\"Default path Not exists");
            System.exit(-1);
        }
        // sharedFiled get an array of MP3 files ONLY
        sharedFiled=currentDir.listFiles((File dir,String name) -> name.endsWith("mp3"));
        // you can use FilenameFilter() and override accpet method -- return fileName.endsWith(".mp3")

        
        return sharedFiled;
    }

   public MP3File[] getSharedFilesAsMp3() {
       sharedFiled=getSharedFiles();

        MP3File[] sharedFiledMp3 = new MP3File[sharedFiled.length];
        
        //For each element create an MP3File(File f)
        for (int i = 0; i < sharedFiled.length; i++) {
            sharedFiledMp3[i]=new MP3File(sharedFiled[i]);
            
        }
        return sharedFiledMp3;
    }

    public void exit() {
        // Print "Napster client exit..."
        System.out.println("Napster client exit...");

        // Write to server "0"
        writeToServer("0");
        try {
            // Close the socket
            socket.close();
        } catch (IOException ex) {
        }
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }
    public String Traitment(String s){
        if (s.startsWith("404:")) {
            s=s.substring(4);
        }
        if (s.startsWith("200:")) {
            s="You can Download From thies Ip"+s.substring(4);
        }
        return s;     
    }
}
