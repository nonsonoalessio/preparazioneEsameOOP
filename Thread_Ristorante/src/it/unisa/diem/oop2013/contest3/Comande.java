package it.unisa.diem.oop2013.contest3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

public class Comande {
    private final String filename;
    private final boolean leggiBackup;
    private LinkedList<Ordinazione> coda = null;


    @SuppressWarnings("unchecked")
    public Comande(String filename, boolean leggiBackup) {
        this.filename = filename;
        this.leggiBackup = leggiBackup;
        if(!leggiBackup)
            coda = new LinkedList<>();
        else{
            try{
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("comande.bin")));
                this.coda = (LinkedList<Ordinazione>) ois.readObject();
                ois.close();
            } catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    // All the following methods are SYNCHRONIZED

    public synchronized void aggiungiOrdinazione(Ordinazione ordinazione){
        this.coda.addLast(ordinazione);
        notifyAll();
    }

    public synchronized boolean isEmpty(){
        return coda.isEmpty();
    }

    /**
     * Gestisce l’accesso alla coda di ordinazioni e restituire la prossima Ordinazione da servire, se c’è almeno una Ordinazione in coda. Se non ci sono ordinazioni in coda, il metodo deve rilasciare il mutex e restare in attesa che venga aggiunta un’ordinazione alla coda.
     * @return La successiva ordinazione da servire, se ve n'è almeno una.
     */
    public synchronized Ordinazione consegnaOrdinazione(){
        while (isEmpty()){
            try{
                wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        notifyAll();
        return this.coda.removeFirst();
    }
        /**
         * Gestisce l’attuale insieme di ordinazioni su file attraverso il sistema di serializzazione degli oggetti di Java. Dovranno essere implementate opportunamente le interfacce per la gestione del meccanismo di serializzazione.
         * Il salvataggio avviene solo se ci sono ordinazioni in coda altrimenti si deve restare in attesa che venga aggiunta un’ordinazione alla coda.
         */
    public synchronized void salvaOrdinazioni(){
        while (isEmpty()){
            try{
                wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        // code here
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("comande.bin")));
            oos.writeObject(this.coda);
            oos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        notifyAll();
    }
}
