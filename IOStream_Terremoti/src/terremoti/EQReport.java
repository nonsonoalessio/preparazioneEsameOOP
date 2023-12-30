package terremoti;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class EQReport {
    private ArrayList<EQEvent> events;

    public EQReport(){
        this.events = new ArrayList<>();
    }

    public void addEvent(EQEvent e){
        this.events.add(e);
    }

    public void sort(){
        this.events.sort(null);
    }

    public void sort(Comparator<EQEvent> c){
        this.events.sort(c);
    }

    public ArrayList<EQEvent> getReport(){
        return this.events;
    }

    public static EQReport readFromTextFile(String filename){
        EQReport r = new EQReport();
        try(Scanner s = new Scanner(new FileReader(filename))){
            s.useDelimiter("\\|\n");
            while(s.hasNext()){
                long id = s.nextLong();
                LocalDateTime d = null;
                double latitude = s.nextDouble();
                double longitude = s.nextDouble();
                double depthKm = s.nextDouble();
                String author = s.next();
                String catalg = s.next();
                String contributor = s.next();
                long contributorID = s.nextLong();
                String magType = s.next();
                double magnitude = s.nextDouble();
                String magAuthor = s.next();
                String locationName = s.next(); 
                EQEvent e = new EQEvent(id, d, latitude, longitude, depthKm, author, catalg, contributor, contributorID, magType, magnitude, magAuthor, locationName);
                r.addEvent(e);
            }
            return r;
        } catch (IOException e){  +
            e.printStackTrace();
            return null;
        }    
    }

    public static void printToTextFile(EQReport eqr, String filename){
        try(BufferedWriter w = new BufferedWriter(new FileWriter(filename))) {
            ArrayList<EQEvent> r = eqr.getReport();
            for(EQEvent ev : r){
                w.append(ev.getEventID() + "|");
                w.append(ev.getTime() + "|");
                w.append(ev.getLatitude() + "|");
                w.append(ev.getLongitude() + "|");
                w.append(ev.get)
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
