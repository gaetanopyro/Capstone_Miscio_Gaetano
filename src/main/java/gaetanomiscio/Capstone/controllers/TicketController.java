package gaetanomiscio.Capstone.controllers;

import gaetanomiscio.Capstone.entities.Ticket;
import gaetanomiscio.Capstone.payload.CreateTicketDTO;
import gaetanomiscio.Capstone.payload.UpdateTicketDTO;
import gaetanomiscio.Capstone.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket createTicket(@RequestBody CreateTicketDTO payload,
                               @RequestParam UUID userId) {
        return ticketService.create(payload, userId);
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable UUID id) {
        return ticketService.findById(id);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.findAllTickets();
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable UUID id,
                               @RequestBody UpdateTicketDTO payload) {
        return ticketService.findByIdAndUpdate(id, payload);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable UUID id) {
        ticketService.deleteTicket(id);
    }
}
