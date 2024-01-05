package it.unisa.diem.oop23.javafx_libri;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Libro {
    private String tipoVol;
    private String gred;
    private long isbn;
    private long codVol;
    private String titolo;
    private int annoPrintable;
    private int annoAcutual;
    private double prezzo;
    private double peso;
    private int pagine;

    public Libro() {}

    public String getTipoVol() {
        return tipoVol;
    }

    public void setTipoVol(String tipoVol) {
        this.tipoVol = tipoVol;
    }

    public String getGred() {
        return gred;
    }

    public void setGred(String gred) {
        this.gred = gred;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public long getCodVol() {
        return codVol;
    }

    public void setCodVol(long codVol) {
        this.codVol = codVol;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getAnnoPrintable() {
        return annoPrintable;
    }

    public void setAnnoPrintable(int annoPrintable) {
        this.annoPrintable = annoPrintable;
    }

    public int getAnnoAcutual() {
        return annoAcutual;
    }

    public void setAnnoAcutual(int annoAcutual) {
        this.annoAcutual = annoAcutual;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getPagine() {
        return pagine;
    }

    public void setPagine(int pagine) {
        this.pagine = pagine;
    }

    @Override
    public String toString() {
        return isbn + ";" + titolo + ";" + annoPrintable + ";" + prezzo;
    }

    @Override
    public boolean equals(Object obj) {
        if(!this.getClass().equals(obj.getClass()))
            return false;
        else if (this == obj)
            return true;
        else
            return this.getIsbn() == ((Libro) obj).getIsbn();
    }
}
