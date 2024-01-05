package com.example.esameintracorso;

import java.io.*;
import java.util.ArrayList;

/**
 * La classe Contact consente la rappresentazione di un contatto mediante gli attributi nome, cognome, numero di telefono.<br>
 * Fornisce i metodi getter e setter per gli attributi, un costruttore vuoto (default) e uno che inizializza tutti gli attributi,
 * più l'override delle funzioni:<br>
 * {@link Object#toString()} per ottenere una rappresentazione CSV-formatted del contatto;<br>
 * {@link Object#hashCode()} per ottenere un hash code del contatto;<br>
 * {@link Object#equals(Object)} per controllare l'uguaglianza tra un contatto e un oggetto.
 * @author Giura Alessio Donato
 */
public class Contact implements Serializable {
    private String firstName;
    private String lastName;
    private String phoneNumber;

    /**
     * Consente di creare un nuovo contatto, inizializzando i campi con {@link String} vuote.
     */
    public Contact() {
        this.firstName = "";
        this.lastName = "";
        this.phoneNumber = "";
    }

    /**
    * Consente di creare un nuovo contatto, inizializzando i campi con il valore dei parametri forniti.
     * @param firstName il nome del contatto;
     * @param lastName il cognome del contatto;
     * @param phoneNumber il numero di telefono del contatto.
     * */
    public Contact(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Consente di ottenere il nome del contatto.
     * @return il nome del contatto.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Consente di impostare il nome del contatto.
     * @param firstName il nome del contatto.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Consente di ottenere il cognome del contatto.
     * @return il cognome del contatto.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Consente di impostare il cognome del contatto.
     * @param lastName il cognome del contatto.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Consente di ottenere il numero di telefono del contatto.<br>
     * La rappresentazione in forma di {@link String} consente di semplificare l'implementazione della tabella
     * e permette un'ipotetica memorizzazione del segno "+" per inserire il prefisso internazionale.
     * Nota: nonostante si scelga {@link String} per la rappresentazione, il numero di telefono deve essere compatibile col tipo
     * {@link Long}.
     * @return il numero di telefono.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Consente di impostare il numero di telefono del contatto.<br>
     * La rappresentazione in forma di {@link String} consente di semplificare l'implementazione della tabella
     * e permette un'ipotetica memorizzazione del segno "+" per inserire il prefisso internazionale.<br>
     * NOTA: nonostante si scelga {@link String} per la rappresentazione, il numero di telefono deve essere compatibile col tipo
     * {@link Long}.
     * @param phoneNumber il numero di telefono.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Funzione di utilità che consente la scrittura su file binario di una lista di contatti.<br>
     * La funzione prevede il salvataggio del file "saved.bin" nella directory <code>Home<code/> dell'utente.
     * @param l la lista di contatti da scrivere
     * @throws IOException in caso di errore di scrittura.
     */
    public static void writeContacts(ArrayList<Contact> l) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(System.getProperty("user.home") + "/saved.bin"))));
        oos.writeObject(l);
        oos.close();
    }

    /**
     * Funzione di utilità che consente la lettura da file binario di una lista di contatti.<br>
     * La funzione prevede la lettura del file "saved.bin" nella directory <code>Home<code/> dell'utente.<br>
     * NOTA: funzione non utilizzata.
     * @throws IOException in caso di errore di scrittura;
     * @throws ClassNotFoundException nel caso in cui il file binario non contiene una lista di contatti.
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Contact> readContacts() throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(System.getProperty("user.home") + "/saved.bin")));
        ArrayList<Contact> l = (ArrayList<Contact>) ois.readObject();
        ois.close();
        return l;
    }

    /**
     * Consente di ottenere la rappresentazione del contatto mediante {@link String} formattata per CSV.
     * @return la rappresentazione del contatto in forma <code>firstName;lastName;phoneNumber;</code>
     */
    @Override
    public String toString() {
        return firstName + ";" + lastName + ";" + phoneNumber + ";";
    }

    /**
     * Calcola l'hash code di un contatto.
     * @return hash code del contatto.
     */
    @Override
    public int hashCode() {
        int result = 17; // Scelta di un numero primo iniziale
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    /**
     * Stabilisce se un oggetto di tipo Contatto è uguale a un altro oggetto, fornito come parametro.
     * @param obj l'oggetto da confrontare.
     * @return true se i due oggetti sono uguali, false altrimenti.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj.getClass().equals(this.getClass())){
            if(this.firstName.equals(((Contact) obj).getFirstName())){
                if(this.lastName.equals(((Contact) obj).getLastName())){
                    return this.phoneNumber.equals(((Contact) obj).getPhoneNumber());
                }
            }
        }
        return false;
    }
}
