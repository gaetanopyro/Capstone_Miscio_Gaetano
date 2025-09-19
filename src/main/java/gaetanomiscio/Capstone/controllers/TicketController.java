package gaetanomiscio.Capstone.controllers;

import gaetanomiscio.Capstone.entities.Ticket;
import gaetanomiscio.Capstone.entities.User;
import gaetanomiscio.Capstone.payload.CreateTicketDTO;
import gaetanomiscio.Capstone.payload.UpdateTicketDTO;
import gaetanomiscio.Capstone.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;


    //controllare la post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Ticket createTicket(@RequestBody CreateTicketDTO payload,
                               //@RequestParam UUID userId,
                               @AuthenticationPrincipal User currentUser) {
        return ticketService.create(payload, currentUser.getId());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Ticket> getAllTickets() {
        return ticketService.findAllTickets();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Ticket getTicketById(@PathVariable UUID id) {
        return ticketService.findById(id);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Ticket> getAllTickets(@AuthenticationPrincipal User currentUser) {
        return ticketService.findByUserId(currentUser.getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @ticketService.isTicketOwner(#id, #currentUser)")
    public Ticket updateTicket(@PathVariable UUID id,
                               @RequestBody UpdateTicketDTO payload,
                               @AuthenticationPrincipal User currentUser) {
        return ticketService.findByIdAndUpdate(id, payload, currentUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @ticketService.isTicketOwner(#id, #currentUser)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable UUID id, @AuthenticationPrincipal User currentUser) {
        ticketService.deleteTicket(id, currentUser);
    }
}
