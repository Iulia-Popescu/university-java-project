package Service.TicketService;

import Event.Event;
import Location.Location;
import Ticket.NormalTicket.NormalTicket;
import Ticket.Ticket;
import Ticket.VIPTicket.VIPTicket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleTicketService implements TicketService {
    private List<Ticket> tickets;
    private Event event;

    public SimpleTicketService(Event event) {
        this.tickets = new ArrayList<Ticket>();
        this.event = event;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public void sellTicket(Ticket ticket) {
        if(ticket.getClass() == NormalTicket.class) {
            event.setNumberOfNormalTickets(event.getNumberOfNormalTickets() - 1);
        } else if (ticket.getClass() == VIPTicket.class) {
            event.setNumberOfVIPTickets(event.getNumberOfVIPTickets() - 1);
        }
        tickets.add(ticket);
    }

    @Override
    public void cancelTicket(Ticket ticket) {
        int id = ticket.getId();
        tickets.removeIf(ticket1 -> ticket1.getId() == id);
        if(ticket.getClass() == NormalTicket.class) {
            event.setNumberOfNormalTickets(event.getNumberOfNormalTickets() + 1);
        } else if (ticket.getClass() == VIPTicket.class) {
            event.setNumberOfVIPTickets(event.getNumberOfVIPTickets() + 1);
        }
    }

    public void writeToCSV() throws IOException {
        StringBuilder data = new StringBuilder("ID" + "," + "Ticket type" + "," + "Event" + "\n");

        for(Ticket ticket : tickets) {
            data.append(ticket.getId()).
                    append(",").
                    append(ticket.getClass().getName()).
                    append(",").
                    append(ticket.getName()).
                    append(",").
                    append("\n");
        }

        FileWriter writer = new FileWriter("Tickets.csv");
        writer.write(String.valueOf(data));
        writer.close();
    }

    public void readFromCSV() throws FileNotFoundException {
        Scanner scan = new Scanner(new File("Tickets.csv"));

        String str = scan.nextLine();
        String []names = str.split(",");

        while(scan.hasNextLine()) {
            String []data = scan.nextLine().split(",");

            Ticket ticket;
            if(data[1].equals("NormalTicket")) {
                ticket = new NormalTicket(data[2]);
            } else {
                ticket = new VIPTicket(data[2]);
            }

            boolean find = false;
            for(Ticket t : tickets) {
                if (Integer.parseInt(data[0]) == t.getId()) {
                    find = true;
                    break;
                }
            }

            if(!find) {
                tickets.add(ticket);
            }

            if(data[1].equals("NormalTicket")) {
                event.setNumberOfNormalTickets(event.getNumberOfNormalTickets() - 1);
            } else {
                event.setNumberOfVIPTickets(event.getNumberOfVIPTickets() - 1);
            }
        }
    }

}
