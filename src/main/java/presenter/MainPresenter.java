package presenter;

import model.*;
import model.repository.ActorRepository;
import model.repository.FilmRepository;
import model.repository.RegizorRepository;
import model.repository.ScenaristRepository;

import java.util.List;

public class MainPresenter {

    private final ActorPresenter actorPresenter;
    private final RegizorPresenter regizorPresenter;
    private final ScenaristPresenter scenaristPresenter;
    private final FilmPresenter filmPresenter;

    public MainPresenter(FilmRepository filmRepo,
                         ActorRepository actorRepo,
                         RegizorRepository regizorRepo,
                         ScenaristRepository scenaristRepo) {

        this.actorPresenter     = new ActorPresenter(actorRepo);
        this.regizorPresenter   = new RegizorPresenter(regizorRepo);
        this.scenaristPresenter = new ScenaristPresenter(scenaristRepo);
        this.filmPresenter      = new FilmPresenter(filmRepo, actorRepo, regizorRepo, scenaristRepo);
    }

    // --- Getteri pentru sub-presentere (folosite de View-uri specializate) ---

    public ActorPresenter getActorPresenter()         { return actorPresenter; }
    public RegizorPresenter getRegizorPresenter()     { return regizorPresenter; }
    public ScenaristPresenter getScenaristPresenter() { return scenaristPresenter; }
    public FilmPresenter getFilmPresenter()           { return filmPresenter; }

    // --- ACTORI ---

    public void adaugaActor(String nume, String prenume, int anNastere, String nationalitate) {
        actorPresenter.adauga(nume, prenume, anNastere, nationalitate);
    }

    public void actualizeazaActor(String id, String nume, String prenume, int anNastere, String nationalitate) {
        actorPresenter.actualizeaza(id, nume, prenume, anNastere, nationalitate);
    }

    public void stergeActor(String id)            { actorPresenter.sterge(id); }
    public List<Actor> getActori()                { return actorPresenter.getAll(); }
    public Actor getActorById(String id)          { return actorPresenter.getById(id); }

    // --- REGIZORI ---

    public void adaugaRegizor(String nume, String prenume, int anNastere, String nationalitate) {
        regizorPresenter.adauga(nume, prenume, anNastere, nationalitate);
    }

    public void actualizeazaRegizor(String id, String nume, String prenume, int anNastere, String nationalitate) {
        regizorPresenter.actualizeaza(id, nume, prenume, anNastere, nationalitate);
    }

    public void stergeRegizor(String id)          { regizorPresenter.sterge(id); }
    public List<Regizor> getRegizori()            { return regizorPresenter.getAll(); }
    public Regizor getRegizorById(String id)      { return regizorPresenter.getById(id); }

    // --- SCENARISTI ---

    public void adaugaScenarist(String nume, String prenume, int anNastere, String nationalitate) {
        scenaristPresenter.adauga(nume, prenume, anNastere, nationalitate);
    }

    public void actualizeazaScenarist(String id, String nume, String prenume, int anNastere, String nationalitate) {
        scenaristPresenter.actualizeaza(id, nume, prenume, anNastere, nationalitate);
    }

    public void stergeScenarist(String id)        { scenaristPresenter.sterge(id); }
    public List<Scenarist> getScenaristi()        { return scenaristPresenter.getAll(); }
    public Scenarist getScenaristById(String id)  { return scenaristPresenter.getById(id); }

    // --- FILME ---

    public void adaugaFilm(String titlu, int an, TipFilm tip, CategorieFilm categorie,
                           String descriere, String regizorId, String scenaristId,
                           List<String> actorIds, List<String> imagini) {
        filmPresenter.adaugaFilm(titlu, an, tip, categorie, descriere, regizorId, scenaristId, actorIds, imagini);
    }

    public void actualizeazaFilm(String id, String titlu, int an, TipFilm tip,
                                 CategorieFilm categorie, String descriere,
                                 String regizorId, String scenaristId,
                                 List<String> actorIds, List<String> imagini) {
        filmPresenter.actualizeazaFilm(id, titlu, an, tip, categorie, descriere, regizorId, scenaristId, actorIds, imagini);
    }

    public void stergeFilm(String id)             { filmPresenter.stergeFilm(id); }
    public List<Film> getFilme()                  { return filmPresenter.getFilme(); }
    public Film getFilmById(String id)            { return filmPresenter.getFilmById(id); }

    public List<Film> getFilmeSortateDeupaTip()   { return filmPresenter.getFilmeSortateDeupaTip(); }

    public List<Film> filtreazaFilme(TipFilm tip, CategorieFilm categorie, Integer an) {
        return filmPresenter.filtreazaFilme(tip, categorie, an);
    }

    public List<Film> cautaFilmeCuActor(String actorId) {
        return filmPresenter.cautaFilmeCuActor(actorId);
    }
}