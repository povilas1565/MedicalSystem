package repository;

import model.Centre;
import model.Priceslist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceListRepository extends JpaRepository<Priceslist, Long> {

    public List<Priceslist> findAllByCentre(Centre c);

    public Priceslist findByTypeOfExaminationAndCentreAndDeleted(String typeOfExamination, Centre centre, Boolean deleted);

    public Priceslist findByTypeOfExaminationAndDeleted(String typeOfExamination, Boolean deleted);

    public List<Priceslist> findAllByPrice(Long price);

    public List<Priceslist> findAllByTypeOfExamination(String typeOfExamination);

}
