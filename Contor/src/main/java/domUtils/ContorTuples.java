package domUtils;

/**
 * Created by C311939 on 03.08.2016.
 */
public class ContorTuples {
    private String room;

    private int cold;

    private int warm;

    public ContorTuples(String room, int cold, int warm) {
        this.room = room;
        this.cold = cold;
        this.warm = warm;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public String toString() {
        return "Room: " + this.getRoom() + " Cold: " + this.getCold() + " Warm:" + this.getWarm();
    }
}
