package Location;

import Service.EventService.SimpleEventService;

import java.util.List;

public class Location {
    private String name;
    private static int id;
    private int numberOfEvents;

    SimpleEventService simpleEventService = new SimpleEventService(this);

    public Location(String name) {
        this.name = name;
        Location.id++;
    }

    public Location(String name, int numberOfEvents) {
        this.name = name;
        this.numberOfEvents = numberOfEvents;
        Location.id++;
    }

    public int getId() {
        return Location.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfEvents() {
        return numberOfEvents;
    }

    public void setNumberOfEvents(int numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
    }

    public SimpleEventService getSimpleEventService() {
        return simpleEventService;
    }
}
