package service;

import model.CentreAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CentreAdminRepository;
import repository.UserRepository;

@Service
public class CentreAdminService {

    @Autowired
    private CentreAdminRepository centreAdminRepository;

    @Autowired
    private UserRepository userRepository;

    public CentreAdmin findByEmail(String email) {
        return centreAdminRepository.findByEmail(email);
    }

    public void save(CentreAdmin centreAdmin) {
        centreAdminRepository.save(centreAdmin);
    }
}

