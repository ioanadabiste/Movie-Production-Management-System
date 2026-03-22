package model.repository;

import model.Actor;
import java.sql.*;
import java.util.*;

public class ActorRepository {

    private final Connection connection;

    
    public ActorRepository(Connection connection) {
        this.connection = connection;
    }

    public void adaugaActor(Actor actor) {
        String sql = "INSERT INTO actori (id, nume, prenume, an_nastere, nationalitate) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, actor.getId());
            stmt.setString(2, actor.getNume());
            stmt.setString(3, actor.getPrenume());
            stmt.setInt(4, actor.getAnNastere());
            stmt.setString(5, actor.getNationalitate());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare adaugare actor: " + e.getMessage());
        }
    }

    public void actualizeazaActor(Actor actor) {
        String sql = "UPDATE actori SET nume=?, prenume=?, an_nastere=?, nationalitate=? WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, actor.getNume());
            stmt.setString(2, actor.getPrenume());
            stmt.setInt(3, actor.getAnNastere());
            stmt.setString(4, actor.getNationalitate());
            stmt.setString(5, actor.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare actualizare actor: " + e.getMessage());
        }
    }

    public void stergeActor(String id) {
        String sql = "DELETE FROM actori WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare stergere actor: " + e.getMessage());
        }
    }

    public List<Actor> getActori() {
        List<Actor> list = new ArrayList<>();
        String sql = "SELECT * FROM actori ORDER BY nume, prenume";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapActor(rs));
            }

        } catch (SQLException e) {
            System.err.println("Eroare listare actori: " + e.getMessage());
        }

        return list;
    }

    public Actor getActorById(String id) {
        String sql = "SELECT * FROM actori WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapActor(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare cautare actor: " + e.getMessage());
        }

        return null;
    }


    private Actor mapActor(ResultSet rs) throws SQLException {
        Actor a = new Actor();
        a.setId(rs.getString("id"));
        a.setNume(rs.getString("nume"));
        a.setPrenume(rs.getString("prenume"));
        a.setAnNastere(rs.getInt("an_nastere"));
        a.setNationalitate(rs.getString("nationalitate"));
        return a;
    }
}