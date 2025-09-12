package gaetanomiscio.Capstone.services;

import gaetanomiscio.Capstone.entities.Ticket;
import gaetanomiscio.Capstone.entities.User;
import gaetanomiscio.Capstone.enums.Status;
import gaetanomiscio.Capstone.exceptions.NotFoundException;
import gaetanomiscio.Capstone.exceptions.UnauthorizedException;
import gaetanomiscio.Capstone.payload.CreateTicketDTO;
import gaetanomiscio.Capstone.payload.UpdateTicketDTO;
import gaetanomiscio.Capstone.repositories.TicketRepository;
import gaetanomiscio.Capstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;


    public Ticket create(CreateTicketDTO nT, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
        Ticket ticket = new Ticket(
                nT.title(),
                nT.description(),
                nT.status() == null ? Status.OPEN : nT.status(),
                nT.priority(),
                LocalDateTime.now(),
                user
        );
        return ticketRepository.save(ticket);
    }

    public Ticket findById(UUID id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket con id: " + id + "non trovato."));
    }

    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket findByIdAndUpdate(UUID id, UpdateTicketDTO payload, User currentUser) {
        Ticket found = this.ticketRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket con id: " + id + "non trovato."));
        if (currentUser.getRole().name().equals("ADMIN")) {
        } else if (currentUser.getRole().name().equals("USER")) {
            if (!found.getUser().getId().equals(currentUser.getId())) {
                throw new UnauthorizedException("Non hai i permessi per modificare questo ticket!");
            }
        } else {
            throw new UnauthorizedException("Ruolo non autorizzato a modificare ticket!");
        }
        if (payload.description() != null) {
            found.setDescription(payload.description());
        }
        if (payload.status() != null) {
            found.setStatus(payload.status());
        }
        found.setDate(LocalDateTime.now());
        return this.ticketRepository.save(found);
    }

    public void deleteTicket(UUID id, User currentUser) {
        Ticket found = this.ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket con id: " + id + " non trovato."));
        if (currentUser.getRole().name().equals("ADMIN")) {
            this.ticketRepository.delete(found);
            return;
        } else if (currentUser.getRole().name().equals("USER")) {
            if (!found.getUser().getId().equals(currentUser.getId())) {
                throw new UnauthorizedException("Non hai i permessi per eliminare questo ticket!");
            }
            this.ticketRepository.delete(found);
        } else {
            throw new UnauthorizedException("Ruolo non autorizzato a eliminare ticket!");
        }
    }
}
