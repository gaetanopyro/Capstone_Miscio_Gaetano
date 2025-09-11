package gaetanomiscio.Capstone.services;

import gaetanomiscio.Capstone.entities.Ticket;
import gaetanomiscio.Capstone.entities.User;
import gaetanomiscio.Capstone.enums.Status;
import gaetanomiscio.Capstone.exceptions.NotFoundException;
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

    public Ticket findByIdAndUpdate(UUID id, UpdateTicketDTO payload) {
        Ticket found = this.ticketRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket con id: " + id + "non trovato."));
        if (payload.description() != null) {
            found.setDescription(payload.description());
        }
        if (payload.status() != null) {
            found.setStatus(payload.status());
        }
        found.setDate(LocalDateTime.now());
        Ticket modifiedTicket = this.ticketRepository.save(found);
        return modifiedTicket;
    }

    public void deleteTicket(UUID id) {
        Ticket found = this.ticketRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        this.ticketRepository.delete(found);
    }
}
