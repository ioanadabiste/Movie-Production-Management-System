package presenter;

import model.Scenarist;
import model.repository.DataRepository;

import java.util.List;

public class ScenaristPresenter {
    private DataRepository repository;

    public ScenaristPresenter() {
        this.repository = DataRepository.getInstance();
    }

    public void adaugaScenarist(String nume, String prenume, int anNastere, String nationalitate) {
        Scenarist scenarist = new Scenarist(nume, prenume, anNastere, nationalitate);
        repository.adaugaScenarist(scenarist);
    }

    public void actualizeazaScenarist(String id, String nume, String prenume, int anNastere, String nationalitate) {
        Scenarist scenarist = repository.getScenaristById(id);
        if (scenarist != null) {
            scenarist.setNume(nume);
            scenarist.setPrenume(prenume);
            scenarist.setAnNastere(anNastere);
            scenarist.setNationalitate(nationalitate);
            repository.actualizeazaScenarist(scenarist);
        }
    }

    public void stergeScenarist(String id) {
        repository.stergeScenarist(id);
    }

    public List<Scenarist> getScenaristi() {
        return repository.getScenaristi();
    }

    public Scenarist getScenaristById(String id) {
        return repository.getScenaristById(id);
    }
}