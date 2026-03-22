package model.repository;

import model.Scenarist;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScenaristRepository {

    private final Connection connection;


    public ScenaristRepository(Connection connection) {
        this.connection = connection;
    }


    public void adauga(Scenarist scenarist) {
        String sql = "INSERT INTO scenaristi (id, nume, prenume, an_nastere, nationalitate) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, scenarist.getId());
            stmt.setString(2, scenarist.getNume());
            stmt.setString(3, scenarist.getPrenume());
            stmt.setInt(4, scenarist.getAnNastere());
            stmt.setString(5, scenarist.getNationalitate());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare adaugare scenarist: " + e.getMessage());
        }
    }

    public List<Scenarist> getAll() {
        List<Scenarist> scenaristi = new ArrayList<>();
        String sql = "SELECT * FROM scenaristi";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                scenaristi.add(mapScenarist(rs));
            }
        } catch (SQLException e) {
            System.err.println("Eroare listare scenaristi: " + e.getMessage());
        }

        return scenaristi;
    }


    public Scenarist getById(String id) {
        String sql = "SELECT * FROM scenaristi WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapScenarist(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare cautare scenarist: " + e.getMessage());
        }

        return null;
    }


    public void update(Scenarist scenarist) {
        String sql = "UPDATE scenaristi SET nume=?, prenume=?, an_nastere=?, nationalitate=? WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, scenarist.getNume());
            stmt.setString(2, scenarist.getPrenume());
            stmt.setInt(3, scenarist.getAnNastere());
            stmt.setString(4, scenarist.getNationalitate());
            stmt.setString(5, scenarist.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare update scenarist: " + e.getMessage());
        }
    }


    public void delete(String id) {
        String sql = "DELETE FROM scenaristi WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare stergere scenarist: " + e.getMessage());
        }
    }

    // Metodă helper pentru mapare
    private Scenarist mapScenarist(ResultSet rs) throws SQLException {
        Scenarist s = new Scenarist();
        s.setId(rs.getString("id"));
        s.setNume(rs.getString("nume"));
        s.setPrenume(rs.getString("prenume"));
        s.setAnNastere(rs.getInt("an_nastere"));
        s.setNationalitate(rs.getString("nationalitate"));
        return s;
    }
}