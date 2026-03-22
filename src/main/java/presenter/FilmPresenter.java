package presenter;

import model.*;
import model.repository.ActorRepository;
import model.repository.RegizorRepository;
import model.repository.ScenaristRepository;
import model.repository.FilmRepository;

import java.util.List;

public class FilmPresenter {
    private FilmRepository filmRepository;
    private ActorRepository actorRepository;
    private RegizorRepository regizorRepository;
    private ScenaristRepository scenaristRepository;

    public FilmPresenter() {
        this.filmRepository = FilmRepository.getInstance();
        this.actorRepository = ActorRepository.getInstance();
        this.regizorRepository = RegizorRepository.getInstance();
        this.scenaristRepository = ScenaristRepository.getInstance();
    }

    // Filme
    public Film getFilmById(String id) {
        return filmRepository.getFilmById(id);
    }

    public List<Film> getToateFilmele() {
        return filmRepository.getFilme();
    }

    // Actori
    public Actor getActorById(String id) {
        return actorRepository.getActorById(id);
    }

    // Regizori
    public Regizor getRegizorById(String id) {
        return regizorRepository.getRegizorById(id);
    }

    // Scenaristi
    public Scenarist getScenaristById(String id) {
        return scenaristRepository.getScenaristById(id);
    }
}