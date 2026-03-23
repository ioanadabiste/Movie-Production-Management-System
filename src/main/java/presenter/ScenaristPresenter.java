package presenter;

import model.Scenarist;
import model.repository.ScenaristRepository;

public class ScenaristPresenter extends PersonaPresenter<Scenarist, ScenaristRepository> {

    public ScenaristPresenter(ScenaristRepository repository) {
        super(repository);
    }

    @Override
    protected Scenarist createInstance(String nume, String prenume, int anNastere, String nationalitate) {
        return new Scenarist(nume, prenume, anNastere, nationalitate);
    }
}
