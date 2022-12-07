package service;

import dto.ReviewDTO;
import helpers.DateUtil;
import model.Centre;
import model.CentreReview;
import model.Doctor;
import model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import repository.CentreRepository;
import repository.UserRepository;


import javax.validation.ValidationException;
import java.util.List;

@Service
public class CentreService {

    @Autowired
    private CentreRepository centreRepository;

    @Autowired
    private UserRepository userRepository;

    public Centre findByName(String name) {
        return centreRepository.findByName(name);
    }

    public Centre findByDoctor(Doctor d) {
        return centreRepository.findByDoctors(d);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void rateCentreSafe(ReviewDTO dto)  {
        Centre centre = findByName(dto.getCentreName());
        Patient patient = (Patient) userRepository.findByEmailAndDeleted(dto.getPatientEmail(), false);

        if (centre == null || patient == null) {
            throw new ValidationException("Centre or patient not found");
        }

        CentreReview cr = new CentreReview(dto.getRating(), DateUtil.getInstance().now("dd-MM-yyyy"), patient);
        centre.getReviews().add(cr);
        centre.calculateRating();

        save(centre);
    }

    public void save(Centre centre) {
        centreRepository.save(centre);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Centre> findAllSafe() {
        return centreRepository.findAll();
    }

    public List<Centre> findAll() {
        return centreRepository.findAll();
    }
}
