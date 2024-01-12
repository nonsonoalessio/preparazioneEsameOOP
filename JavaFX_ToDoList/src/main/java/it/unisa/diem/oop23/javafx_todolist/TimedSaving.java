package it.unisa.diem.oop23.javafx_todolist;

import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class TimedSaving implements Runnable {
    final ObservableList<Reminder> list;

    public TimedSaving(ObservableList<Reminder> list) {
        this.list = list;
    }

    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement"})
    @Override
    public void run() {
        ArrayList<Reminder> l;
        while(true) {
            synchronized(list) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("save.bin")))) {
                    l = new ArrayList<>(list);
                    oos.writeObject(l);
                    list.notifyAll();
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException exc) {
                        System.out.println("Interrupted saving service.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
