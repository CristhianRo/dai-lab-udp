package dai;

import com.google.gson.annotations.Expose;

public class Musician {
    @Expose
    public String uuid;
    @Expose
    public Instrument instrument;
    public String sound;
    @Expose
    public long lastActivity;

    public Musician(String uuid, String sound) {
        this.uuid = uuid;
        this.sound = sound;
        this.instrument = Instrument.instrumentFromSound(sound);
    }

    public void assigneIntrument() {
        this.instrument = Instrument.instrumentFromSound(this.sound);
    }
}
