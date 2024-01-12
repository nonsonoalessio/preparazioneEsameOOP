package it.unisa.diem.oop23.javafx_libri;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class CaricaCatologoService extends Service<Void> {

    private final URL url;
    private final int limit;
    private final int minYear;
    private final int maxYear;
    private final ObservableList<String> combo;
    private ComboBox<String> c;
    private final ObservableList<Libro> list;
    private final String category;
    private int count;

    public CaricaCatologoService(URL url, int limit, int minYear, int maxYear, ComboBox<String> combo, String category) {
        this.url = url;
        this.limit = limit;
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.combo = FXCollections.observableArrayList();
        this.c = combo;
        this.list = FXCollections.observableArrayList();
        this.category = category;
        this.count = 0;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>(){
            @Override
            protected Void call() throws Exception {
                System.out.println("Retrieving catalog...");
                final Scanner scan = new Scanner(url.openStream());
                System.out.println("Opened stream. Now compiling list...");
                scan.nextLine();
                scan.useDelimiter("[\n;]");
                while (scan.hasNext()) {
                    Libro l = new Libro();
                    l.setTipoVol(scan.next());
                    l.setGred(scan.next());
                    l.setIsbn(Long.parseLong(scan.next().replaceAll(",", "")));
                    l.setCodVol(Long.parseLong(scan.next().replaceAll(",", "")));
                    l.setTitolo(scan.next());
                    l.setAnnoPrintable(Integer.parseInt(scan.next().replaceAll(",", "")));
                    // I libri hanno cifre significative:
                    //  00 - 22 se negli anni 2000
                    //  23 - 99 se negli anni 19xx
                    if (l.getAnnoPrintable() <= 22)
                        l.setAnnoAcutual(2000 + l.getAnnoPrintable());
                    else
                        l.setAnnoAcutual(1900 + l.getAnnoPrintable());
                    l.setPrezzo(Double.parseDouble(scan.next().replaceAll(",", "")));
                    l.setPeso(Double.parseDouble(scan.next().replaceAll(",", "")));
                    l.setPagine(Integer.parseInt(scan.next().replaceAll(",", "")));

                    // System.out.println("Retrieved: \n" + l);

                    if(category.isEmpty()) {
                        if (limit == -1) {
                            list.add(l);
                            count++;
                            createComboBox(l);
                        } else {
                            if (isValidForSelection(l)) {
                                System.out.println("Added " + l + "to list.");
                                list.add(l);
                                count++;
                                createComboBox(l);
                            }
                        }
                    } else {
                        if (limit == -1) {
                            if(category.equals(l.getTipoVol())) {
                                list.add(l);
                                count++;
                                createComboBox(l);
                            }
                        } else {
                            if (isValidForSelection(l)) {
                                if(category.equals(l.getTipoVol())) {
                                    list.add(l);
                                    count++;
                                    createComboBox(l);
                                }
                            }
                        }
                    }

                    if(limit != -1)
                        updateProgress(list.size(), limit);

                }
                Platform.runLater(() -> c = new ComboBox<>(combo));
                succeeded();
                return null;
            }
        };
    }


    private void createComboBox(Libro l){
        if (!combo.contains(l.getTipoVol())) {
            combo.add(l.getTipoVol());
            System.out.println("Added " + l.getTipoVol() + " to combo");
        }
    }

    private boolean isValidForSelection(Libro l){
        if(l.getAnnoAcutual() >= minYear){
            if(l.getAnnoAcutual() <= maxYear){
                return l.getTipoVol().equals(category);
            }
        }
        return false;
    }

    /*public ComboBox<String> getCombo() {
        return graphicalCombo;
    }*/

    public ObservableList<Libro> getList() {
        return list;
    }

    @Override
    protected void succeeded() {
        System.out.println("Imported " + count + " volumes.");
        super.succeeded();
    }
}
