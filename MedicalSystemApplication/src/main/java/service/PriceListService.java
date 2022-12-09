package service;

import model.Centre;
import model.Priceslist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CentreRepository;
import repository.PriceListRepository;

import java.util.List;

@Service
public class PriceListService {

    @Autowired
    private PriceListRepository priceListRepository;
    @Autowired
    private CentreRepository centreRepository;

    public Priceslist findByTypeOfExaminationAndCentre(String typeOfExamination, String centreName) {

        Centre centre = centreRepository.findByName(centreName);
        return priceListRepository.findByTypeOfExaminationAndCentreAndDeleted(typeOfExamination, centre, false);
    }

    public Priceslist findByTypeOfExaminationAndCentre(String typeOfExamination, Centre centre) {
        return priceListRepository.findByTypeOfExaminationAndCentreAndDeleted(typeOfExamination, centre, false);
    }

    public List<Priceslist> findAllByCentre(Centre c) {
        return priceListRepository.findAllByCentre(c);
    }

    public List<Priceslist> findAllByPrice(Long price) {
        return priceListRepository.findAllByPrice(price);
    }

    public List<Priceslist> findAllByTypeOfExamination(String typeOfExamination) {
        return priceListRepository.findAllByTypeOfExamination(typeOfExamination);
    }

    public Priceslist findByTypeOfExaminationAndDeleted(String typeOfExamination, Boolean deleted) {
        return priceListRepository.findByTypeOfExaminationAndDeleted(typeOfExamination, deleted);
    }

    public void save(Priceslist priceslist) {
        priceListRepository.save(priceslist);
    }

    public void delete(Priceslist priceslist) {
        priceListRepository.delete(priceslist);
    }

    public List<Priceslist> findAll() {
        return priceListRepository.findAll();
    }
}
