
public class NapsterClientCLI {

    public static void main(String[] args) {
        
        // Create a new instance of NapsterClient class 'nc';
        NapsterClient nc=new NapsterClient();
        
        // Connect this instance 'nc' to server with connect() methode
        nc.connect();
        // Identify a new client "1:Messaoud"
           nc.writeToServer("1:Messaoud");
     
        // Update the database  "2:Roar;Katy Perry;PRISM;2013;03m:42s"
       MP3File[] files = nc.getSharedFilesAsMp3(); 
        for (int i = 0; i < files.length; i++) {
            nc.writeToServer("2:"+files[i].getDom());
        }
        // Search a file  "3:Despacito"
        nc.writeToServer("3:They Say");
        // When you search a file you need to read response from server
        // Read a String by calling readFromServer() method and print it.
        String reponse = nc.readFromServer();
        reponse=nc.Traitment(reponse);
        System.out.println(reponse);
        if(reponse != "File not found"){
            nc.writeToServer("Down");           
        }
        while(true){
            
        }
        // Close connection -- cal exit() method 
        //nc.exit();
    }

}
