package dai;

import com.google.gson.annotations.Expose;

public class Musician {
    @Expose
    public String uuid;
    @Expose
    public Instrument instrument;
    public String sound;
    @Expose
    public  long activeSince;

    public Musician(String uuid, String sound, long activeSince) {
        this.uuid = uuid;
        this.sound = sound;
        this.instrument = Instrument.instrumentFromSound(sound);
        this.activeSince = activeSince;
    }
}
