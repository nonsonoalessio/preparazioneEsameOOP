package com.example.esameintracorso;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Contact implements Serializable {
    private String firstName;
    private String lastName;
    private long phoneNumber;

    public Contact() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void writeContacts(ArrayList<Contact> l, File f) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f.getAbsolutePath())));
        oos.writeObject(l);
        oos.close();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Contact> readContacts(File f) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f.getAbsolutePath())));
        ArrayList<Contact> l = (ArrayList<Contact>) ois.readObject();
        ois.close();
        return l;
    }

    @Override
    public String toString() {
        return firstName + ";" + lastName + ";" + phoneNumber + ";";
    }
}
