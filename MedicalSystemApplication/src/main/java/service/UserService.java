package service;

import java.security.Principal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import model.*;
import model.User.UserRole;
import repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;


	public void delete(User user) {
		userRepository.delete(user);
	}

	public List<User> getAll(UserRole role) {
		return userRepository.findAllByRole(role);

	}

	public List<User> getAllByRole(UserRole role) {
		return userRepository.findAllByRole(role);

	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User findById(Long id) {
		Optional<User> user = userRepository.findById(id);

		if (user.isPresent()) {
			return user.get();
		}

		return null;

	}

	public User findByEmailAndDeleted(String email, Boolean deleted) {
		return userRepository.findByEmailAndDeleted(email, deleted);
	}

	public User findUserById(Long userId) {
		return userRepository.findUserById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	public void save(User user) {
		userRepository.save(user);

	}

	private User getUserByPrincipal(Principal principal) {
		String username = principal.getName();
		return userRepository.findUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
	}
}
