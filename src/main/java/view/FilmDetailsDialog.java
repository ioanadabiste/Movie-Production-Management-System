package view;

import model.*;
import presenter.FilmPresenter;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class FilmDetailsDialog extends JDialog {
    private Film film;
    private FilmPresenter presenter;

    public FilmDetailsDialog(Frame parent, Film film, FilmPresenter presenter) {
        super(parent, "Detalii Film - " + film.getTitlu(), true);
        this.film = film;
        this.presenter = presenter;

        initializeUI();
        setSize(800, 600);
        setLocationRelativeTo(parent);
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Titlu
        JLabel lblTitlu = new JLabel(film.getTitlu());
        lblTitlu.setFont(new Font("Arial", Font.BOLD, 24));
                lblTitlu.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblTitlu);
        panelPrincipal.add(Box.createVerticalStrut(10));

        // Informatii de baza
        JPanel panelInfo = new JPanel(new GridLayout(0, 2, 10, 5));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Informații Generale"));

                panelInfo.add(new JLabel("An Realizare:"));
                        panelInfo.add(new JLabel(String.valueOf(film.getAnRealizare())));

        panelInfo.add(new JLabel("Tip Film:"));
                panelInfo.add(new JLabel(film.getTipFilm().toString()));

        panelInfo.add(new JLabel("Categorie:"));
                panelInfo.add(new JLabel(film.getCategorieFilm().toString()));

        // Regizor
        panelInfo.add(new JLabel("Regizor:"));
                String numeRegizor = "";
        if (film.getRegizorId() != null) {
            Regizor regizor = presenter.getRegizorById(film.getRegizorId());
            if (regizor != null) {
                numeRegizor = regizor.getNumeComplet() + " (n. " + regizor.getAnNastere() + ", " + regizor.getNationalitate() + ")";
            }
        }
        panelInfo.add(new JLabel(numeRegizor));

        // Scenarist
        panelInfo.add(new JLabel("Scenarist:"));
                String numeScenarist ="";
        if (film.getScenaristId() != null) {
            Scenarist scenarist = presenter.getScenaristById(film.getScenaristId());
            if (scenarist != null) {
                numeScenarist = scenarist.getNumeComplet() + " (n. " + scenarist.getAnNastere() + ", " + scenarist.getNationalitate() + ")";
            }
        }
        panelInfo.add(new JLabel(numeScenarist));

        panelPrincipal.add(panelInfo);
        panelPrincipal.add(Box.createVerticalStrut(10));

        // Descriere
        if (film.getDescriere() != null && !film.getDescriere().isEmpty()) {
            JPanel panelDescriere = new JPanel(new BorderLayout());
            panelDescriere.setBorder(BorderFactory.createTitledBorder("Descriere"));
                    JTextArea txtDescriere = new JTextArea(film.getDescriere());
            txtDescriere.setEditable(false);
            txtDescriere.setLineWrap(true);
            txtDescriere.setWrapStyleWord(true);
            txtDescriere.setRows(3);
            JScrollPane scrollDescriere = new JScrollPane(txtDescriere);
            panelDescriere.add(scrollDescriere, BorderLayout.CENTER);
            panelPrincipal.add(panelDescriere);
            panelPrincipal.add(Box.createVerticalStrut(10));
        }

        // Lista actori
        JPanel panelActori = new JPanel(new BorderLayout());
        panelActori.setBorder(BorderFactory.createTitledBorder("Distribuție Actori"));

                StringBuilder listaActori = new StringBuilder("<html>");
        if (!film.getActorIds().isEmpty()) {
            for (String actorId : film.getActorIds()) {
                Actor actor = presenter.getActorById(actorId);
                if (actor != null) {
                    listaActori.append("• ").append(actor.getNumeComplet())
                            .append(" (n. ").append(actor.getAnNastere())
                                    .append(", ").append(actor.getNationalitate())
                                            .append(")<br>");
                }
            }
        } else {
            listaActori.append("Niciun actor adăugat");
        }
        listaActori.append("</html>");

                JLabel lblActori = new JLabel(listaActori.toString());
        panelActori.add(lblActori, BorderLayout.CENTER);
        panelPrincipal.add(panelActori);
        panelPrincipal.add(Box.createVerticalStrut(10));

        // Imagini
        if (!film.getCaiImagini().isEmpty()) {
            JPanel panelImagini = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panelImagini.setBorder(BorderFactory.createTitledBorder("Imagini din Film"));

            for (String caleImagine : film.getCaiImagini()) {
                try {
                    File fisierImagine = new File(caleImagine);
                    if (fisierImagine.exists()) {
                        Image img = ImageIO.read(fisierImagine);
                        if (img != null) {
                            Image imgScalat = img.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                            JLabel lblImagine = new JLabel(new ImageIcon(imgScalat));
                            lblImagine.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                            panelImagini.add(lblImagine);
                        }
                    } else {
                        JLabel lblEroare = new JLabel("Imagine lipsă");
                                lblEroare.setPreferredSize(new Dimension(200, 150));
                        lblEroare.setHorizontalAlignment(SwingConstants.CENTER);
                        lblEroare.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                        panelImagini.add(lblEroare);
                    }
                } catch (Exception e) {
                    JLabel lblEroare = new JLabel("Eroare încărcare imagine");
                            lblEroare.setPreferredSize(new Dimension(200, 150));
                    lblEroare.setHorizontalAlignment(SwingConstants.CENTER);
                    lblEroare.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                    panelImagini.add(lblEroare);
                }
            }

            JScrollPane scrollImagini = new JScrollPane(panelImagini);
            scrollImagini.setPreferredSize(new Dimension(750, 200));
            panelPrincipal.add(scrollImagini);
        }

        JScrollPane scrollPrincipal = new JScrollPane(panelPrincipal);
        add(scrollPrincipal, BorderLayout.CENTER);

        // Buton inchidere
        JPanel panelButon = new JPanel();
        JButton btnInchide = new JButton("Închide");
                btnInchide.addActionListener(e -> dispose());
        panelButon.add(btnInchide);
        add(panelButon, BorderLayout.SOUTH);
    }
}
