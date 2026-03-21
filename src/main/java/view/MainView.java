package view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private ActorView actorView;
    private RegizorView regizorView;
    private ScenaristView scenaristView;
    private FilmView filmView;

    public MainView() {
        super("Sistem Management Producție Filme");
                initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Creare tab pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Taburi pentru fiecare sectiune
        actorView = new ActorView();
        regizorView = new RegizorView();
        scenaristView = new ScenaristView();
        filmView = new FilmView();

        tabbedPane.addTab("Actori", actorView);
                tabbedPane.addTab("Regizori", regizorView);
                        tabbedPane.addTab("Scenariști", scenaristView);
                                tabbedPane.addTab("Filme", filmView);

                                        // Listener pentru refresh la schimbarea tab-ului
                                        tabbedPane.addChangeListener(e -> {
                                            int selectedIndex = tabbedPane.getSelectedIndex();
                                            switch (selectedIndex) {
                                                case 0:
                                                    actorView.incarcaActori();
                                                    break;
                                                case 1:
                                                    regizorView.incarcaRegizori();
                                                    break;
                                                case 2:
                                                    scenaristView.incarcaScenaristi();
                                                    break;
                                                case 3:
                                                    filmView.incarcaFilme();
                                                    break;
                                            }
                                        });

        add(tabbedPane);

        // Meniu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFisier = new JMenu("Fișier");
                JMenuItem itemIesire = new JMenuItem("Ieșire");
                itemIesire.addActionListener(e -> System.exit(0));
        menuFisier.add(itemIesire);

        JMenu menuAjutor = new JMenu("Ajutor");
                JMenuItem itemDespre = new JMenuItem("Despre");
                itemDespre.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this,
                            "Sistem Management Producție Filme " +
                            "Versiune 1.0 " +
                            "Aplicație pentru gestionarea filmelor, " +
                            "actorilor, regizorilor și scenariștilor.",
                            "Despre",
                            JOptionPane.INFORMATION_MESSAGE);
                });
        menuAjutor.add(itemDespre);

        menuBar.add(menuFisier);
        menuBar.add(menuAjutor);
        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.setVisible(true);
        });
    }
}
