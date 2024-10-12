import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class GUI {

    private int rader, kolonner;
    private Kontroll kontroll;
    private JFrame vindu;
    private JPanel hovedpanel, kommandopanel, rutepanel, styrepanel, tekstpanel;
    private JLabel lengdeLabel;
    private JButton opp, ned, venstre, hoyre, slutt;

    private final int DIM = 12;
    private JLabel labelBrett[][] = new JLabel[DIM][DIM];

    public GUI (int rader, int kolonner, Kontroll kontroll) {
        this.rader = rader;
        this.kolonner = kolonner;
        this.kontroll = kontroll;

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { System.exit(1); }

        vindu = new JFrame("SNAKE");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        hovedpanel = new JPanel();
        hovedpanel.setLayout(new BorderLayout());
        hovedpanel.setPreferredSize(new Dimension(400, 500));
        vindu.add(hovedpanel);

        kommandopanel = new JPanel();
        kommandopanel.setLayout(new BorderLayout());
        kommandopanel.setPreferredSize(new Dimension(400, 100));
        hovedpanel.add(kommandopanel, BorderLayout.NORTH);

        styrepanel = new JPanel();
        styrepanel.setLayout(new BorderLayout());
        styrepanel.setPreferredSize(new Dimension(200, 100));
        kommandopanel.add(styrepanel, BorderLayout.CENTER);

        tekstpanel = new JPanel();
        tekstpanel.setLayout(new BorderLayout());
        tekstpanel.setPreferredSize(new Dimension(100, 100));
        kommandopanel.add(tekstpanel, BorderLayout.WEST);

        lengdeLabel = new JLabel("   POENG: 0");
        lengdeLabel.setPreferredSize(new Dimension(100, 100));
        tekstpanel.add(lengdeLabel, BorderLayout.CENTER);

        slutt = new JButton( " Slutt ");

        class Avslutt implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                kontroll.avslutt();
            }
        }

        slutt.addActionListener(new Avslutt());
        slutt.setPreferredSize(new Dimension(100, 100));
        kommandopanel.add(slutt, BorderLayout.EAST);

        opp = new JButton(" Opp "); 
        class Opp implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                kontroll.oppdaterBevegelse("opp");
            }
        }
        opp.addActionListener(new Opp());

        ned = new JButton(" Ned ");
        class Ned implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                kontroll.oppdaterBevegelse("ned");
            }
        }
        ned.addActionListener(new Ned());

        venstre = new JButton(" Venstre "); 
        class Venstre implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                kontroll.oppdaterBevegelse("venstre");
            }
        }
        venstre.addActionListener(new Venstre());

        hoyre = new JButton(" Hoyre ");
        class Hoyre implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                kontroll.oppdaterBevegelse("hoyre");
            }
        }
        hoyre.addActionListener(new Hoyre());

        styrepanel.add(opp, BorderLayout.NORTH); styrepanel.add(ned, BorderLayout.SOUTH);
        styrepanel.add(venstre, BorderLayout.WEST); styrepanel.add(hoyre, BorderLayout.EAST);

        rutepanel = new JPanel();
        rutepanel.setLayout(new GridLayout(rader, kolonner));
        rutepanel.setPreferredSize(new Dimension(480, 480));
        hovedpanel.add(rutepanel, BorderLayout.SOUTH);

        for (int rad = 0; rad < DIM; rad++) {
            for (int kol = 0; kol < DIM; kol++) {
                JLabel rute = new JLabel("", SwingConstants.CENTER);
                rute.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                labelBrett[rad][kol] = rute;
                rute.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));

                rutepanel.add(rute);
            }
        }

        vindu.setSize(480, 610); vindu.setVisible(true); 
    }

    public Rute[][] tegnStartBrett() {
        Rute[][] brett = new Rute[DIM][DIM];

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                brett[i][j] = new TomRute(i, j);  
                labelBrett[i][j].setText("");         
            }
        }

        Slange nySlange = new Slange(5, 6);
        brett[5][6] = nySlange;
        nySlange.settHode();
        kontroll.leggTilSlange(nySlange);
        labelBrett[5][6].setBackground(Color.GREEN); labelBrett[5][6].setText("o");


        // kontroll.leggTilSkatter(10);

        int skattTeller = 0;
        int skattRad, skattKol;

        while (skattTeller < 10) {
            skattRad = Skatt.trekk(0, 11);
            skattKol = Skatt.trekk(0, 11);

            if (!(brett[skattRad][skattKol] instanceof Slange) && !(brett[skattRad][skattKol] instanceof Skatt)) {
                Skatt nySkatt = new Skatt(skattRad, skattKol);
                brett[skattRad][skattKol] = nySkatt;
                labelBrett[skattRad][skattKol].setText("$"); labelBrett[skattRad][skattKol].setForeground(Color.RED);
                skattTeller++;

                kontroll.leggTilSkatt(nySkatt);
            }
        }

        for (JLabel[] rad : labelBrett) {
            for (JLabel kol : rad) {
                kol.setOpaque(true);
            }
        }

        return brett;
    }

    public void oppdaterBrett(Rute[][] nyttBrett) {

        for (int rad = 0; rad < DIM; rad++) {
            for (int kol = 0; kol < DIM; kol++) {
                if (nyttBrett[rad][kol] instanceof Slange) {
                    settSlange(labelBrett[rad][kol], nyttBrett[rad][kol].erHode());
                }
                else if (nyttBrett[rad][kol] instanceof Skatt) {
                    settSkatt(labelBrett[rad][kol]);
                }
                else {
                    settTom(labelBrett[rad][kol]);
                }
            }
        }

        lengdeLabel.setText("   POENG: " + kontroll.hentPoeng() + "");

        System.out.println("oppdatert");

    }

    public void avsluttSpill() throws InterruptedException { 

        for (int rad = 0; rad < DIM; rad++) {
            Thread.sleep(200);
            for (int kol = 0; kol < DIM; kol++) {
                if (rad == 5) {
                    if (kol == 2) settBokstav(labelBrett[rad][kol], "G");
                    else if (kol == 3) settBokstav(labelBrett[rad][kol], "A");
                    else if (kol == 4) settBokstav(labelBrett[rad][kol], "M");
                    else if (kol == 5) settBokstav(labelBrett[rad][kol], "E");
                    else if (kol == 6) settBokstav(labelBrett[rad][kol], "O");
                    else if (kol == 7) settBokstav(labelBrett[rad][kol], "V");
                    else if (kol == 8) settBokstav(labelBrett[rad][kol], "E");
                    else if (kol == 9) settBokstav(labelBrett[rad][kol], "R");
                    else settSvart(labelBrett[rad][kol]);
                }
                else settSvart(labelBrett[rad][kol]);
                
            }
        }

        lengdeLabel.setText("   POENG: " + kontroll.hentPoeng() + " ");

    }

    public void avsluttSpill(Rute[][] sisteBrett) {

        // for (int rad = 0; rad < DIM; rad++) {
        //     for (int kol = 0; kol < DIM; kol++) {
        //         if (sisteBrett[rad][kol] instanceof Slange) {
        //             labelBrett[rad][kol].setBackground(Color.RED);
        //             labelBrett[rad][kol].setOpaque(true);
        //         }
        //     }
        // }

        // lengdeLabel.setText("   POENG: " + kontroll.hentPoeng() + " ");

        Koe<Slange> slange = kontroll.hentSlange();

        for (Slange bit : slange) {
            labelBrett[bit.hentRad()][bit.hentKol()].setBackground(Color.RED);
            labelBrett[bit.hentRad()][bit.hentKol()].setOpaque(true);
        }
    }

    private static void settSlange(JLabel rute, boolean erHode) {
        rute.setBackground(Color.GREEN); 
        rute.setForeground(Color.BLACK);
        if (erHode) rute.setText("o");
        if (!erHode) rute.setText("x");
        rute.setOpaque(true);
    }

    private static void settSkatt(JLabel rute) {
        rute.setForeground(Color.RED);
        rute.setBackground(Color.WHITE);
        rute.setText("$");
        rute.setOpaque(true);
    }

    private static void settTom(JLabel rute) {
        rute.setText("");
        rute.setForeground(Color.BLACK);
        rute.setBackground(Color.WHITE);
        rute.setOpaque(true);
    }

    private static void settSvart(JLabel rute) {
        rute.setText("");
        rute.setForeground(Color.BLACK);
        rute.setBackground(Color.BLACK);
        rute.setOpaque(true);
    }

    private static void settBokstav(JLabel rute, String bokstav) {
        rute.setText("" + bokstav);
        rute.setForeground(Color.BLACK);
        rute.setBackground(Color.WHITE);
        rute.setOpaque(true);
    }
    
    public void endreLengde() {

    }
}