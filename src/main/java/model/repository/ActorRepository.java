package model.repository;

import model.Actor;
import java.sql.*;
import java.util.*;

public class ActorRepository {

    private static ActorRepository instance; // instanța singleton

    private ActorRepository() {

    }

    public static ActorRepository getInstance() {
        if (instance == null) {
            instance = new ActorRepository();
        }
        return instance;
    }
    public void adaugaActor(Actor actor) {
        String sql = "INSERT INTO Actori (id, nume, prenume, an_nastere, nationalitate) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, actor.getId());
            stmt.setString(2, actor.getNume());
            stmt.setString(3, actor.getPrenume());
            stmt.setInt(4, actor.getAnNastere());
            stmt.setString(5, actor.getNationalitate());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare actor: " + e.getMessage());
        }
    }

    public void actualizeazaActor(Actor actor) {
        String sql = "UPDATE Actori SET nume=?, prenume=?, an_nastere=?, nationalitate=? WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, actor.getNume());
            stmt.setString(2, actor.getPrenume());
            stmt.setInt(3, actor.getAnNastere());
            stmt.setString(4, actor.getNationalitate());
            stmt.setString(5, actor.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void stergeActor(String id) {
        String sql = "DELETE FROM Actori WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Actor> getActori() {
        List<Actor> list = new ArrayList<>();
        String sql = "SELECT * FROM Actori ORDER BY nume, prenume";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Actor a = new Actor();
                a.setId(rs.getString("id"));
                a.setNume(rs.getString("nume"));
                a.setPrenume(rs.getString("prenume"));
                a.setAnNastere(rs.getInt("an_nastere"));
                a.setNationalitate(rs.getString("nationalitate"));
                list.add(a);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return list;
    }

    public Actor getActorById(String id) {
        String sql = "SELECT * FROM Actori WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Actor a = new Actor();
                a.setId(rs.getString("id"));
                a.setNume(rs.getString("nume"));
                a.setPrenume(rs.getString("prenume"));
                a.setAnNastere(rs.getInt("an_nastere"));
                a.setNationalitate(rs.getString("nationalitate"));
                return a;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}