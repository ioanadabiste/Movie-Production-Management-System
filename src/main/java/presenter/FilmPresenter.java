package presenter;

import model.*;
import model.repository.ActorRepository;
import model.repository.RegizorRepository;
import model.repository.ScenaristRepository;
import model.repository.FilmRepository;

import java.util.ArrayList;
import java.util.List;

public class FilmPresenter {
    private final FilmRepository filmRepository;
    private final ActorRepository actorRepository;
    private final RegizorRepository regizorRepository;
    private final ScenaristRepository scenaristRepository;

    public FilmPresenter(FilmRepository filmRepository,
                         ActorRepository actorRepository,
                         RegizorRepository regizorRepository,
                         ScenaristRepository scenaristRepository) {
        this.filmRepository = filmRepository;
        this.actorRepository = actorRepository;
        this.regizorRepository = regizorRepository;
        this.scenaristRepository = scenaristRepository;
    }

    // FILME — neschimbat

    public void adaugaFilm(String titlu, int an, TipFilm tip, CategorieFilm categorie,
                           String descriere, String regizorId, String scenaristId,
                           List<String> actorIds, List<String> imagini) {
        Film film = new Film();
        film.setId(java.util.UUID.randomUUID().toString());
        film.setTitlu(titlu);
        film.setAnRealizare(an);
        film.setTipFilm(tip);
        film.setCategorieFilm(categorie);
        film.setDescriere(descriere);
        film.setRegizorId(regizorId);
        film.setScenaristId(scenaristId);
        film.setActorIds(actorIds != null ? actorIds : new ArrayList<>());
        film.setCaiImagini(imagini != null ? imagini : new ArrayList<>());
        filmRepository.adaugaFilm(film);
    }

    public void actualizeazaFilm(String id, String titlu, int an, TipFilm tip,
                                 CategorieFilm categorie, String descriere,
                                 String regizorId, String scenaristId,
                                 List<String> actorIds, List<String> imagini) {
        Film film = filmRepository.getFilmById(id);
        if (film != null) {
            film.setTitlu(titlu);
            film.setAnRealizare(an);
            film.setTipFilm(tip);
            film.setCategorieFilm(categorie);
            film.setDescriere(descriere);
            film.setRegizorId(regizorId);
            film.setScenaristId(scenaristId);
            film.setActorIds(actorIds);
            film.setCaiImagini(imagini);
            filmRepository.actualizeazaFilm(film);
        }
    }

    public void stergeFilm(String id)          { filmRepository.stergeFilm(id); }
    public Film getFilmById(String id)          { return filmRepository.getFilmById(id); }
    public List<Film> getFilme()                { return filmRepository.getFilme(); }
    public List<Film> getToateFilmele()         { return filmRepository.getFilme(); }
    public List<Film> getFilmeSortateDeupaTip() { return filmRepository.getFilmeSortateDeupaTip(); }

    public List<Film> filtreazaFilme(TipFilm tip, CategorieFilm categorie, Integer an) {
        return filmRepository.filtreazaFilme(tip, categorie, an);
    }

    public List<Film> cautaFilmeCuActor(String actorId) {
        return filmRepository.cautaFilmeCuActor(actorId);
    }


    public Actor getActorById(String id)  { return actorRepository.getById(id); }
    public List<Actor> getActori()        { return actorRepository.getAll(); }


    public Regizor getRegizorById(String id)  { return regizorRepository.getById(id); }
    public List<Regizor> getRegizori()        { return regizorRepository.getAll(); }


    public Scenarist getScenaristById(String id)  { return scenaristRepository.getById(id); }
    public List<Scenarist> getScenaristi()        { return scenaristRepository.getAll(); }
}