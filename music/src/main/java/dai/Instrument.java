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
}
