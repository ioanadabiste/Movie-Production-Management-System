package model.repository;

import model.*;

import java.sql.*;
import java.util.*;

public class FilmRepository {

    private static FilmRepository instance; // instanța singleton

    private FilmRepository() {
        // constructor privat ca să nu poată fi instanțiat de afară
    }

    public static FilmRepository getInstance() {
        if (instance == null) {
            instance = new FilmRepository();
        }
        return instance;
    }
    public void adaugaFilm(Film film) {
        String sqlFilm = "INSERT INTO Filme (id, titlu, an_realizare, tip_film, categorie_film, regizor_id, scenarist_id, descriere) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlActor = "INSERT INTO Filme_Actori (film_id, actor_id) VALUES (?, ?)";
        String sqlImagine = "INSERT INTO Filme_Imagini (film_id, cale_imagine) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtFilm = conn.prepareStatement(sqlFilm)) {
                stmtFilm.setString(1, film.getId());
                stmtFilm.setString(2, film.getTitlu());
                stmtFilm.setInt(3, film.getAnRealizare());
                stmtFilm.setString(4, film.getTipFilm() != null ? film.getTipFilm().name() : null);
                stmtFilm.setString(5, film.getCategorieFilm() != null ? film.getCategorieFilm().name() : null);
                stmtFilm.setString(6, film.getRegizorId());
                stmtFilm.setString(7, film.getScenaristId());
                stmtFilm.setString(8, film.getDescriere());
                stmtFilm.executeUpdate();
            }

            try (PreparedStatement stmtActor = conn.prepareStatement(sqlActor)) {
                for (String actorId : film.getActorIds()) {
                    stmtActor.setString(1, film.getId());
                    stmtActor.setString(2, actorId);
                    stmtActor.addBatch();
                }
                stmtActor.executeBatch();
            }

            try (PreparedStatement stmtImagine = conn.prepareStatement(sqlImagine)) {
                for (String cale : film.getCaiImagini()) {
                    stmtImagine.setString(1, film.getId());
                    stmtImagine.setString(2, cale);
                    stmtImagine.addBatch();
                }
                stmtImagine.executeBatch();
            }

            conn.commit();

        } catch (SQLException e) {
            System.err.println("Eroare adaugare film: " + e.getMessage());
        }
    }

    public void actualizeazaFilm(Film film) {
        String sqlFilm = "UPDATE Filme SET titlu=?, an_realizare=?, tip_film=?, categorie_film=?, regizor_id=?, scenarist_id=?, descriere=? WHERE id=?";
        String sqlDeleteActori = "DELETE FROM Filme_Actori WHERE film_id=?";
        String sqlDeleteImagini = "DELETE FROM Filme_Imagini WHERE film_id=?";
        String sqlActor = "INSERT INTO Filme_Actori (film_id, actor_id) VALUES (?, ?)";
        String sqlImagine = "INSERT INTO Filme_Imagini (film_id, cale_imagine) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtFilm = conn.prepareStatement(sqlFilm)) {
                stmtFilm.setString(1, film.getTitlu());
                stmtFilm.setInt(2, film.getAnRealizare());
                stmtFilm.setString(3, film.getTipFilm() != null ? film.getTipFilm().name() : null);
                stmtFilm.setString(4, film.getCategorieFilm() != null ? film.getCategorieFilm().name() : null);
                stmtFilm.setString(5, film.getRegizorId());
                stmtFilm.setString(6, film.getScenaristId());
                stmtFilm.setString(7, film.getDescriere());
                stmtFilm.setString(8, film.getId());
                stmtFilm.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteActori)) {
                stmt.setString(1, film.getId());
                stmt.executeUpdate();
            }

            try (PreparedStatement stmtActor = conn.prepareStatement(sqlActor)) {
                for (String actorId : film.getActorIds()) {
                    stmtActor.setString(1, film.getId());
                    stmtActor.setString(2, actorId);
                    stmtActor.addBatch();
                }
                stmtActor.executeBatch();
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteImagini)) {
                stmt.setString(1, film.getId());
                stmt.executeUpdate();
            }

            try (PreparedStatement stmtImagine = conn.prepareStatement(sqlImagine)) {
                for (String cale : film.getCaiImagini()) {
                    stmtImagine.setString(1, film.getId());
                    stmtImagine.setString(2, cale);
                    stmtImagine.addBatch();
                }
                stmtImagine.executeBatch();
            }

            conn.commit();

        } catch (SQLException e) {
            System.err.println("Eroare update film: " + e.getMessage());
        }
    }

    public void stergeFilm(String id) {
        String sql = "DELETE FROM Filme WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Film> getFilme() {
        List<Film> list = new ArrayList<>();
        String sql = "SELECT * FROM Filme ORDER BY titlu";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapFilm(rs, conn));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return list;
    }

    public Film getFilmById(String id) {
        String sql = "SELECT * FROM Filme WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapFilm(rs, conn);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    private Film mapFilm(ResultSet rs, Connection conn) throws SQLException {
        Film f = new Film();

        f.setId(rs.getString("id"));
        f.setTitlu(rs.getString("titlu"));
        f.setAnRealizare(rs.getInt("an_realizare"));

        if (rs.getString("tip_film") != null)
            f.setTipFilm(TipFilm.valueOf(rs.getString("tip_film")));

        if (rs.getString("categorie_film") != null)
            f.setCategorieFilm(CategorieFilm.valueOf(rs.getString("categorie_film")));

        f.setRegizorId(rs.getString("regizor_id"));
        f.setScenaristId(rs.getString("scenarist_id"));
        f.setDescriere(rs.getString("descriere"));

        f.setActorIds(getActori(f.getId(), conn));
        f.setCaiImagini(getImagini(f.getId(), conn));

        return f;
    }

    public List<String> getActori(String filmId, Connection conn) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT actor_id FROM Filme_Actori WHERE film_id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, filmId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("actor_id"));
            }
        }

        return list;
    }

    private List<String> getImagini(String filmId, Connection conn) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT cale_imagine FROM Filme_Imagini WHERE film_id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, filmId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("cale_imagine"));
            }
        }

        return list;
    }

    public List<Film> getFilmeSortateDeupaTip() {
        List<Film> list = new ArrayList<>();
        String sql = "SELECT * FROM Filme ORDER BY tip_film, titlu";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapFilm(rs, conn));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return list;
    }

    public List<Film> filtreazaFilme(TipFilm tip, CategorieFilm categorie, Integer an) {
        List<Film> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Filme WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (tip != null) {
            sql.append(" AND tip_film=?");
            params.add(tip.name());
        }

        if (categorie != null) {
            sql.append(" AND categorie_film=?");
            params.add(categorie.name());
        }

        if (an != null) {
            sql.append(" AND an_realizare=?");
            params.add(an);
        }

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapFilm(rs, conn));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return list;
    }

    public List<Film> cautaFilmeCuActor(String actorId) {
        List<Film> list = new ArrayList<>();
        String sql = "SELECT f.* FROM Filme f JOIN Filme_Actori fa ON f.id=fa.film_id WHERE fa.actor_id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, actorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapFilm(rs, conn));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return list;
    }
}