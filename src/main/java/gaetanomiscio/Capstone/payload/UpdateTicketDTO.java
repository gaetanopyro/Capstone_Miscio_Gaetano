package gaetanomiscio.Capstone.payload;

import gaetanomiscio.Capstone.enums.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdateTicketDTO(
        @NotEmpty(message = "la descrizione è obbligatoria!")
        @Size(min = 2, max = 5000, message = "La descrizione deve essere di lunghezza compresa tra 2 e 5000")
        String description,
        @NotEmpty(message = "Lo status è obbligatorio")
        Status status
) {
}
