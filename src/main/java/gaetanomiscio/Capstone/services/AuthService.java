package gaetanomiscio.Capstone.services;

import gaetanomiscio.Capstone.entities.User;
import gaetanomiscio.Capstone.exceptions.BadRequestException;
import gaetanomiscio.Capstone.exceptions.UnauthorizedException;
import gaetanomiscio.Capstone.payload.LoginDTO;
import gaetanomiscio.Capstone.payload.UserDTO;
import gaetanomiscio.Capstone.payload.UserRespDTO;
import gaetanomiscio.Capstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthService {
    @Autowired
    private UserRepository userRepository;
    //   private PasswordEncoder passwordEncoder;
    //   private JWTTools jwtTools;

    public String authenticateUser(LoginDTO body) {
        User user = userRepository.findByEmail(body.email());
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }

    public UserRespDTO register(UserDTO body) {
        userRepository.existsByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + body.getEmail() + " è già in uso!");
        });
        User newUser = new User();
        newUser.setUsername(body.username());
        newUser.setEmail(body.email());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        User savedUser = userRepository.save(newUser);
        return new UserRespDTO(savedUser.getId());
    }


}
