package service;

import model.Diagnosis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.DiagnosisRepository;

import java.util.List;

@Service
public class DiagnosisService {

    @Autowired
    public DiagnosisRepository diagnosisRepository;

    public Diagnosis findByCode(String code) {
        return diagnosisRepository.findByCode(code);
    }

    public Diagnosis findByName(String name) {
        return diagnosisRepository.findByName(name);
    }

    public void save(Diagnosis diagnosis) {
        diagnosisRepository.save(diagnosis);
    }

    public void delete(Diagnosis diagnosis) {
        diagnosisRepository.delete(diagnosis);
    }

    public List<Diagnosis> findAll() {
        return diagnosisRepository.findAll();
    }
}
