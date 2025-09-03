package gaetanomiscio.Capstone.entities;

import gaetanomiscio.Capstone.enums.Priority;
import gaetanomiscio.Capstone.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor

public class TIcket {
    @Id
    private UUID id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private LocalDateTime date;


}
