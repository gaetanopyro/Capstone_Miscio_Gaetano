package gaetanomiscio.Capstone.controllers;

import gaetanomiscio.Capstone.exceptions.ValidationException;
import gaetanomiscio.Capstone.payload.LoginDTO;
import gaetanomiscio.Capstone.payload.LoginRespDTO;
import gaetanomiscio.Capstone.payload.UserDTO;
import gaetanomiscio.Capstone.payload.UserRespDTO;
import gaetanomiscio.Capstone.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody @Validated LoginDTO payload) {
        return authService.authenticateUser(payload);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRespDTO register(@RequestBody @Validated UserDTO newUser, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            throw new ValidationException(errors);
        }
        return authService.registerUser(newUser);
    }


}
