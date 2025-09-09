package gaetanomiscio.Capstone.services;

import gaetanomiscio.Capstone.entities.User;
import gaetanomiscio.Capstone.enums.Role;
import gaetanomiscio.Capstone.exceptions.NotFoundException;
import gaetanomiscio.Capstone.payload.UserDTO;
import gaetanomiscio.Capstone.payload.UserRespDTO;
import gaetanomiscio.Capstone.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UsersRepository usersRepository;
    //private final PasswordEncoder passwordEncoder;

    public User createUser(UserDTO payload) {
        if (usersRepository.existsByEmail(payload.email())) {
            throw new IllegalArgumentException("Email già registrata");
        }
        User u = new User();
        u.setUsername(payload.username());
        u.setEmail(payload.email());
        // u.setPassword(passwordEncoder.encode(payload.password()));
        u.setRole(Role.USER);

        return usersRepository.save(u);
    }

    public User findById(UUID id) {
        return usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User con id: " + id + "non trovato."));
    }

    public List<User> findAllUsers() {
        return usersRepository.findAll();
    }

    public UserRespDTO updateUser(UUID id, UserDTO payload) {
        User u = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User con id: " + id + "non trovato."));
        u.setUsername(payload.username());
        u.setEmail(payload.email());
        //u.setPassword(passwordEncoder.encode(dto.password()));
        User saved = usersRepository.save(u);
        return new UserRespDTO(saved.getId());
    }


    public void deleteUser(UUID id) {
        if (!usersRepository.existsById(id)) throw new NotFoundException(id);
        usersRepository.deleteById(id);
    }
}
