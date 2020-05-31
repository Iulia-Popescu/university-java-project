package Event;

import Service.TicketService.SimpleTicketService;

public abstract class Event {
    protected String name;
    protected String date;
    protected static int id;
    private int numberOfNormalTickets;
    private int numberOfVIPTickets;

    private SimpleTicketService simpleTicketService;

    public SimpleTicketService getSimpleTicketService() {
        return simpleTicketService;
    }

    protected Event(String name, String date, int normalTickets, int VIPTickets) {
        this.name = name;
        this.date = date;
        Event.id++;
        this.numberOfNormalTickets = normalTickets;
        this.numberOfVIPTickets = VIPTickets;

        simpleTicketService = new SimpleTicketService(this);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Event.id;
    }

    public int getNumberOfNormalTickets() {
        return numberOfNormalTickets;
    }

    public void setNumberOfNormalTickets(int numberOfNormalTickets) {
        this.numberOfNormalTickets = numberOfNormalTickets;
    }

    public int getNumberOfVIPTickets() {
        return numberOfVIPTickets;
    }

    public void setNumberOfVIPTickets(int numberOfVIPTickets) {
        this.numberOfVIPTickets = numberOfVIPTickets;
    }
}
