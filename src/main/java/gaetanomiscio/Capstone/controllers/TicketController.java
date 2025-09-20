package gaetanomiscio.Capstone.controllers;

import gaetanomiscio.Capstone.entities.Ticket;
import gaetanomiscio.Capstone.entities.User;
import gaetanomiscio.Capstone.enums.Role;
import gaetanomiscio.Capstone.exceptions.UnauthorizedException;
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
    public List<Ticket> getAllTickets(@AuthenticationPrincipal User currentUser) {
        if (currentUser.getRole() == Role.ADMIN) {
            return ticketService.findAllTickets();
        } else {

            throw new UnauthorizedException("Non sei autorizzato a vedere tutti i ticket.");
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Ticket getTicketById(@PathVariable UUID id,
                                @AuthenticationPrincipal User currentUser) {
        Ticket ticket = ticketService.findById(id);
        if (currentUser.getRole() != Role.ADMIN && !ticket.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("Non sei autorizzato a vedere questo ticket.");
        }

        return ticket;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Ticket> getMyTickets(@AuthenticationPrincipal User currentUser) {
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
