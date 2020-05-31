package Ticket;

public abstract class Ticket {
    protected String name;
    protected static int id;

    protected Ticket(String name) {
        this.name = name;
        Ticket.id++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Ticket.id;
    }
}
