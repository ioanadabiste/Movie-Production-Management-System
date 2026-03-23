package view;

import model.Actor;
import presenter.ActorPresenter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ActorView extends JPanel {
    private ActorPresenter presenter;
    private JTable tabelActori;
    private DefaultTableModel modelTabel;
    private JTextField txtNume, txtPrenume, txtAnNastere, txtNationalitate;
    private JButton btnAdauga, btnActualizeaza, btnSterge, btnCurata;
    private String idActorSelectat = null;

    public ActorView(ActorPresenter presenter) {
        this.presenter = presenter; // Primim presenterul gata configurat
        initializeUI();
        incarcaActori();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel formular
        JPanel panelFormular = new JPanel(new GridBagLayout());
        panelFormular.setBorder(BorderFactory.createTitledBorder("Detalii Actor"));
                GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nume
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormular.add(new JLabel("Nume:"), gbc);
                gbc.gridx = 1;
        txtNume = new JTextField(20);
        panelFormular.add(txtNume, gbc);

        // Prenume
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormular.add(new JLabel("Prenume:"), gbc);
                gbc.gridx = 1;
        txtPrenume = new JTextField(20);
        panelFormular.add(txtPrenume, gbc);

        // An Nastere
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormular.add(new JLabel("An Naștere:"), gbc);
                gbc.gridx = 1;
        txtAnNastere = new JTextField(20);
        panelFormular.add(txtAnNastere, gbc);

        // Nationalitate
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormular.add(new JLabel("Naționalitate:"), gbc);
                gbc.gridx = 1;
        txtNationalitate = new JTextField(20);
        panelFormular.add(txtNationalitate, gbc);

        // Panel butoane
        JPanel panelButoane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAdauga = new JButton("Adaugă");
                btnActualizeaza = new JButton("Actualizează");
                        btnSterge = new JButton("Șterge");
                                btnCurata = new JButton("Curăță");

                                        panelButoane.add(btnAdauga);
        panelButoane.add(btnActualizeaza);
        panelButoane.add(btnSterge);
        panelButoane.add(btnCurata);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelFormular.add(panelButoane, gbc);

        add(panelFormular, BorderLayout.NORTH);

        // Tabel
        String[] coloane = {"ID", "Nume", "Prenume", "An Naștere", "Naționalitate"};
        modelTabel = new DefaultTableModel(coloane, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelActori = new JTable(modelTabel);
        tabelActori.getColumnModel().getColumn(0).setMinWidth(0);
        tabelActori.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelActori.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(tabelActori);
        add(scrollPane, BorderLayout.CENTER);

        // Event handlers
        btnAdauga.addActionListener(e -> adaugaActor());
        btnActualizeaza.addActionListener(e -> actualizeazaActor());
        btnSterge.addActionListener(e -> stergeActor());
        btnCurata.addActionListener(e -> curataFormular());

        tabelActori.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selecteazaActor();
            }
        });
    }

        private void adaugaActor() {
            try {
                String nume = txtNume.getText().trim();
                String prenume = txtPrenume.getText().trim();
                int anNastere = Integer.parseInt(txtAnNastere.getText().trim());
                String nationalitate = txtNationalitate.getText().trim();

                if (nume.isEmpty() || prenume.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Numele și prenumele sunt obligatorii!");
                    return;
                }

                presenter.adauga(nume, prenume, anNastere, nationalitate);
                incarcaActori();
                curataFormular();
                JOptionPane.showMessageDialog(this, "Actor adăugat cu succes!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "An naștere invalid!");
            }
        }

        private void actualizeazaActor() {
            if (idActorSelectat == null) {
                JOptionPane.showMessageDialog(this, "Selectați un actor din tabel!");
                return;
            }

            try {
                String nume = txtNume.getText().trim();
                String prenume = txtPrenume.getText().trim();
                int anNastere = Integer.parseInt(txtAnNastere.getText().trim());
                String nationalitate = txtNationalitate.getText().trim();

                if (nume.isEmpty() || prenume.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Numele și prenumele sunt obligatorii!");
                    return;
                }

                presenter.actualizeaza(idActorSelectat, nume, prenume, anNastere, nationalitate);
                incarcaActori();
                curataFormular();
                JOptionPane.showMessageDialog(this, "Actor actualizat cu succes!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "An naștere invalid!");
            }
        }

        private void stergeActor() {
            if (idActorSelectat == null) {
                JOptionPane.showMessageDialog(this, "Selectați un actor din tabel!");
                return;
            }

            int confirmare = JOptionPane.showConfirmDialog(this,
                    "Sigur doriți să ștergeți acest actor?",
                    "Confirmare",
                    JOptionPane.YES_NO_OPTION);

            if (confirmare == JOptionPane.YES_OPTION) {
                presenter.sterge(idActorSelectat);
                incarcaActori();
                curataFormular();
                JOptionPane.showMessageDialog(this, "Actor șters cu succes!");
            }
        }

        private void selecteazaActor() {
            int randSelectat = tabelActori.getSelectedRow();
            if (randSelectat >= 0) {
                idActorSelectat = (String) modelTabel.getValueAt(randSelectat, 0);
                txtNume.setText((String) modelTabel.getValueAt(randSelectat, 1));
                txtPrenume.setText((String) modelTabel.getValueAt(randSelectat, 2));
                txtAnNastere.setText(modelTabel.getValueAt(randSelectat, 3).toString());
                txtNationalitate.setText((String) modelTabel.getValueAt(randSelectat, 4));
            }
        }

        private void curataFormular() {
            idActorSelectat = null;
            txtNume.setText("");
                    txtPrenume.setText("");
                            txtAnNastere.setText("");
                                    txtNationalitate.setText("");
                                            tabelActori.clearSelection();
        }

        public void incarcaActori() {
            modelTabel.setRowCount(0);
            List<Actor> actori = presenter.getAll();
            for (Actor actor : actori) {
                modelTabel.addRow(new Object[]{
                        actor.getId(),
                        actor.getNume(),
                        actor.getPrenume(),
                        actor.getAnNastere(),
                        actor.getNationalitate()
                });
            }
        }
    }
