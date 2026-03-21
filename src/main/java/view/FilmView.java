package view;

import model.*;
import presenter.FilmPresenter;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FilmView extends JPanel {
    private FilmPresenter presenter;
    private JTable tabelFilme;
    private DefaultTableModel modelTabel;
    private JTextField txtTitlu, txtAnRealizare, txtDescriere;
    private JComboBox<TipFilm> cmbTipFilm;
    private JComboBox<CategorieFilm> cmbCategorieFilm;
    private JComboBox<Regizor> cmbRegizor;
    private JComboBox<Scenarist> cmbScenarist;
    private JList<Actor> listaActori;
    private DefaultListModel<Actor> modelListaActori;
    private JButton btnAdauga, btnActualizeaza, btnSterge, btnCurata, btnDetalii;
    private JButton btnAdaugaImagini, btnFiltreaza, btnCautaActor;
    private String idFilmSelectat = null;
    private List<String> imaginiSelectate = new ArrayList<>();

    public FilmView() {
        this.presenter = new FilmPresenter();
        initializeUI();
        incarcaFilme();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel principal split
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(350);

        // Panel formular
        JPanel panelFormular = new JPanel(new BorderLayout());
        panelFormular.setBorder(BorderFactory.createTitledBorder("Detalii Film"));

                JPanel panelCampuri = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titlu
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampuri.add(new JLabel("Titlu:"), gbc);
                gbc.gridx = 1;
        txtTitlu = new JTextField(20);
        panelCampuri.add(txtTitlu, gbc);

        // An Realizare
        gbc.gridx = 0; gbc.gridy = 1;
        panelCampuri.add(new JLabel("An Realizare:"), gbc);
                gbc.gridx = 1;
        txtAnRealizare = new JTextField(20);
        panelCampuri.add(txtAnRealizare, gbc);

        // Tip Film
        gbc.gridx = 0; gbc.gridy = 2;
        panelCampuri.add(new JLabel("Tip Film:"), gbc);
                gbc.gridx = 1;
        cmbTipFilm = new JComboBox<>(TipFilm.values());
        panelCampuri.add(cmbTipFilm, gbc);

        // Categorie
        gbc.gridx = 0; gbc.gridy = 3;
        panelCampuri.add(new JLabel("Categorie:"), gbc);
                gbc.gridx = 1;
        cmbCategorieFilm = new JComboBox<>(CategorieFilm.values());
        panelCampuri.add(cmbCategorieFilm, gbc);

        // Regizor
        gbc.gridx = 0; gbc.gridy = 4;
        panelCampuri.add(new JLabel("Regizor:"), gbc);
                gbc.gridx = 1;
        cmbRegizor = new JComboBox<>();
        incarcaRegizori();
        panelCampuri.add(cmbRegizor, gbc);

        // Scenarist
        gbc.gridx = 0; gbc.gridy = 5;
        panelCampuri.add(new JLabel("Scenarist:"), gbc);
                gbc.gridx = 1;
        cmbScenarist = new JComboBox<>();
        incarcaScenaristi();
        panelCampuri.add(cmbScenarist, gbc);

        // Descriere
        gbc.gridx = 0; gbc.gridy = 6;
        panelCampuri.add(new JLabel("Descriere:"), gbc);
                gbc.gridx = 1;
        txtDescriere = new JTextField(20);
        panelCampuri.add(txtDescriere, gbc);

        // Lista actori
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.NORTH;
        panelCampuri.add(new JLabel("Actori:"), gbc);
                gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        modelListaActori = new DefaultListModel<>();
        listaActori = new JList<>(modelListaActori);
        listaActori.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        incarcaActori();
        JScrollPane scrollActori = new JScrollPane(listaActori);
        scrollActori.setPreferredSize(new Dimension(200, 100));
        panelCampuri.add(scrollActori, gbc);

        panelFormular.add(panelCampuri, BorderLayout.CENTER);

        // Panel butoane
        JPanel panelButoane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAdauga = new JButton("Adaugă");
                btnActualizeaza = new JButton("Actualizează");
                        btnSterge = new JButton("Șterge");
                                btnCurata = new JButton("Curăță");
                                        btnAdaugaImagini = new JButton("Adaugă Imagini (max 3)");

                                                panelButoane.add(btnAdauga);
        panelButoane.add(btnActualizeaza);
        panelButoane.add(btnSterge);
        panelButoane.add(btnCurata);
        panelButoane.add(btnAdaugaImagini);

        panelFormular.add(panelButoane, BorderLayout.SOUTH);

        splitPane.setTopComponent(panelFormular);

        // Panel tabel si filtre
        JPanel panelJos = new JPanel(new BorderLayout());

        // Panel filtre si cautare
        JPanel panelFiltrare = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnFiltreaza = new JButton("Filtrează Filme");
                btnCautaActor = new JButton("Caută după Actor");
                        JButton btnResetare = new JButton("Afișează Toate");

                        panelFiltrare.add(btnFiltreaza);
        panelFiltrare.add(btnCautaActor);
        panelFiltrare.add(btnResetare);

        panelJos.add(panelFiltrare, BorderLayout.NORTH);

        // Tabel filme
        String[] coloane = {"ID", "Titlu", "An", "Tip", "Categorie", "Regizor", "Scenarist"};
        modelTabel = new DefaultTableModel(coloane, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelFilme = new JTable(modelTabel);
        tabelFilme.getColumnModel().getColumn(0).setMinWidth(0);
        tabelFilme.getColumnModel().getColumn(0).setMaxWidth(0);

        JScrollPane scrollPane = new JScrollPane(tabelFilme);
        panelJos.add(scrollPane, BorderLayout.CENTER);

        // Buton detalii
        JPanel panelDetalii = new JPanel();
        btnDetalii = new JButton("Vezi Detalii Film");
                panelDetalii.add(btnDetalii);
        panelJos.add(panelDetalii, BorderLayout.SOUTH);

        splitPane.setBottomComponent(panelJos);
        add(splitPane, BorderLayout.CENTER);

        // Event handlers
        btnAdauga.addActionListener(e -> adaugaFilm());
        btnActualizeaza.addActionListener(e -> actualizeazaFilm());
        btnSterge.addActionListener(e -> stergeFilm());
        btnCurata.addActionListener(e -> curataFormular());
        btnAdaugaImagini.addActionListener(e -> selecteazaImagini());
        btnFiltreaza.addActionListener(e -> afiseazaDialogFiltrare());
        btnCautaActor.addActionListener(e -> cautaDupaActor());
        btnResetare.addActionListener(e -> incarcaFilme());
        btnDetalii.addActionListener(e -> afiseazaDetaliiFilm());

        tabelFilme.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selecteazaFilm();
            }
        });
    }

        private void adaugaFilm() {
            try {
                String titlu = txtTitlu.getText().trim();
                int anRealizare = Integer.parseInt(txtAnRealizare.getText().trim());
                TipFilm tipFilm = (TipFilm) cmbTipFilm.getSelectedItem();
                CategorieFilm categorieFilm = (CategorieFilm) cmbCategorieFilm.getSelectedItem();
                String descriere = txtDescriere.getText().trim();

                if (titlu.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Titlul este obligatoriu!");
                    return;
                }

                presenter.adaugaFilm(titlu, anRealizare, tipFilm, categorieFilm, descriere);

                // Găsim filmul nou adăugat și îl actualizăm cu detalii complete
                List<Film> filme = presenter.getFilme();
                Film filmNou = filme.get(filme.size() - 1);

                actualizeazaDetaliiFilm(filmNou);

                incarcaFilme();
                curataFormular();
                JOptionPane.showMessageDialog(this, "Film adăugat cu succes!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "An realizare invalid!");
            }
        }

        private void actualizeazaFilm() {
            if (idFilmSelectat == null) {
                JOptionPane.showMessageDialog(this, "Selectați un film din tabel!");
                return;
            }

            try {
                String titlu = txtTitlu.getText().trim();
                int anRealizare = Integer.parseInt(txtAnRealizare.getText().trim());
                TipFilm tipFilm = (TipFilm) cmbTipFilm.getSelectedItem();
                CategorieFilm categorieFilm = (CategorieFilm) cmbCategorieFilm.getSelectedItem();
                String descriere = txtDescriere.getText().trim();

                Film film = presenter.getFilmById(idFilmSelectat);
                if (film != null) {
                    actualizeazaDetaliiFilm(film);
                }

                incarcaFilme();
                curataFormular();
                JOptionPane.showMessageDialog(this, "Film actualizat cu succes!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "An realizare invalid!");
            }
        }

        private void actualizeazaDetaliiFilm(Film film) {
            String titlu = txtTitlu.getText().trim();
            int anRealizare = Integer.parseInt(txtAnRealizare.getText().trim());
            TipFilm tipFilm = (TipFilm) cmbTipFilm.getSelectedItem();
            CategorieFilm categorieFilm = (CategorieFilm) cmbCategorieFilm.getSelectedItem();
            String descriere = txtDescriere.getText().trim();

            String regizorId = null;
            Regizor regizor = (Regizor) cmbRegizor.getSelectedItem();
            if (regizor != null) {
                regizorId = regizor.getId();
            }

            String scenaristId = null;
            Scenarist scenarist = (Scenarist) cmbScenarist.getSelectedItem();
            if (scenarist != null) {
                scenaristId = scenarist.getId();
            }

            List<String> actorIds = new ArrayList<>();
            for (Actor actor : listaActori.getSelectedValuesList()) {
                actorIds.add(actor.getId());
            }

            presenter.actualizeazaFilm(film.getId(), titlu, anRealizare, tipFilm,
                    categorieFilm, descriere, regizorId, scenaristId,
                    actorIds, imaginiSelectate);
        }

        private void stergeFilm() {
            if (idFilmSelectat == null) {
                JOptionPane.showMessageDialog(this, "Selectați un film din tabel!");
                return;
            }

            int confirmare = JOptionPane.showConfirmDialog(this,
                    "Sigur doriți să ștergeți acest film?",
                    "Confirmare",
                    JOptionPane.YES_NO_OPTION);

            if (confirmare == JOptionPane.YES_OPTION) {
                presenter.stergeFilm(idFilmSelectat);
                incarcaFilme();
                curataFormular();
                JOptionPane.showMessageDialog(this, "Film șters cu succes!");
            }
        }

        private void selecteazaFilm() {
            int randSelectat = tabelFilme.getSelectedRow();
            if (randSelectat >= 0) {
                idFilmSelectat = (String) modelTabel.getValueAt(randSelectat, 0);
                Film film = presenter.getFilmById(idFilmSelectat);

                if (film != null) {
                    txtTitlu.setText(film.getTitlu());
                    txtAnRealizare.setText(String.valueOf(film.getAnRealizare()));
                    cmbTipFilm.setSelectedItem(film.getTipFilm());
                    cmbCategorieFilm.setSelectedItem(film.getCategorieFilm());
                    txtDescriere.setText(film.getDescriere() != null ? film.getDescriere() : "");

                    if (film.getRegizorId() != null) {
                        Regizor regizor = presenter.getRegizorById(film.getRegizorId());
                        cmbRegizor.setSelectedItem(regizor);
                    }

                    if (film.getScenaristId() != null) {
                        Scenarist scenarist = presenter.getScenaristById(film.getScenaristId());
                        cmbScenarist.setSelectedItem(scenarist);
                    }

                    listaActori.clearSelection();
                    List<Integer> indiciSelectati = new ArrayList<>();
                    for (String actorId : film.getActorIds()) {
                        for (int i = 0; i < modelListaActori.getSize(); i++) {
                            if (modelListaActori.getElementAt(i).getId().equals(actorId)) {
                                indiciSelectati.add(i);
                            }
                        }
                    }
                    int[] indices = indiciSelectati.stream().mapToInt(Integer::intValue).toArray();
                    listaActori.setSelectedIndices(indices);

                    imaginiSelectate = new ArrayList<>(film.getCaiImagini());
                }
            }
        }

        private void selecteazaImagini() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Imagini", "jpg", "jpeg", "png", "gif");
                    fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File[] fisiere = fileChooser.getSelectedFiles();

                if (fisiere.length > 3) {
                    JOptionPane.showMessageDialog(this, "Puteți selecta maximum 3 imagini!");
                    return;
                }

                imaginiSelectate.clear();
                for (File fisier : fisiere) {
                    try {
                        File destinatie = new File("images" + System.currentTimeMillis() + "_" + fisier.getName());
                                destinatie.getParentFile().mkdirs();
                        Files.copy(fisier.toPath(), destinatie.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        imaginiSelectate.add(destinatie.getPath());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Eroare la copierea imaginii: " + ex.getMessage());
                    }
                }

                JOptionPane.showMessageDialog(this, imaginiSelectate.size() + " imagini selectate!");
            }
        }

        private void afiseazaDialogFiltrare() {
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Filtrare Filme", true);
                    dialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JComboBox<TipFilm> cmbTip = new JComboBox<>();
            cmbTip.addItem(null);
            for (TipFilm tip : TipFilm.values()) {
                cmbTip.addItem(tip);
            }

            JComboBox<CategorieFilm> cmbCategorie = new JComboBox<>();
            cmbCategorie.addItem(null);
            for (CategorieFilm cat : CategorieFilm.values()) {
                cmbCategorie.addItem(cat);
            }

            JTextField txtAn = new JTextField(10);

            gbc.gridx = 0; gbc.gridy = 0;
            dialog.add(new JLabel("Tip Film:"), gbc);
                    gbc.gridx = 1;
            dialog.add(cmbTip, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            dialog.add(new JLabel("Categorie:"), gbc);
                    gbc.gridx = 1;
            dialog.add(cmbCategorie, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            dialog.add(new JLabel("An:"), gbc);
                    gbc.gridx = 1;
            dialog.add(txtAn, gbc);

            JButton btnFiltreaza = new JButton("Filtrează");
                    gbc.gridx = 0; gbc.gridy = 3;
            gbc.gridwidth = 2;
            dialog.add(btnFiltreaza, gbc);

            btnFiltreaza.addActionListener(e -> {
                TipFilm tip = (TipFilm) cmbTip.getSelectedItem();
                CategorieFilm categorie = (CategorieFilm) cmbCategorie.getSelectedItem();
                Integer an = null;

                if (!txtAn.getText().trim().isEmpty()) {
                    try {
                        an = Integer.parseInt(txtAn.getText().trim());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "An invalid!");
                        return;
                    }
                }

                List<Film> filmeFiltrare = presenter.filtreazaFilme(tip, categorie, an);
                afiseazaFilmeInTabel(filmeFiltrare);
                dialog.dispose();
            });

            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }

        private void cautaDupaActor() {
            List<Actor> actori = presenter.getActori();
            Actor actorSelectat = (Actor) JOptionPane.showInputDialog(
                    this,
                    "Selectați un actor:",
                    "Căutare după Actor",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    actori.toArray(),
                    null
            );

            if (actorSelectat != null) {
                List<Film> filme = presenter.cautaFilmeCuActor(actorSelectat.getId());
                afiseazaFilmeInTabel(filme);
            }
        }

        private void afiseazaDetaliiFilm() {
            if (idFilmSelectat == null) {
                JOptionPane.showMessageDialog(this, "Selectați un film din tabel!");
                return;
            }

            Film film = presenter.getFilmById(idFilmSelectat);
            if (film != null) {
                new FilmDetailsDialog((Frame) SwingUtilities.getWindowAncestor(this), film, presenter).setVisible(true);
            }
        }

        private void curataFormular() {
            idFilmSelectat = null;
            txtTitlu.setText("");
                    txtAnRealizare.setText("");
                            txtDescriere.setText("");
                                    cmbTipFilm.setSelectedIndex(0);
            cmbCategorieFilm.setSelectedIndex(0);
            cmbRegizor.setSelectedIndex(0);
            cmbScenarist.setSelectedIndex(0);
            listaActori.clearSelection();
            imaginiSelectate.clear();
            tabelFilme.clearSelection();
        }

        public void incarcaFilme() {
            List<Film> filme = presenter.getFilmeSortateDeupaTip();
            afiseazaFilmeInTabel(filme);
        }

        private void afiseazaFilmeInTabel(List<Film> filme) {
            modelTabel.setRowCount(0);
            for (Film film : filme) {
                String regizor = "";
                if (film.getRegizorId() != null) {
                    Regizor r = presenter.getRegizorById(film.getRegizorId());
                    if (r != null) regizor = r.getNumeComplet();
                }

                String scenarist ="";
                if (film.getScenaristId() != null) {
                    Scenarist s = presenter.getScenaristById(film.getScenaristId());
                    if (s != null) scenarist = s.getNumeComplet();
                }

                modelTabel.addRow(new Object[]{
                        film.getId(),
                        film.getTitlu(),
                        film.getAnRealizare(),
                        film.getTipFilm(),
                        film.getCategorieFilm(),
                        regizor,
                        scenarist
                });
            }
        }

        private void incarcaRegizori() {
            cmbRegizor.removeAllItems();
            cmbRegizor.addItem(null);
            for (Regizor regizor : presenter.getRegizori()) {
                cmbRegizor.addItem(regizor);
            }
        }

        private void incarcaScenaristi() {
            cmbScenarist.removeAllItems();
            cmbScenarist.addItem(null);
            for (Scenarist scenarist : presenter.getScenaristi()) {
                cmbScenarist.addItem(scenarist);
            }
        }

        private void incarcaActori() {
            modelListaActori.clear();
            for (Actor actor : presenter.getActori()) {
                modelListaActori.addElement(actor);
            }
        }
    }
