public class Modell {
    private GUI gui;
    private Kontroll kontroller;
    private static Koe<Slange> slange = new Koe<>();
    private Rute[][] brett = new Rute[12][12];
    private Skatt[] skatter = new Skatt[10];
    private boolean kjor;
    
    public Modell(GUI gui, Kontroll kontroller) {
        this.gui = gui;
        this.kontroller = kontroller;
    }

    public void startSpill() throws InterruptedException {
        kjor = true;
        this.brett = kontroller.tegnStartBrett();
        kontroller.spillLøkke();
    }

    public void oppdaterBrett(Rute[][] nyttBrett) {
        this.brett = nyttBrett;
    }

    public Rute[][] hentBrett() {
        return brett;
    }

    public void beveg(String retning) throws InterruptedException { // må endres slik at den fjerner/legger til skatter i skattelisten (bruke fjernSkatt og leggTilSkatt)
        Slange hode = slange.hent(0);

        switch (retning) {
            case "opp":

                if (hode.hentRad() - 1 < 0) { // treffer en vegg
                    kjor = false;
                    kontroller.avsluttSpill(brett);
                }
                else if (brett[hode.hentRad() - 1][hode.hentKol()] instanceof Slange) { // treffer en slangerute
                    kjor = false;
                    kontroller.avsluttSpill(brett);
                }
                else if (brett[hode.hentRad() - 1][hode.hentKol()] instanceof Skatt){ // treffer en skatt
                    Slange nyttHode = new Slange(hode.hentRad() - 1, hode.hentKol());
                    this.fjernSkatt((Skatt)brett[hode.hentRad() - 1][hode.hentKol()]);
                    
                    nyttHode.settHode();
                    hode.fjernHode();
                    slange.leggTil(nyttHode);
                    this.leggTilSkatter(1);

                    brett[nyttHode.hentRad()][nyttHode.hentKol()] = nyttHode;
                }
                else { // treffer en tom rute
                    Slange nyttHode = new Slange(hode.hentRad() - 1, hode.hentKol());
                    TomRute skalFjernes = new TomRute(slange.hent(hentHaleLengde()).hentRad(), slange.hent(hentHaleLengde()).hentKol());
                    nyttHode.settHode();
                    hode.fjernHode();
                    slange.leggTil(nyttHode);
                    slange.fjern();

                    brett[nyttHode.hentRad()][nyttHode.hentKol()] = nyttHode;
                    brett[skalFjernes.hentRad()][skalFjernes.hentKol()] = skalFjernes;       
                }

                break;
            case "ned":
                
                if (hode.hentRad() + 1 > 11) { // treffer en vegg
                    kjor = false;
                    // kontroller.avsluttSpill(brett);
                }
                else if (brett[hode.hentRad() + 1][hode.hentKol()] instanceof Slange) { // treffer en slangerute
                    kjor = false;
                    // kontroller.avsluttSpill(brett);
                }
                else if (brett[hode.hentRad() + 1][hode.hentKol()] instanceof Skatt){ // treffer en skatt
                    Slange nyttHode = new Slange(hode.hentRad() + 1, hode.hentKol());
                    this.fjernSkatt((Skatt)brett[hode.hentRad() + 1][hode.hentKol()]);

                    nyttHode.settHode();
                    hode.fjernHode();
                    slange.leggTil(nyttHode);
                    this.leggTilSkatter(1);

                    brett[nyttHode.hentRad()][nyttHode.hentKol()] = nyttHode;
                }
                else { // treffer en tom rute
                    Slange nyttHode = new Slange(hode.hentRad() + 1, hode.hentKol());
                    TomRute skalFjernes = new TomRute(slange.hent(hentHaleLengde()).hentRad(), slange.hent(hentHaleLengde()).hentKol());
                    nyttHode.settHode();
                    hode.fjernHode();
                    slange.leggTil(nyttHode);
                    slange.fjern();

                    brett[nyttHode.hentRad()][nyttHode.hentKol()] = nyttHode;
                    brett[skalFjernes.hentRad()][skalFjernes.hentKol()] = skalFjernes;       
                }

                break;
            case "venstre":
                
                if (hode.hentKol() - 1 < 0) { // treffer en vegg
                    kjor = false;
                    kontroller.avsluttSpill(brett);
                }
                else if (brett[hode.hentRad()][hode.hentKol() - 1] instanceof Slange) { // treffer en slangerute
                    kjor = false;
                    kontroller.avsluttSpill(brett);
                }
                else if (brett[hode.hentRad()][hode.hentKol() - 1] instanceof Skatt){ // treffer en skatt
                    Slange nyttHode = new Slange(hode.hentRad(), hode.hentKol() - 1);
                    this.fjernSkatt((Skatt)brett[hode.hentRad()][hode.hentKol() - 1]);

                    nyttHode.settHode();
                    slange.leggTil(nyttHode);
                    hode.fjernHode();
                    this.leggTilSkatter(1);

                    brett[nyttHode.hentRad()][nyttHode.hentKol()] = nyttHode;
                }
                else { // treffer en tom rute
                    Slange nyttHode = new Slange(hode.hentRad(), hode.hentKol() - 1);
                    TomRute skalFjernes = new TomRute(slange.hent(hentHaleLengde()).hentRad(), slange.hent(hentHaleLengde()).hentKol());
                    nyttHode.settHode();
                    hode.fjernHode();
                    slange.leggTil(nyttHode);
                    slange.fjern();

                    brett[nyttHode.hentRad()][nyttHode.hentKol()] = nyttHode;
                    brett[skalFjernes.hentRad()][skalFjernes.hentKol()] = skalFjernes;       
                }

                break;
            case "hoyre":
                
                if (hode.hentKol() + 1 > 11) { // treffer en vegg ENDRE + case ned
                    kjor = false;
                    kontroller.avsluttSpill(brett);
                }
                else if (brett[hode.hentRad()][hode.hentKol() + 1] instanceof Slange) { // treffer en slangerute
                    kjor = false;
                    kontroller.avsluttSpill(brett);
                }
                else if (brett[hode.hentRad()][hode.hentKol() + 1] instanceof Skatt){ // treffer en skatt
                    Slange nyttHode = new Slange(hode.hentRad(), hode.hentKol() + 1);
                    this.fjernSkatt((Skatt)brett[hode.hentRad()][hode.hentKol() + 1]);

                    nyttHode.settHode();
                    slange.leggTil(nyttHode);
                    hode.fjernHode();
                    this.leggTilSkatter(1);

                    brett[nyttHode.hentRad()][nyttHode.hentKol()] = nyttHode;
                }
                else { // treffer en tom rute
                    Slange nyttHode = new Slange(hode.hentRad(), hode.hentKol() + 1);
                    TomRute skalFjernes = new TomRute(slange.hent(hentHaleLengde()).hentRad(), slange.hent(hentHaleLengde()).hentKol());
                    nyttHode.settHode();
                    hode.fjernHode();
                    slange.leggTil(nyttHode);
                    slange.fjern();

                    brett[nyttHode.hentRad()][nyttHode.hentKol()] = nyttHode;
                    brett[skalFjernes.hentRad()][skalFjernes.hentKol()] = skalFjernes;       
                }

                break;
        }
    }

