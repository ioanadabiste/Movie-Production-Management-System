package presenter;

import model.Actor;
import model.repository.ActorRepository;

public class ActorPresenter extends PersonaPresenter<Actor, ActorRepository> {

    public ActorPresenter(ActorRepository repository) {
        super(repository);
    }

    @Override
    protected Actor createInstance(String nume, String prenume, int anNastere, String nationalitate) {
        return new Actor(nume, prenume, anNastere, nationalitate);
    }
}
