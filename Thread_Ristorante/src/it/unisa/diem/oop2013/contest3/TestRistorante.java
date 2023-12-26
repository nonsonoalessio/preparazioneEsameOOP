package it.unisa.diem.oop2013.contest3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;

public class TestRistorante {

	public static void main(String[] args) {
		System.out.println("RISTORANTE MIVIA");
		Comande comande = new Comande("backup.dat", false);
		
		System.out.println("Inserire il numero di camerieri: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
		try {
			line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int n = Integer.parseInt(line);
		
		LinkedList<Cameriere> listacamerieri = new LinkedList<Cameriere>();
		for (int i = 0; i < n; i++) {
			System.out.println("Inserire il nome del cameriere "+(i+1)+": ");
			String nome = null;
			try {
				nome = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			listacamerieri.add(new Cameriere(nome, comande));
		}
		
		System.out.println("\nAVVIO RISTORANTE\n");
		
		// Inizializzazione camerieri
		Iterator<Cameriere> it = listacamerieri.iterator();
		while (it.hasNext()) {
			Thread tcameriere = new Thread(it.next());
			tcameriere.start();
		}
		
		// Inizializzazione cuoco
		Cuoco cuoco = new Cuoco(comande);
		Thread tcuoco = new Thread(cuoco);
		tcuoco.start();
		
		// Inizializzazione backup
		BackupAutomatico backup = new BackupAutomatico(comande);
		Thread tbackup = new Thread(backup);
		tbackup.start();
	}
}
