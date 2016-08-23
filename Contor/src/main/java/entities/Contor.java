package entities;

import javax.inject.Named;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by C311939 on 02.08.2016.
 */
@Entity
public class Contor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String room;

    private int cold;

    private int warm;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date time;

    public Contor(String room, int cold, int warm) {
        this.room = room;
        this.cold = cold;
        this.warm = warm;
    }

    public Contor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCold() {
        return cold;
    }

    public void setCold(int cold) {
        this.cold = cold;
    }

    public int getWarm() {
        return warm;
    }

    public void setWarm(int warm) {
        this.warm = warm;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String toString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getTime());


        return "Camera: " + this.getRoom() + " Rece: " + this.getCold() + " Calda: " + this.getWarm() + " Time" + cal.get(Calendar.MONTH) + " " + cal.get(Calendar.YEAR) + "\n";
    }
}
