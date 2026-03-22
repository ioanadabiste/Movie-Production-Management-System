package presenter;

import model.Scenarist;
import model.repository.ScenaristRepository;
import java.util.List;

public class ScenaristPresenter {
    private ScenaristRepository repository;

    public ScenaristPresenter() {
        this.repository = ScenaristRepository.getInstance();
    }

    public void adaugaScenarist(String nume, String prenume, int anNastere, String nationalitate) {
        Scenarist scenarist = new Scenarist(nume, prenume, anNastere, nationalitate);
        repository.adauga(scenarist);
    }

    public void actualizeazaScenarist(String id, String nume, String prenume, int anNastere, String nationalitate) {
        Scenarist scenarist = repository.getById(id);
        if (scenarist != null) {
            scenarist.setNume(nume);
            scenarist.setPrenume(prenume);
            scenarist.setAnNastere(anNastere);
            scenarist.setNationalitate(nationalitate);
            repository.update(scenarist);
        }
    }

    public void stergeScenarist(String id) {
        repository.delete(id);
    }

    public List<Scenarist> getScenaristi() {
        return repository.getAll();
    }

    public Scenarist getScenaristById(String id) {
        return repository.getById(id);
    }
}