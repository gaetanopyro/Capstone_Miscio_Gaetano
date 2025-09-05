package gaetanomiscio.Capstone.repositories;

import gaetanomiscio.Capstone.entities.Ticket;
import gaetanomiscio.Capstone.enums.Priority;
import gaetanomiscio.Capstone.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByStatus(Status status);

    List<Ticket> findByPriority(Priority priority);

    List<Ticket> findByUserId(UUID userId);
}
