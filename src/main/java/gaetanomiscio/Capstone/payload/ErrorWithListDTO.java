package gaetanomiscio.Capstone.payload;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorWithListDTO(String message, LocalDateTime timestamp, List<String> errorsList) {
}
