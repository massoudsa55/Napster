
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.helpers.DefaultHandler;

public class MP3File {

    private String title = "";
    private String artist = "";
    private String album = "";
    private String year = "";
    private String bitRate = "";

    public MP3File(File file) {
        try {
            InputStream input = new FileInputStream(file);
            DefaultHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata, parseCtx);
            input.close();

            title = metadata.get("title");
            artist = metadata.get("xmpDM:artist");
            album =metadata.get("xmpDM:album");
            year =metadata.get("xmpDM:release Date");
            bitRate =metadata.get("xmpDM:duration");
             
          
        } catch (Exception ex) {
        }
    }


    
    public MP3File(String dom) {
        // use split(";") to get String[] cells;
        String[] cells = dom.split(";");
        title =  cells[0];
        artist = cells[1];
        album =cells[2];
        year =cells[3];
        bitRate =cells[4];
    }

    // return String as : "Title;Artist;Album;Year;BitRate"
    public String getDom() {
        return ""+title+";"+artist+";"+album+";"+year+";"+bitRate;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getYear() {
        return year;
    }

    public String getBitRate() {
        return bitRate;
    }

    private String toMinutes(String timeInMilis) {
        double milis = Double.valueOf(timeInMilis);
        long second = (long) (milis / 1000) % 60;
        long minute = (long) (milis / (1000 * 60)) % 60;
        long hour = (long) (milis / (1000 * 60 * 60)) % 24;
        return hour != 0 ? String.format("%02dh:%02dm:%02ds", hour, minute, second) : String.format("%02dm:%02ds", minute, second);
    }

    
    @Override
    public String toString() {
        String str = "";
       // Return  "Title      Artist       Album       Year         BitRate"
       str=title+"\t"+artist+"\t"+album+"\t"+year+"\t"+bitRate;
        return str;
    }

}
