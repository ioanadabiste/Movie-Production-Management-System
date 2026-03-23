package presenter;

import model.Regizor;
import model.repository.RegizorRepository;

public class RegizorPresenter extends PersonaPresenter<Regizor, RegizorRepository> {

    public RegizorPresenter(RegizorRepository repository) {
        super(repository);
    }

    @Override
    protected Regizor createInstance(String nume, String prenume, int anNastere, String nationalitate) {
        return new Regizor(nume, prenume, anNastere, nationalitate);
    }
}
