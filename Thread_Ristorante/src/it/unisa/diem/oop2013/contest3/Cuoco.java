package it.unisa.diem.oop2013.contest3;

import java.util.concurrent.ThreadLocalRandom;

public class Cuoco implements Runnable{
    Comande comande;
    
    public Cuoco(Comande comande) {
        this.comande = comande;
    }

    @Override
    public void run(){
        while(true){
            Ordinazione o = comande.consegnaOrdinazione();
            try{
                Thread.sleep(ThreadLocalRandom.current().nextInt(5000, 10001));
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("Piatto pronto: " + o.toString());
        }
    }
}
