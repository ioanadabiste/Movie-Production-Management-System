package presenter;

import model.Persoana;
import model.repository.PersonaRepository;

import java.util.List;

public abstract class PersonaPresenter<T extends Persoana, R extends PersonaRepository<T>> {

    protected final R repository;

    public PersonaPresenter(R repository) {
        this.repository = repository;
    }

    // Subclasele știu cum să construiască o instanță T cu parametrii dați
    protected abstract T createInstance(String nume, String prenume, int anNastere, String nationalitate);

    public void adauga(String nume, String prenume, int anNastere, String nationalitate) {
        T persoana = createInstance(nume, prenume, anNastere, nationalitate);
        repository.adauga(persoana);
    }

    public void actualizeaza(String id, String nume, String prenume, int anNastere, String nationalitate) {
        T persoana = repository.getById(id);
        if (persoana != null) {
            persoana.setNume(nume);
            persoana.setPrenume(prenume);
            persoana.setAnNastere(anNastere);
            persoana.setNationalitate(nationalitate);
            repository.actualizeaza(persoana);
        }
    }

    public void sterge(String id) {
        repository.sterge(id);
    }

    public List<T> getAll() {
        return repository.getAll();
    }

    public T getById(String id) {
        return repository.getById(id);
    }
}