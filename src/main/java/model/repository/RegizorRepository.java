package model.repository;

import model.Regizor;
import java.sql.*;
import java.util.*;

public class RegizorRepository {

    private final Connection connection;


    public RegizorRepository(Connection connection) {
        this.connection = connection;
    }

    public void adaugaRegizor(Regizor r) {
        String sql = "INSERT INTO regizori (id, nume, prenume, an_nastere, nationalitate) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, r.getId());
            stmt.setString(2, r.getNume());
            stmt.setString(3, r.getPrenume());
            stmt.setInt(4, r.getAnNastere());
            stmt.setString(5, r.getNationalitate());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare adaugare regizor: " + e.getMessage());
        }
    }

    public void actualizeazaRegizor(Regizor r) {
        String sql = "UPDATE regizori SET nume=?, prenume=?, an_nastere=?, nationalitate=? WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, r.getNume());
            stmt.setString(2, r.getPrenume());
            stmt.setInt(3, r.getAnNastere());
            stmt.setString(4, r.getNationalitate());
            stmt.setString(5, r.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare actualizare regizor: " + e.getMessage());
        }
    }

    public void stergeRegizor(String id) {
        String sql = "DELETE FROM regizori WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare stergere regizor: " + e.getMessage());
        }
    }

    public List<Regizor> getRegizori() {
        List<Regizor> list = new ArrayList<>();
        String sql = "SELECT * FROM regizori ORDER BY nume, prenume";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRegizor(rs));
            }
        } catch (SQLException e) {
            System.err.println("Eroare listare regizori: " + e.getMessage());
        }

        return list;
    }

    public Regizor getRegizorById(String id) {
        String sql = "SELECT * FROM regizori WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRegizor(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare cautare regizor: " + e.getMessage());
        }

        return null;
    }


    private Regizor mapRegizor(ResultSet rs) throws SQLException {
        Regizor r = new Regizor();
        r.setId(rs.getString("id"));
        r.setNume(rs.getString("nume"));
        r.setPrenume(rs.getString("prenume"));
        r.setAnNastere(rs.getInt("an_nastere"));
        r.setNationalitate(rs.getString("nationalitate"));
        return r;
    }
}