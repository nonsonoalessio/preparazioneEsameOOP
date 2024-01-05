package it.unisa.diem.oop23.javafx_libri;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class CaricaCatologoService extends Service<ArrayList<Libro>> {

    private final URL url;
    private int limit;
    private int minYear;
    private int maxYear;
    private ProgressBar bar;
    private ComboBox<String> combo;

    public CaricaCatologoService(URL url, int limit, int minYear, int maxYear, ProgressBar bar, ComboBox<String> combo) {
        this.url = url;
        this.limit = limit;
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.bar = bar;
        this.combo = combo;
    }

    @Override
    protected Task<ArrayList<Libro>> createTask() {
        return new Task<>(){
            @Override
            protected ArrayList<Libro> call() throws Exception {
                // TODO: Implementazione dei filtri.
                final Scanner scan = new Scanner(url.openStream());
                scan.nextLine();
                scan.useDelimiter("[\n;]");
                ArrayList<Libro> catalog = new ArrayList<>();
                while (scan.hasNext()) {
                    Libro l = new Libro();
                    l.setTipoVol(scan.next());
                    l.setGred(scan.next());
                    l.setIsbn(Long.parseLong(scan.next()));
                    l.setCodVol(Long.parseLong(scan.next()));
                    l.setTitolo(scan.next());
                    l.setAnnoPrintable(Integer.parseInt(scan.next()));
                    // I libri hanno cifre significative:
                    //  00 - 22 se negli anni 2000
                    //  23 - 99 se negli anni 19xx
                    if (l.getAnnoPrintable() <= 22)
                        l.setAnnoAcutual(2000 + l.getAnnoPrintable());
                    else
                        l.setAnnoAcutual(1900 + l.getAnnoPrintable());
                    l.setPrezzo(Double.parseDouble(scan.next()));
                    l.setPeso(Double.parseDouble(scan.next()));
                    l.setPagine(Integer.parseInt(scan.next()));
                    catalog.add(l);
                }
                return catalog;
            }
        };
    }
}
