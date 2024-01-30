package dai;

public enum Instrument {
    piano("ti-ta-ti"),
    trumpet("pouet"),
    flute("trulu"),
    violin("gzi-gzi"),
    drum("boum-boum");

    private final String sound;

    Instrument(String sound) {
        this.sound = sound;
    }

    public String sound() {
        return this.sound;
    }

    public static Instrument instrumentFromSound(String sound) {
        for (Instrument inst : Instrument.values()) {
            if (inst.sound.equalsIgnoreCase(sound)) {
                return inst;
            }
        }
        return null;
    }
}
