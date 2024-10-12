abstract class Rute {
    private int rad, kolonne;

    public Rute (int rad, int kolonne) {
        this.rad = rad;
        this.kolonne = kolonne;
    }

    public int hentRad() {
        return rad;
    }

    public int hentKol() {
        return kolonne;
    }

    public void endreRad(int rad) {
        this.rad = rad;
    }

    public void endreKol(int kol) {
        this.kolonne = kol;
    }

    public boolean erHode() {
        return false;
    }

}
