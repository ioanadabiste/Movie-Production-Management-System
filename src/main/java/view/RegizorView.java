package view;

import model.Regizor;
import presenter.RegizorPresenter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RegizorView extends JPanel {
    private RegizorPresenter presenter;
    private JTable tabelRegizori;
    private DefaultTableModel modelTabel;
    private JTextField txtNume, txtPrenume, txtAnNastere, txtNationalitate;
    private JButton btnAdauga, btnActualizeaza, btnSterge, btnCurata;
    private String idRegizorSelectat = null;

    public RegizorView() {
        this.presenter = new RegizorPresenter();
        initializeUI();
        incarcaRegizori();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel formular
        JPanel panelFormular = new JPanel(new GridBagLayout());
        panelFormular.setBorder(BorderFactory.createTitledBorder("Detalii Regizor"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormular.add(new JLabel("Nume:"), gbc);
        gbc.gridx = 1;
        txtNume = new JTextField(20);
        panelFormular.add(txtNume, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormular.add(new JLabel("Prenume:"), gbc);
        gbc.gridx = 1;
        txtPrenume = new JTextField(20);
        panelFormular.add(txtPrenume, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormular.add(new JLabel("An Naștere:"), gbc);
        gbc.gridx = 1;
        txtAnNastere = new JTextField(20);
        panelFormular.add(txtAnNastere, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormular.add(new JLabel("Naționalitate:"), gbc);
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

        gbc.gridx = 0;
        gbc.gridy = 4;
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
        tabelRegizori = new JTable(modelTabel);
        tabelRegizori.getColumnModel().getColumn(0).setMinWidth(0);
        tabelRegizori.getColumnModel().getColumn(0).setMaxWidth(0);

        JScrollPane scrollPane = new JScrollPane(tabelRegizori);
        add(scrollPane, BorderLayout.CENTER);

        btnAdauga.addActionListener(e -> adaugaRegizor());
        btnActualizeaza.addActionListener(e -> actualizeazaRegizor());
        btnSterge.addActionListener(e -> stergeRegizor());
        btnCurata.addActionListener(e -> curataFormular());

        tabelRegizori.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selecteazaRegizor();
            }
        });
    }

         void adaugaRegizor() {
            try {
                String nume = txtNume.getText().trim();
                String prenume = txtPrenume.getText().trim();
                int anNastere = Integer.parseInt(txtAnNastere.getText().trim());
                String nationalitate = txtNationalitate.getText().trim();

                if (nume.isEmpty() || prenume.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Numele și prenumele sunt obligatorii!");
                    return;
                }

                presenter.adaugaRegizor(nume, prenume, anNastere, nationalitate);
                incarcaRegizori();
                curataFormular();
                JOptionPane.showMessageDialog(this, "Regizor adăugat cu succes!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "An naștere invalid!");
            }
        }

        private void actualizeazaRegizor() {
            if (idRegizorSelectat == null) {
                JOptionPane.showMessageDialog(this, "Selectați un regizor din tabel!");
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

                presenter.actualizeazaRegizor(idRegizorSelectat, nume, prenume, anNastere, nationalitate);
                incarcaRegizori();
                curataFormular();
                JOptionPane.showMessageDialog(this, "Regizor actualizat cu succes!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "An naștere invalid!");
            }
        }

         void stergeRegizor() {
            if (idRegizorSelectat == null) {
                JOptionPane.showMessageDialog(this, "Selectați un regizor din tabel!");
                return;
            }

            int confirmare = JOptionPane.showConfirmDialog(this,
                    "Sigur doriți să ștergeți acest regizor?",
                    "Confirmare",
                    JOptionPane.YES_NO_OPTION);

            if (confirmare == JOptionPane.YES_OPTION) {
                presenter.stergeRegizor(idRegizorSelectat);
                incarcaRegizori();
                curataFormular();
                JOptionPane.showMessageDialog(this, "Regizor șters cu succes!");
            }
        }

        void selecteazaRegizor() {
            int randSelectat = tabelRegizori.getSelectedRow();
            if (randSelectat >= 0) {
                idRegizorSelectat = (String) modelTabel.getValueAt(randSelectat, 0);
                txtNume.setText((String) modelTabel.getValueAt(randSelectat, 1));
                txtPrenume.setText((String) modelTabel.getValueAt(randSelectat, 2));
                txtAnNastere.setText(modelTabel.getValueAt(randSelectat, 3).toString());
                txtNationalitate.setText((String) modelTabel.getValueAt(randSelectat, 4));
            }
        }

        void curataFormular() {
            idRegizorSelectat = null;
            txtNume.setText("");
                    txtPrenume.setText("");
                            txtAnNastere.setText("");
                                    txtNationalitate.setText("");
                                            tabelRegizori.clearSelection();
        }

        void incarcaRegizori() {
            modelTabel.setRowCount(0);
            List<Regizor> regizori = presenter.getRegizori();
            for (Regizor regizor : regizori) {
                modelTabel.addRow(new Object[]{
                        regizor.getId(),
                        regizor.getNume(),
                        regizor.getPrenume(),
                        regizor.getAnNastere(),
                        regizor.getNationalitate()
                });
            }
        }
    }

