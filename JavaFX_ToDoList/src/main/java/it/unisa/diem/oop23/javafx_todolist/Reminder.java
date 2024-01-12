package it.unisa.diem.oop23.javafx_todolist;

import java.io.Serializable;
import java.time.LocalDate;

public class Reminder implements Serializable, Comparable<Reminder> {
    private LocalDate date;
    private String event;
    private final long id;
    static long currentID;

    public Reminder() {
        this.id = currentID++;
    }

    public Reminder(LocalDate date, String event) {
        this.date = date;
        this.event = event;
        this.id = currentID++;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public long getId() {
        return id;
    }

    @Override
    public int compareTo(Reminder o) {
        if(this.date != o.getDate())
            return this.date.compareTo(o.getDate());
        else{
            return this.id < o.getId() ? -1 : 1;
        }
    }
}
