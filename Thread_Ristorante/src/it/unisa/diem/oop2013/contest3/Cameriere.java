package it.unisa.diem.oop2013.contest3;

import java.util.concurrent.ThreadLocalRandom;

public class Cameriere implements Runnable {
    private String nome;
    private Comande comande;
    private Menu m = new Menu(); 

    public Cameriere(String nome, Comande comande){
        this.comande = comande;
        this.nome = nome;
    }

    @Override
    public void run(){
        while(true){
            int tavolo = ThreadLocalRandom.current().nextInt(1, 6);
            String piatto = m.getPiatto();
            int quantity = ThreadLocalRandom.current().nextInt(1, 5);
            try{
                Thread.sleep(ThreadLocalRandom.current().nextInt(5000, 10001));
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            Ordinazione o = new Ordinazione(piatto, tavolo, quantity);
            comande.aggiungiOrdinazione(o);
            System.out.println("Ordinazione presa da " + this.nome + ": " + o.toString());
        }
    }
}
