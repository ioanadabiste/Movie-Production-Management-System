package presenter;

import model.Actor;
import model.repository.ActorRepository;

import java.util.List;

public class ActorPresenter {
        private ActorRepository repository;

    public ActorPresenter(ActorRepository repository) {
        this.repository = repository;
    }

    public void adaugaActor(String nume, String prenume, int anNastere, String nationalitate) {
        Actor actor = new Actor(nume, prenume, anNastere, nationalitate);
        repository.adaugaActor(actor);
    }

    public void actualizeazaActor(String id, String nume, String prenume, int anNastere, String nationalitate) {
        Actor actor = repository.getActorById(id);
        if (actor != null) {
            actor.setNume(nume);
            actor.setPrenume(prenume);
            actor.setAnNastere(anNastere);
            actor.setNationalitate(nationalitate);
            repository.actualizeazaActor(actor);
        }
    }

    public void stergeActor(String id) {
        repository.stergeActor(id);
    }

    public List<Actor> getActori() {
        return repository.getActori();
    }

    public Actor getActorById(String id) {
        return repository.getActorById(id);
    }
}