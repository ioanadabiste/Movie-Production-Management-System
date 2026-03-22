package model.repository;

import model.Regizor;
import java.sql.*;
import java.util.*;

public class RegizorRepository {

    private static RegizorRepository instance; // instanța singleton

    private RegizorRepository() {
    }

    public static RegizorRepository getInstance() {
        if (instance == null) {
            instance = new RegizorRepository();
        }
        return instance;
    }
    public void adaugaRegizor(Regizor r) {
        String sql = "INSERT INTO Regizori VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, r.getId());
            stmt.setString(2, r.getNume());
            stmt.setString(3, r.getPrenume());
            stmt.setInt(4, r.getAnNastere());
            stmt.setString(5, r.getNationalitate());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void actualizeazaRegizor(Regizor r) {
        String sql = "UPDATE Regizori SET nume=?, prenume=?, an_nastere=?, nationalitate=? WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, r.getNume());
            stmt.setString(2, r.getPrenume());
            stmt.setInt(3, r.getAnNastere());
            stmt.setString(4, r.getNationalitate());
            stmt.setString(5, r.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void stergeRegizor(String id) {
        String sql = "DELETE FROM Regizori WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Regizor> getRegizori() {
        List<Regizor> list = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Regizori ORDER BY nume, prenume")) {

            while (rs.next()) {
                Regizor r = new Regizor();
                r.setId(rs.getString("id"));
                r.setNume(rs.getString("nume"));
                r.setPrenume(rs.getString("prenume"));
                r.setAnNastere(rs.getInt("an_nastere"));
                r.setNationalitate(rs.getString("nationalitate"));
                list.add(r);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return list;
    }

    public Regizor getRegizorById(String id) {
        String sql = "SELECT * FROM Regizori WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Regizor r = new Regizor();
                r.setId(rs.getString("id"));
                r.setNume(rs.getString("nume"));
                r.setPrenume(rs.getString("prenume"));
                r.setAnNastere(rs.getInt("an_nastere"));
                r.setNationalitate(rs.getString("nationalitate"));
                return r;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}