package presenter;

import model.*;
import model.repository.DataRepository;

import java.util.List;

public class FilmPresenter {
    private DataRepository repository;

    public FilmPresenter() {
        this.repository = DataRepository.getInstance();
    }

    public void adaugaFilm(String titlu, int anRealizare, TipFilm tipFilm,
                           CategorieFilm categorieFilm, String descriere) {
        Film film = new Film(titlu, anRealizare, tipFilm, categorieFilm);
        film.setDescriere(descriere);
        repository.adaugaFilm(film);
    }

    public void actualizeazaFilm(String id, String titlu, int anRealizare,
                                 TipFilm tipFilm, CategorieFilm categorieFilm,
                                 String descriere, String regizorId, String scenaristId,
                                 List<String> actorIds, List<String> caiImagini) {
        Film film = repository.getFilmById(id);
        if (film != null) {
            film.setTitlu(titlu);
            film.setAnRealizare(anRealizare);
            film.setTipFilm(tipFilm);
            film.setCategorieFilm(categorieFilm);
            film.setDescriere(descriere);
            film.setRegizorId(regizorId);
            film.setScenaristId(scenaristId);
            film.setActorIds(actorIds);
            film.setCaiImagini(caiImagini);
            repository.actualizeazaFilm(film);
        }
    }

    public void stergeFilm(String id) {
        repository.stergeFilm(id);
    }

    public List<Film> getFilme() {
        return repository.getFilme();
    }

    public List<Film> getFilmeSortateDeupaTip() {
        return repository.getFilmeSortateDeupaTip();
    }

    public Film getFilmById(String id) {
        return repository.getFilmById(id);
    }

    public List<Film> filtreazaFilme(TipFilm tip, CategorieFilm categorie, Integer an) {
        return repository.filtreazaFilme(tip, categorie, an);
    }

    public List<Film> cautaFilmeCuActor(String actorId) {
        return repository.cautaFilmeCuActor(actorId);
    }

    public List<Actor> getActori() {
        return repository.getActori();
    }

    public List<Regizor> getRegizori() {
        return repository.getRegizori();
    }

    public List<Scenarist> getScenaristi() {
        return repository.getScenaristi();
    }

    public Actor getActorById(String id) {
        return repository.getActorById(id);
    }

    public Regizor getRegizorById(String id) {
        return repository.getRegizorById(id);
    }

    public Scenarist getScenaristById(String id) {
        return repository.getScenaristById(id);
    }
}