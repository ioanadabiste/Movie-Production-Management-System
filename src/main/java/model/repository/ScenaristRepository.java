package model.repository;

import model.Scenarist;
import model.repository.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScenaristRepository {
    private static ScenaristRepository instance; // instanța singleton

    private ScenaristRepository() {
    }

    public static ScenaristRepository getInstance() {
        if (instance == null) {
            instance = new ScenaristRepository();
        }
        return instance;
    }
    // CREATE
    public void adauga(Scenarist scenarist) {
        String sql = "INSERT INTO scenaristi (id, nume, prenume, an_nastere, nationalitate) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, scenarist.getId());
            stmt.setString(2, scenarist.getNume());
            stmt.setString(3, scenarist.getPrenume());
            stmt.setInt(4, scenarist.getAnNastere());
            stmt.setString(5, scenarist.getNationalitate());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ ALL
    public List<Scenarist> getAll() {
        List<Scenarist> scenaristi = new ArrayList<>();
        String sql = "SELECT * FROM scenaristi";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Scenarist s = new Scenarist();
                s.setId(rs.getString("id"));
                s.setNume(rs.getString("nume"));
                s.setPrenume(rs.getString("prenume"));
                s.setAnNastere(rs.getInt("an_nastere"));
                s.setNationalitate(rs.getString("nationalitate"));

                scenaristi.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scenaristi;
    }

    // READ BY ID
    public Scenarist getById(String id) {
        String sql = "SELECT * FROM scenaristi WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Scenarist s = new Scenarist();
                s.setId(rs.getString("id"));
                s.setNume(rs.getString("nume"));
                s.setPrenume(rs.getString("prenume"));
                s.setAnNastere(rs.getInt("an_nastere"));
                s.setNationalitate(rs.getString("nationalitate"));
                return s;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // UPDATE
    public void update(Scenarist scenarist) {
        String sql = "UPDATE scenaristi SET nume=?, prenume=?, an_nastere=?, nationalitate=? WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, scenarist.getNume());
            stmt.setString(2, scenarist.getPrenume());
            stmt.setInt(3, scenarist.getAnNastere());
            stmt.setString(4, scenarist.getNationalitate());
            stmt.setString(5, scenarist.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(String id) {
        String sql = "DELETE FROM scenaristi WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}