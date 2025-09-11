package gaetanomiscio.Capstone.services;

import gaetanomiscio.Capstone.entities.User;
import gaetanomiscio.Capstone.enums.Role;
import gaetanomiscio.Capstone.exceptions.NotFoundException;
import gaetanomiscio.Capstone.payload.UserDTO;
import gaetanomiscio.Capstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    public User createUser(UserDTO payload) {
        if (userRepository.existsByEmail(payload.email())) {
            throw new IllegalArgumentException("Email già registrata");
        }
        User u = new User();
        u.setUsername(payload.username());
        u.setEmail(payload.email());
        // u.setPassword(passwordEncoder.encode(payload.password()));
        u.setRole(Role.USER);

        return userRepository.save(u);
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User con id: " + id + "non trovato."));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User updateRole(UUID userId, Role newRole) {
        User found = this.findById(userId);
        found.setRole(newRole);
        return userRepository.save(found);
    }

}
