package view;

import model.repository.*;
import presenter.MainPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class MainView extends JFrame {
    private ActorView actorView;
    private RegizorView regizorView;
    private ScenaristView scenaristView;
    private FilmView filmView;

    private MainPresenter mainPresenter;
    private Connection connection;

    public MainView(MainPresenter mainPresenter, Connection connection) {
        super("Sistem Management Producție Filme");
        this.mainPresenter = mainPresenter;
        this.connection = connection;
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                        System.out.println("Conexiunea la baza de date a fost închisă.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        JTabbedPane tabbedPane = new JTabbedPane();


        actorView = new ActorView(mainPresenter.getActorPresenter());
        regizorView = new RegizorView(mainPresenter.getRegizorPresenter());
        scenaristView = new ScenaristView(mainPresenter.getScenaristPresenter());
        filmView = new FilmView(mainPresenter.getFilmPresenter());

        tabbedPane.addTab("Actori", actorView);
        tabbedPane.addTab("Regizori", regizorView);
        tabbedPane.addTab("Scenariști", scenaristView);
        tabbedPane.addTab("Filme", filmView);


        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            switch (selectedIndex) {
                case 0: actorView.incarcaActori(); break;
                case 1: regizorView.incarcaRegizori(); break;
                case 2: scenaristView.incarcaScenaristi(); break;
                case 3: filmView.incarcaFilme(); break;
            }
        });

        add(tabbedPane);

        // Meniu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFisier = new JMenu("Fișier");
        JMenuItem itemIesire = new JMenuItem("Ieșire");
        itemIesire.addActionListener(e -> {
            // Declanșăm evenimentul de închidere pentru a rula logica de stop connection
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            System.exit(0);
        });
        menuFisier.add(itemIesire);

        JMenu menuAjutor = new JMenu("Ajutor");
        JMenuItem itemDespre = new JMenuItem("Despre");
        itemDespre.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Sistem Management Producție Filme\n" +
                            "Versiune 1.0\nAplicație pentru gestionarea filmelor.",
                    "Despre", JOptionPane.INFORMATION_MESSAGE);
        });
        menuAjutor.add(itemDespre);

        menuBar.add(menuFisier);
        menuBar.add(menuAjutor);
        setJMenuBar(menuBar);
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            try {
                DatabaseConfig dbConfig = DatabaseManager.getConnectionWrapper(false);
                Connection connection = dbConfig.getConnection();

                if (connection == null) {
                    JOptionPane.showMessageDialog(null, "Eroare Conexiune DB!", "Eroare", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                FilmRepository filmRepo = new FilmRepository(connection);
                ActorRepository actorRepo = new ActorRepository(connection);
                RegizorRepository regizorRepo = new RegizorRepository(connection);
                ScenaristRepository scenaristRepo = new ScenaristRepository(connection);

                MainPresenter mainPresenter = new MainPresenter(filmRepo, actorRepo, regizorRepo, scenaristRepo);

                MainView mainView = new MainView(mainPresenter, connection);
                mainView.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}