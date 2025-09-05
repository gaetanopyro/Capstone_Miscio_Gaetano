package gaetanomiscio.Capstone.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotEmpty(message = "Il nome è obbligatorio!")
        @Size(min = 2, max = 40, message = "Il nome deve essere di lunghezza compresa tra 2 e 40")
        String username,
        @NotEmpty(message = "L'email è obbligatoria!")
        @Email(message = "Formato email non valido!")
        String email,
        @NotEmpty(message = "La password è obbligatoria!")
        @Size(min = 8)
        String password) {
}
