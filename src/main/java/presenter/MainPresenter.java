package presenter;

import model.*;

import java.util.List;

public class MainPresenter {

    private ActorPresenter actorPresenter;
    private FilmPresenter filmPresenter;
    private RegizorPresenter regizorPresenter;
    private ScenaristPresenter scenaristPresenter;

    public MainPresenter() {
        actorPresenter = new ActorPresenter();
        filmPresenter = new FilmPresenter();
        regizorPresenter = new RegizorPresenter();
        scenaristPresenter = new ScenaristPresenter();
    }

    // ===================== ACTORI =====================

    public void adaugaActor(String nume, String prenume, int anNastere, String nationalitate) {
        actorPresenter.adaugaActor(nume, prenume, anNastere, nationalitate);
    }

    public void actualizeazaActor(String id, String nume, String prenume, int anNastere, String nationalitate) {
        actorPresenter.actualizeazaActor(id, nume, prenume, anNastere, nationalitate);
    }

    public void stergeActor(String id) {
        actorPresenter.stergeActor(id);
    }

    public List<Actor> getActori() {
        return actorPresenter.getActori();
    }

    public Actor getActorById(String id) {
        return actorPresenter.getActorById(id);
    }

    // ===================== REGIZORI =====================

    public void adaugaRegizor(String nume, String prenume, int anNastere, String nationalitate) {
        regizorPresenter.adaugaRegizor(nume, prenume, anNastere, nationalitate);
    }

    public void actualizeazaRegizor(String id, String nume, String prenume, int anNastere, String nationalitate) {
        regizorPresenter.actualizeazaRegizor(id, nume, prenume, anNastere, nationalitate);
    }

    public void stergeRegizor(String id) {
        regizorPresenter.stergeRegizor(id);
    }

    public List<Regizor> getRegizori() {
        return regizorPresenter.getRegizori();
    }

    public Regizor getRegizorById(String id) {
        return regizorPresenter.getRegizorById(id);
    }

    // ===================== SCENARISTI =====================

    public void adaugaScenarist(String nume, String prenume, int anNastere, String nationalitate) {
        scenaristPresenter.adaugaScenarist(nume, prenume, anNastere, nationalitate);
    }

    public void actualizeazaScenarist(String id, String nume, String prenume, int anNastere, String nationalitate) {
        scenaristPresenter.actualizeazaScenarist(id, nume, prenume, anNastere, nationalitate);
    }

    public void stergeScenarist(String id) {
        scenaristPresenter.stergeScenarist(id);
    }

    public List<Scenarist> getScenaristi() {
        return scenaristPresenter.getScenaristi();
    }

    public Scenarist getScenaristById(String id) {
        return scenaristPresenter.getScenaristById(id);
    }

    // ===================== FILME =====================

    public void adaugaFilm(String titlu, int an, TipFilm tip, CategorieFilm categorie, String descriere) {
        filmPresenter.adaugaFilm(titlu, an, tip, categorie, descriere);
    }

    public void actualizeazaFilm(String id, String titlu, int an, TipFilm tip,
                                 CategorieFilm categorie, String descriere,
                                 String regizorId, String scenaristId,
                                 List<String> actorIds, List<String> imagini) {

        filmPresenter.actualizeazaFilm(id, titlu, an, tip, categorie,
                descriere, regizorId, scenaristId, actorIds, imagini);
    }

    public void stergeFilm(String id) {
        filmPresenter.stergeFilm(id);
    }

    public List<Film> getFilme() {
        return filmPresenter.getFilme();
    }

    public Film getFilmById(String id) {
        return filmPresenter.getFilmById(id);
    }

    public List<Film> getFilmeSortateDeupaTip() {
        return filmPresenter.getFilmeSortateDeupaTip();
    }

    public List<Film> filtreazaFilme(TipFilm tip, CategorieFilm categorie, Integer an) {
        return filmPresenter.filtreazaFilme(tip, categorie, an);
    }

    public List<Film> cautaFilmeCuActor(String actorId) {
        return filmPresenter.cautaFilmeCuActor(actorId);
    }
}