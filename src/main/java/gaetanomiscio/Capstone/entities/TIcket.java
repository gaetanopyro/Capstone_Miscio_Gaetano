package gaetanomiscio.Capstone.entities;

import gaetanomiscio.Capstone.enums.Priority;
import gaetanomiscio.Capstone.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@ToString

public class TIcket {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private LocalDateTime date;

    public TIcket(String title, String description, Status status, Priority priority, LocalDateTime date) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.date = date;
    }
}
