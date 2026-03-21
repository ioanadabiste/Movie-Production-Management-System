package model.repository;

import model.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DataRepository {
    private static DataRepository instance;

    private List<Actor> actori;
    private List<Regizor> regizori;
    private List<Scenarist> scenaristi;
    private List<Film> filme;

    private final String DATA_DIR = "data";
    private final String ACTORI_FILE = DATA_DIR + "actori.dat";
    private final String REGIZORI_FILE = DATA_DIR + "regizori.dat";
    private final String SCENARISTI_FILE = DATA_DIR + "scenaristi.dat";
    private final String FILME_FILE = DATA_DIR + "filme.dat";

    private DataRepository() {
        actori = new ArrayList<>();
        regizori = new ArrayList<>();
        scenaristi = new ArrayList<>();
        filme = new ArrayList<>();
        incarcaDate();
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    // ACTORI
    public void adaugaActor(Actor actor) {
        actori.add(actor);
        salveazaActori();
    }

    public void actualizeazaActor(Actor actor) {
        for (int i = 0; i < actori.size(); i++) {
            if (actori.get(i).getId().equals(actor.getId())) {
                actori.set(i, actor);
                salveazaActori();
                return;
            }
        }
    }

    public void stergeActor(String id) {
        actori.removeIf(a -> a.getId().equals(id));
        salveazaActori();
    }

    public List<Actor> getActori() {
        return new ArrayList<>(actori);
    }

    public Actor getActorById(String id) {
        return actori.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // REGIZORI
    public void adaugaRegizor(Regizor regizor) {
        regizori.add(regizor);
        salveazaRegizori();
    }

    public void actualizeazaRegizor(Regizor regizor) {
        for (int i = 0; i < regizori.size(); i++) {
            if (regizori.get(i).getId().equals(regizor.getId())) {
                regizori.set(i, regizor);
                salveazaRegizori();
                return;
            }
        }
    }

    public void stergeRegizor(String id) {
        regizori.removeIf(r -> r.getId().equals(id));
        salveazaRegizori();
    }

    public List<Regizor> getRegizori() {
        return new ArrayList<>(regizori);
    }

    public Regizor getRegizorById(String id) {
        return regizori.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // SCENARISTI
    public void adaugaScenarist(Scenarist scenarist) {
        scenaristi.add(scenarist);
        salveazaScenaristi();
    }

    public void actualizeazaScenarist(Scenarist scenarist) {
        for (int i = 0; i < scenaristi.size(); i++) {
            if (scenaristi.get(i).getId().equals(scenarist.getId())) {
                scenaristi.set(i, scenarist);
                salveazaScenaristi();
                return;
            }
        }
    }

    public void stergeScenarist(String id) {
        scenaristi.removeIf(s -> s.getId().equals(id));
        salveazaScenaristi();
    }

    public List<Scenarist> getScenaristi() {
        return new ArrayList<>(scenaristi);
    }

    public Scenarist getScenaristById(String id) {
        return scenaristi.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // FILME
    public void adaugaFilm(Film film) {
        filme.add(film);
        salveazaFilme();
    }

    public void actualizeazaFilm(Film film) {
        for (int i = 0; i < filme.size(); i++) {
            if (filme.get(i).getId().equals(film.getId())) {
                filme.set(i, film);
                salveazaFilme();
                return;
            }
        }
    }

    public void stergeFilm(String id) {
        filme.removeIf(f -> f.getId().equals(id));
        salveazaFilme();
    }

    public List<Film> getFilme() {
        return new ArrayList<>(filme);
    }

    public Film getFilmById(String id) {
        return filme.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Film> getFilmeSortateDeupaTip() {
        return filme.stream()
                .sorted(Comparator.comparing(Film::getTipFilm))
                .collect(Collectors.toList());
    }

    public List<Film> filtreazaFilme(TipFilm tip, CategorieFilm categorie, Integer an) {
        return filme.stream()
                .filter(f -> tip == null || f.getTipFilm() == tip)
                .filter(f -> categorie == null || f.getCategorieFilm() == categorie)
                .filter(f -> an == null || f.getAnRealizare() == an)
                .collect(Collectors.toList());
    }

    public List<Film> cautaFilmeCuActor(String actorId) {
        return filme.stream()
                .filter(f -> f.getActorIds().contains(actorId))
                .collect(Collectors.toList());
    }

    // PERSISTENȚĂ
    private void incarcaDate() {
        actori = incarcaLista(ACTORI_FILE);
        regizori = incarcaLista(REGIZORI_FILE);
        scenaristi = incarcaLista(SCENARISTI_FILE);
        filme = incarcaLista(FILME_FILE);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> incarcaLista(String numeFisier) {
        File file = new File(numeFisier);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            System.err.println("Eroare la încărcarea datelor din " + numeFisier + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void salveazaActori() {
        salveazaLista(actori, ACTORI_FILE);
    }

    private void salveazaRegizori() {
        salveazaLista(regizori, REGIZORI_FILE);
    }

    private void salveazaScenaristi() {
        salveazaLista(scenaristi, SCENARISTI_FILE);
    }

    private void salveazaFilme() {
        salveazaLista(filme, FILME_FILE);
    }

    private <T> void salveazaLista(List<T> lista, String numeFisier) {
        File file = new File(numeFisier);
        file.getParentFile().mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(lista);
        } catch (Exception e) {
            System.err.println("Eroare la salvarea datelor în " + numeFisier + ": " + e.getMessage());
        }
    }
}
