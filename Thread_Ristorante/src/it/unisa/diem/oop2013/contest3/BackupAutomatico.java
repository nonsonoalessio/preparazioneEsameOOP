package it.unisa.diem.oop2013.contest3;

public class BackupAutomatico implements Runnable {
    
    Comande comande;

    public BackupAutomatico(Comande comande){
        this.comande = comande;
    }

    @Override
    public void run(){
        while(true){
            comande.salvaOrdinazioni();
            System.out.println("BACKUP EFFETTUATO");
            try{
                Thread.sleep(20000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