    public void leggTilSkatt(Skatt skatt) {
        for (Skatt s : skatter) {
            if (s == null) {
                s = skatt;
            }
        }
    }

    public void leggTilSkatter(int antall) {
        int skattTeller = 0;
        int skattRad, skattKol;

        while (skattTeller < antall) {
            skattRad = Skatt.trekk(0, 11);
            skattKol = Skatt.trekk(0, 11);

            if (!(brett[skattRad][skattKol] instanceof Slange)) {
                Skatt nySkatt = new Skatt(skattRad, skattKol);
                brett[skattRad][skattKol] = nySkatt;
                this.leggTilSkatt(nySkatt);
                skattTeller++;
            }
        }
    }

    public void fjernSkatt(Skatt skatt) {
        for (Skatt s : skatter) {
            if (s == skatt) {
                s = null;
            }
        }
    }

    public void leggTilSlange(Slange s) {
        slange.leggTil(s);
    }

    public void forkortSlange() {
        slange.fjern();
    }

    public int hentLengde() {
        return slange.storrelse();
    }

    public Koe<Slange> hentSlange() {
        return slange;
    }

    public Skatt[] hentSkatter() {
        return skatter;
    }

    public static int hentHaleLengde() {
        return slange.storrelse() - 1;
    }

    public boolean hentKjor() {
        return kjor;
    }
}
