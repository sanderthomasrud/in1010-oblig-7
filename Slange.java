
public class Slange extends Rute{
    private boolean erHode;

    public Slange(int rad, int kolonne) {
        super(rad, kolonne);
    }

    @Override
    public boolean erHode() {
        return erHode;
    }

    public void settHode() {
        erHode = true;
    }

    public void fjernHode() {
        erHode = false;
    }
}
