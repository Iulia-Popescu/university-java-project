package Service.TicketService;
import Ticket.Ticket;

public interface TicketService {
    void sellTicket(Ticket ticket);
    void cancelTicket(Ticket ticket);
}
