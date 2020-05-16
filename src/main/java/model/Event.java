package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Event implements Comparable<Event> {

    private int id;
    private String name;
    private String date;
    private static final int MAXCAPACITY = 20;

    public Event(int id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static int getMAXCAPACITY() {
        return Event.MAXCAPACITY;
    }

    @Override
    public String toString() {
        return name + " " + date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Event other) {
        SimpleDateFormat sdformat = new SimpleDateFormat("dd/mm/yyyy");
        try {
            Date d1 = sdformat.parse(this.date);
            Date d2 = sdformat.parse(other.date);
            return d1.compareTo(d2);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
