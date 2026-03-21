package presenter;

import model.Regizor;
import model.repository.DataRepository;

import java.util.List;

public class RegizorPresenter {
    private DataRepository repository;

    public RegizorPresenter() {
        this.repository = DataRepository.getInstance();
    }

    public void adaugaRegizor(String nume, String prenume, int anNastere, String nationalitate) {
        Regizor regizor = new Regizor(nume, prenume, anNastere, nationalitate);
        repository.adaugaRegizor(regizor);
    }

    public void actualizeazaRegizor(String id, String nume, String prenume, int anNastere, String nationalitate) {
        Regizor regizor = repository.getRegizorById(id);
        if (regizor != null) {
            regizor.setNume(nume);
            regizor.setPrenume(prenume);
            regizor.setAnNastere(anNastere);
            regizor.setNationalitate(nationalitate);
            repository.actualizeazaRegizor(regizor);
        }
    }

    public void stergeRegizor(String id) {
        repository.stergeRegizor(id);
    }

    public List<Regizor> getRegizori() {
        return repository.getRegizori();
    }

    public Regizor getRegizorById(String id) {
        return repository.getRegizorById(id);
    }
}