package view;

import model.Scenarist;
import presenter.ScenaristPresenter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ScenaristView extends JPanel {
    private ScenaristPresenter presenter;
    private JTable tabelScenaristi;
    private DefaultTableModel modelTabel;
    private JTextField txtNume, txtPrenume, txtAnNastere, txtNationalitate;
    private JButton btnAdauga, btnActualizeaza, btnSterge, btnCurata;
    private String idScenaristSelectat = null;

    public ScenaristView(ScenaristPresenter presenter) {
        this.presenter = presenter; // Primim presenterul deja legat la baza de date
        initializeUI();
        incarcaScenaristi();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelFormular = new JPanel(new GridBagLayout());
        panelFormular.setBorder(BorderFactory.createTitledBorder("Detalii Scenarist"));
                GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelFormular.add(new JLabel("Nume:"), gbc);
                gbc.gridx = 1;
        txtNume = new JTextField(20);
        panelFormular.add(txtNume, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelFormular.add(new JLabel("Prenume:"), gbc);
                gbc.gridx = 1;
        txtPrenume = new JTextField(20);
        panelFormular.add(txtPrenume, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelFormular.add(new JLabel("An Naștere:"), gbc);
                gbc.gridx = 1;
        txtAnNastere = new JTextField(20);
        panelFormular.add(txtAnNastere, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelFormular.add(new JLabel("Naționalitate"), gbc);
                gbc.gridx = 1;
        txtNationalitate = new JTextField(20);
        panelFormular.add(txtNationalitate, gbc);

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

        String[] coloane = {"ID", "Nume", "Prenume", "An Naștere", "Naționalitate"};
        modelTabel = new DefaultTableModel(coloane, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelScenaristi = new JTable(modelTabel);
        tabelScenaristi.getColumnModel().getColumn(0).setMinWidth(0);
        tabelScenaristi.getColumnModel().getColumn(0).setMaxWidth(0);

        JScrollPane scrollPane = new JScrollPane(tabelScenaristi);
        add(scrollPane, BorderLayout.CENTER);

        btnAdauga.addActionListener(e -> adaugaScenarist());
        btnActualizeaza.addActionListener(e -> actualizeazaScenarist());
        btnSterge.addActionListener(e -> stergeScenarist());
        btnCurata.addActionListener(e -> curataFormular());

        tabelScenaristi.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selecteazaScenarist();
            }
        });
    }

        private void adaugaScenarist() {
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
                incarcaScenaristi();
                curataFormular();
                JOptionPane.showMessageDialog(this, "Scenarist adăugat cu succes!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "An naștere invalid!");
            }
        }

        private void actualizeazaScenarist() {
            if (idScenaristSelectat == null) {
                JOptionPane.showMessageDialog(this, "Selectați un scenarist din tabel!");
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

                presenter.actualizeaza(idScenaristSelectat, nume, prenume, anNastere, nationalitate);
                incarcaScenaristi();
                curataFormular();
                JOptionPane.showMessageDialog(this, "Scenarist actualizat cu succes!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "An naștere invalid!");
            }
        }

        private void stergeScenarist() {
            if (idScenaristSelectat == null) {
                JOptionPane.showMessageDialog(this, "Selectați un scenarist din tabel!");
                return;
            }

            int confirmare = JOptionPane.showConfirmDialog(this,
                    "Sigur doriți să ștergeți acest scenarist?",
                    "Confirmare",
                    JOptionPane.YES_NO_OPTION);

            if (confirmare == JOptionPane.YES_OPTION) {
                presenter.sterge(idScenaristSelectat);
                incarcaScenaristi();
                curataFormular();
                JOptionPane.showMessageDialog(this, "Scenarist șters cu succes!");
            }
        }

        private void selecteazaScenarist() {
            int randSelectat = tabelScenaristi.getSelectedRow();
            if (randSelectat >= 0) {
                idScenaristSelectat = (String) modelTabel.getValueAt(randSelectat, 0);
                txtNume.setText((String) modelTabel.getValueAt(randSelectat, 1));
                txtPrenume.setText((String) modelTabel.getValueAt(randSelectat, 2));
                txtAnNastere.setText(modelTabel.getValueAt(randSelectat, 3).toString());
                txtNationalitate.setText((String) modelTabel.getValueAt(randSelectat, 4));
            }
        }

        private void curataFormular() {
            idScenaristSelectat = null;
            txtNume.setText("");
                    txtPrenume.setText("");
                            txtAnNastere.setText("");
                                    txtNationalitate.setText("");
                                            tabelScenaristi.clearSelection();
        }

        public void incarcaScenaristi() {
            modelTabel.setRowCount(0);
            List<Scenarist> scenaristi = presenter.getAll();
            for (Scenarist scenarist : scenaristi) {
                modelTabel.addRow(new Object[]{
                        scenarist.getId(),
                        scenarist.getNume(),
                        scenarist.getPrenume(),
                        scenarist.getAnNastere(),
                        scenarist.getNationalitate()
                });
            }
        }
    }
