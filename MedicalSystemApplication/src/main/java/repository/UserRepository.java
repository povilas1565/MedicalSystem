package repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.User;
import model.User.UserRole;

public interface UserRepository extends JpaRepository<User,Long>{
	
	public User findByEmailAndDeleted(String email, Boolean deleted);

	Optional<User> findUserByUsername(String username);

	Optional<User> findUserByEmail(String email);

	Optional<User> findUserById(Long id);

	List<User> findAllByRole(UserRole role);

}
