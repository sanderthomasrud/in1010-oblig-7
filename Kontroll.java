public class Kontroll {
    private GUI gui;
    private Modell modell;
    private boolean garOpp = false;
    private boolean garNed = false;
    private boolean garHoyre = false;
    private boolean garVenstre = false;

    Kontroll(int rader, int kolonner){
        gui = new GUI(rader, kolonner, this);
        modell = new Modell(gui, this);
    }

    public void startSpill() throws InterruptedException {
        modell.startSpill();
    }

    public void spillLøkke() throws InterruptedException {
        while (modell.hentKjor()) {

            beveg();
            gui.oppdaterBrett(modell.hentBrett());

            Thread.sleep(1000);
        }

        gui.avsluttSpill();
    }

    public void avsluttSpill(Rute[][] sisteBrett) throws InterruptedException {
        gui.avsluttSpill();
    }

    void avslutt() {
        System.exit(0);
    }

    public void beveg() throws InterruptedException {
        if (garOpp) {
            this.beveg("opp");
        }
        if (garNed) {
            this.beveg("ned");
        }
        if (garVenstre) {
            this.beveg("venstre");
        }
        if (garHoyre) {
            this.beveg("hoyre");
        }
    }

    public Rute[][] tegnStartBrett() {
        return gui.tegnStartBrett();
    }

    public void tegnSpillBrett(Rute[][] nyttBrett) {
        modell.oppdaterBrett(nyttBrett);
    }

    public int hentLengde() {
        return modell.hentLengde();
    }

    public void leggTilSkatter(int antall) {
        modell.leggTilSkatter(antall);
    }

    public void leggTilSkatt(Skatt skatt) {
        modell.leggTilSkatt(skatt);
    }

    public void fjernSkatt(Skatt skatt) {
        modell.fjernSkatt(skatt);
    }

    public void leggTilSlange(Slange slange) {
        modell.leggTilSlange(slange);
    }

    public void forkortSlagne() {
        modell.forkortSlange();
    }

    public void beveg(String retning) throws InterruptedException {
        switch (retning) {
            case "opp":
                modell.beveg("opp");
                break;
            case "ned":
                modell.beveg("ned");
                break;
            case "venstre":
                modell.beveg("venstre");
                break;
            case "hoyre":
                modell.beveg("hoyre");
                break;
        }
    }

    public void oppdaterBevegelse(String retning) {
        switch (retning) {
            case "opp":
                if (garNed) return;
                garOpp = true;
                garHoyre = false;
                garNed = false;
                garVenstre = false;
                break;
            case "hoyre":
                if (garVenstre) return;
                garOpp = false;
                garHoyre = true;
                garNed = false;
                garVenstre = false;
                break;
            case "ned":
                if (garOpp) return;
                garOpp = false;
                garHoyre = false;
                garNed = true;
                garVenstre = false;
                break;
            case "venstre":
                if (garHoyre) return;
                garOpp = false;
                garHoyre = false;
                garNed = false;
                garVenstre = true;
                break;
        }
    }

    public Koe<Slange> hentSlange() {
        return modell.hentSlange();
    }

    public Skatt[] hentSkatter() {
        return modell.hentSkatter();
    }

    public int hentPoeng() {
        return modell.hentHaleLengde();
    }
    
}
