package terremoti;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

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

    public static EQReport readFromTextFile(String filename){
        EQReport r = null;
        try(DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)))){
        } catch (IOException e){
            System.out.println("c");
        }    
        return r;
    }

    public static void printToTextFile(EQReport eqr, String filename){}
}
