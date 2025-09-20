package gaetanomiscio.Capstone.services;

import gaetanomiscio.Capstone.entities.User;
import gaetanomiscio.Capstone.enums.Role;
import gaetanomiscio.Capstone.exceptions.BadRequestException;
import gaetanomiscio.Capstone.exceptions.NotFoundException;
import gaetanomiscio.Capstone.exceptions.UnauthorizedException;
import gaetanomiscio.Capstone.payload.LoginDTO;
import gaetanomiscio.Capstone.payload.UserDTO;
import gaetanomiscio.Capstone.payload.UserRespDTO;
import gaetanomiscio.Capstone.repositories.UserRepository;
import gaetanomiscio.Capstone.tools.JWTTools;
import gaetanomiscio.Capstone.tools.MailgunSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private MailgunSender mailgunSender;


    public String authenticateUser(LoginDTO body) {
        User user = userRepository.findByEmail(body.email()).orElseThrow(() -> new NotFoundException("Email non trovata."));
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }

    public UserRespDTO registerUser(UserDTO body) {
        if (userRepository.existsByEmail(body.email())) {
            throw new BadRequestException("L'email " + body.email() + " è già in uso!");
        }
        User newUser = new User();
        newUser.setUsername(body.username());
        newUser.setEmail(body.email());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setRole(body.role() != null ? body.role() : Role.USER);
        User savedUser = userRepository.save(newUser);
        mailgunSender.sendRegistrationEmail(savedUser);
        return new UserRespDTO(savedUser.getId());
    }

}
