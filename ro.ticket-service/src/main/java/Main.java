import Comparator.SortByLocation;
import Event.ArtEvent.ArtEvent;
import Event.MusicEvent.MusicEvent;
import Location.Location;
import Event.Event;
import Service.LocationService.SimpleLocationService;
import Ticket.NormalTicket.NormalTicket;
import Ticket.Ticket;
import Ticket.VIPTicket.VIPTicket;

import java.io.File;
import java.time.LocalTime;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;

public class Main {
    public static void history(String action, String time) throws IOException {
        FileWriter writer = new FileWriter("History.csv", true);

        String data = action + "," + time + "\n";
        writer.append(data);
        writer.flush();
        writer.close();
    }

    public static void viewHistory() throws IOException {
        Scanner scan = new Scanner(new File("History.csv"));

        while(scan.hasNextLine()) {
            System.out.println(scan.nextLine());
        }
    }

    public static void main(String[] args) throws IOException {
        FileWriter h = new FileWriter("History.csv");
        h.write("action_name,time_stamp\n");
        h.close();

        SimpleLocationService locationService = SimpleLocationService.getInstance();
        TreeMap<Location, List<Event>> map = new TreeMap<Location, List<Event>>(new SortByLocation());
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("This is a service for multiple event locations!" + "\n");
        System.out.println("You can select one of the following options:" + "\n");

        System.out.println("0. exit");
        System.out.println("1. Pick a new location to host events.");
        System.out.println("2. Set up an event in selected location.");
        System.out.println("3. List current locations.");
        System.out.println("4. List events in selected location.");
        System.out.println("5. Discard a location.");
        System.out.println("6. Cancel an event in selected location.");
        System.out.println("7. Buy a ticket.");
        System.out.println("8. Cancel a ticket.");
        System.out.println("9. List every event in every location.");
        System.out.println("10. Save locations to CSV.");
        System.out.println("11. Load locations from CSV.");
        System.out.println("12. Save art events in selected location to CSV.");
        System.out.println("13. Load art events in selected location from CSV.");
        System.out.println("14. Save music events in selected location to CSV.");
        System.out.println("15. Load music events in selected location from CSV.");
        System.out.println("16. Save tickets to CSV.");
        System.out.println("17. Load tickets from CSV.");
        System.out.println("18. View history.");

        while (true) {
            System.out.println("\nSelect an option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "0": {
                    System.exit(0);
                    break;
                }

                case "1": {
                    System.out.println("Enter name of the location: ");
                    String locationName = scanner.nextLine();
                    Location location = new Location(locationName);
                    map.put(location, new ArrayList<Event>());
                    locationService.pickLocation(location);

                    history("pick a location", LocalTime.now().toString());
                    break;
                }

                case "2": {
                    System.out.println("Enter name of the location:");
                    String locationName = scanner.nextLine();
                    Location location = locationService.getLocation(locationName);
                    if(location == null) {
                        System.out.println("Invalid location name. Try again.");
                        break;
                    }

                    System.out.println("Enter the type of the event: [art/music] ");
                    String eventType = scanner.nextLine();

                    System.out.println("Enter the name of the event: ");
                    String eventName = scanner.nextLine();

                    System.out.println("Enter the date of the event: ");
                    String eventDate = scanner.nextLine();

                    System.out.println("Enter number of normal tickets: ");
                    int normalTickets = Integer.parseInt(scanner.nextLine());

                    System.out.println("Enter number of VIP tickets: ");
                    int VIPTickets = Integer.parseInt(scanner.nextLine());

                    if (eventType.equals("art")) {
                        ArtEvent event = new ArtEvent(eventName, eventDate, normalTickets, VIPTickets);
                        locationService.getLocation(locationName).getSimpleEventService().setUpArtEvent(event);
                        map.get(location).add(event);
                    } else if (eventType.equals("music")) {
                        MusicEvent event = new MusicEvent(eventName, eventDate, normalTickets, VIPTickets);
                        locationService.getLocation(locationName).getSimpleEventService().setUpMusicEvent(event);
                        map.get(location).add(event);
                    } else {
                        System.out.println("Event type not valid");
                        break;
                    }

                    history("set up an event", LocalTime.now().toString());

                    break;
                }

                case "3": {
                    System.out.println("The locations that currently host events are: ");
                    for (Location l : locationService.getLocations()) {
                        System.out.println(l.getName());
                    }

                    history("list locations", LocalTime.now().toString());

                    break;
                }

                case "4": {
                    System.out.println("Enter name of the location: ");
                    String locationName = scanner.nextLine();

                    Location location = locationService.getLocation(locationName);

                    if(location == null) {
                        System.out.println("Invalid location name. Try again.");
                        break;
                    }

                    System.out.println("Events in selected location are: ");
                    System.out.println("\tArt events:");
                    for(Event e : location.getSimpleEventService().getArtEvents()) {
                        System.out.println("\t\t" + e.getName());
                    }
                    System.out.println("\tMusic events:");
                    for(Event e : location.getSimpleEventService().getMusicEvents()) {
                        System.out.println("\t\t" + e.getName());
                    }

                    history("list events in selected location", LocalTime.now().toString());

                    break;
                }

                case "5": {
                    System.out.println("Enter name of the location: ");
                    String locationName = scanner.nextLine();

                    Location location = locationService.getLocation(locationName);

                    if(location == null) {
                        System.out.println("Invalid location name. Try again.");
                        break;
                    }

                    map.remove(location);
                    locationService.discardLocation(location);

                    history("discard location", LocalTime.now().toString());

                    break;
                }

                case "6": {
                    System.out.println("Enter name of the location: ");
                    String locationName = scanner.nextLine();

                    Location location = locationService.getLocation(locationName);

                    if(location == null) {
                        System.out.println("Invalid location name. Try again.");
                        break;
                    }

                    System.out.println("Enter name of the event: ");
                    String eventName = scanner.nextLine();

                    System.out.println("Enter type of the event: [art/music] ");
                    String eventType = scanner.nextLine();

                    Event event = null;
                    if(eventType.equals("art")) {
                        event = locationService.getLocation(locationName).getSimpleEventService().getArtEvent(eventName);
                        map.get(location).remove(event);
                        location.getSimpleEventService().cancelArtEvent(event);
                    } else if(eventType.equals(("music"))){
                        event = locationService.getLocation(locationName).getSimpleEventService().getMusicEvent(eventName);
                        map.get(location).remove(event);
                        location.getSimpleEventService().cancelMusicEvent(event);
                    }

                    if(event == null) {
                        System.out.println("Invalid type of event.");
                        break;
                    }

                    history("cancel event", LocalTime.now().toString());

                    break;
                }

                case "7": {
                    System.out.println("Enter name of the location: ");
                    String locationName = scanner.nextLine();

                    Location location = locationService.getLocation(locationName);

                    if(location == null) {
                        System.out.println("Invalid location name. Try again.");
                        break;
                    }

                    System.out.println("Enter name of the event: ");
                    String eventName = scanner.nextLine();

                    System.out.println("Enter type of the event: [art/music] ");
                    String eventType = scanner.nextLine();

                    System.out.println("Enter the type of the ticket: [normal/VIP] ");
                    String ticketType = scanner.nextLine();

                    Event event = null;
                    if(eventType.equals("art")) {
                        event = location.getSimpleEventService().getArtEvent(eventName);
                    } else if(eventType.equals("music")){
                        event = location.getSimpleEventService().getMusicEvent(eventName);
                    }

                    if(event == null) {
                        System.out.println("Invalid type of event. Try again.");
                        break;
                    }

                    if (ticketType.equals("normal")) {
                        NormalTicket ticket = new NormalTicket(eventName);
                        event.getSimpleTicketService().sellTicket(ticket);
                        System.out.println("The id of your ticket is " + ticket.getId());
                    } else if (ticketType.equals("VIP")) {
                        VIPTicket ticket = new VIPTicket(eventName);
                        event.getSimpleTicketService().sellTicket(ticket);
                        System.out.println("The id of your ticket is " + ticket.getId());
                    } else {
                        System.out.println("Invalid type of ticket. Try again.");
                        break;
                    }

                    history("buy ticket", LocalTime.now().toString());

                    break;
                }

                case "8": {
                    System.out.println("Enter name of the location: ");
                    String locationName = scanner.nextLine();

                    Location location = locationService.getLocation(locationName);

                    if(location == null) {
                        System.out.println("Invalid location name. Try again.");
                        break;
                    }

                    System.out.println("Enter name of the event: ");
                    String eventName = scanner.nextLine();

                    System.out.println("Enter type of the event: [art/music] ");
                    String eventType = scanner.nextLine();

                    Event event = null;
                    if(eventType.equals("art")) {
                        event = location.getSimpleEventService().getArtEvent(eventName);
                    } else if(eventType.equals(("music"))){
                        event = location.getSimpleEventService().getMusicEvent(eventName);
                    }

                    if(event == null) {
                        System.out.println("Invalid type of event. Try again.");
                        break;
                    }

                    System.out.println("Enter the id of the ticket: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Ticket ticket = null;

                    try {
                        ticket = event.getSimpleTicketService().getTickets().get(id);
                    }
                    catch (Exception e) {
                        System.out.println("Invalid ticket ID. Try again.");
                    }

                    if (ticket == null) throw new AssertionError();
                    event.getSimpleTicketService().cancelTicket(ticket);

                    history("cancel ticket", LocalTime.now().toString());

                    break;
                }

                case "9": {
                    for(Map.Entry<Location, List<Event>> entry : map.entrySet()) {
                        Location key = entry.getKey();
                        List<Event> value = entry.getValue();

                        System.out.println("Events in " + key.getName() + ":");
                        for(Event event : value) {
                            System.out.println("\t• " + event.getName());
                        }
                    }

                    history("list all events", LocalTime.now().toString());
                }

                case "10": {
                    locationService.writeToCSV();
                    System.out.println("The locations have been saved.\n");

                    history("save locations to CSV", LocalTime.now().toString());

                    break;
                }

                case "11": {
                    locationService.readFromCSV();
                    System.out.println("The locations currently in the CSV are: ");
                    for (Location location : locationService.getLocations()) {
                        System.out.println(location.getName());
                    }

                    history("load locations from CSV", LocalTime.now().toString());

                    break;
                }

                case "12": {
                    System.out.println("Enter name of the location to save art events from: ");
                    String locationName = scanner.nextLine();

                    Location location = locationService.getLocation(locationName);

                    if(location == null) {
                        System.out.println("Invalid location name. Try again.");
                        break;
                    }

                    location.getSimpleEventService().writeArtEventsToCSV();
                    System.out.println("The art events from " + locationName + " have been saved.\n");

                    history("save art events to CSV", LocalTime.now().toString());

                    break;
                }

                case "13": {
                    System.out.println("Enter name of the location to load art events from: ");
                    String locationName = scanner.nextLine();

                    Location location = locationService.getLocation(locationName);

                    if(location == null) {
                        System.out.println("Invalid location name. Try again.");
                        break;
                    }

                    location.getSimpleEventService().readArtEventsFromCSV();
                    System.out.println("The art events in " + locationName + " currently in the CSV are: ");
                    for (Event event : location.getSimpleEventService().getArtEvents()) {
                        System.out.println(event.getName());
                    }

                    history("load art events from CSV", LocalTime.now().toString());

                    break;
                }

                case "14": {
                    System.out.println("Enter name of the location to save music events from: ");
                    String locationName = scanner.nextLine();

                    Location location = locationService.getLocation(locationName);

                    if(location == null) {
                        System.out.println("Invalid location name. Try again.");
                        break;
                    }

                    location.getSimpleEventService().writeMusicEventsToCSV();
                    System.out.println("The music events from " + locationName + " have been saved.\n");

                    history("save music events to CSV", LocalTime.now().toString());

                    break;
                }

                case "15": {
                    System.out.println("Enter name of the location to load music events from: ");
                    String locationName = scanner.nextLine();

                    Location location = locationService.getLocation(locationName);

                    if(location == null) {
                        System.out.println("Invalid location name. Try again.");
                        break;
                    }

                    location.getSimpleEventService().readMusicEventsFromCSV();
                    System.out.println("The music events in " + locationName + " currently in the CSV are: ");
                    for (Event event : location.getSimpleEventService().getMusicEvents()) {
                        System.out.println(event.getName());
                    }

                    history("load music events from CSV", LocalTime.now().toString());

                    break;
                }

                case "16": {
                    System.out.println("Enter name of the location of the event: ");
                    String locationName = scanner.nextLine();

                    Location location = locationService.getLocation(locationName);

                    if(location == null) {
                        System.out.println("Invalid location name. Try again.");
                        break;
                    }

                    System.out.println("Enter name of the event: ");
                    String eventName = scanner.nextLine();

                    System.out.println("Enter type of the event: [art/music] ");
                    String eventType = scanner.nextLine();

                    Event event = null;
                    if(eventType.equals("art")) {
                        event = location.getSimpleEventService().getArtEvent(eventName);
                    } else if(eventType.equals(("music"))){
                        event = location.getSimpleEventService().getMusicEvent(eventName);
                    }

                    if(event == null) {
                        System.out.println("Invalid type of event. Try again.");
                        break;
                    }

                    event.getSimpleTicketService().writeToCSV();
                    System.out.println("Tickets sold at " + eventName + " in " + locationName + " have been saved to the CSV.");

                    history("save tickets to CSV", LocalTime.now().toString());

                    break;
                }

                case "17": {
                    System.out.println("Enter name of the location of the event: ");
                    String locationName = scanner.nextLine();

                    Location location = locationService.getLocation(locationName);

                    if(location == null) {
                        System.out.println("Invalid location name. Try again.");
                        break;
                    }

                    System.out.println("Enter name of the event: ");
                    String eventName = scanner.nextLine();

                    System.out.println("Enter type of the event: [art/music] ");
                    String eventType = scanner.nextLine();

                    Event event = null;
                    if(eventType.equals("art")) {
                        event = location.getSimpleEventService().getArtEvent(eventName);
                    } else if(eventType.equals(("music"))){
                        event = location.getSimpleEventService().getMusicEvent(eventName);
                    }

                    if(event == null) {
                        System.out.println("Invalid type of event. Try again.");
                        break;
                    }

                    event.getSimpleTicketService().readFromCSV();
                    System.out.println("There are currently " +
                            event.getNumberOfNormalTickets() +
                            " normal tickets and " +
                            event.getNumberOfVIPTickets() +
                            " VIP tickets in the CSV.");

                    history("load tickets from CSV", LocalTime.now().toString());

                    break;
                }

                case "18": {
                    viewHistory();
                    break;
                }

                default: {
                    System.out.println("\n");
                    System.out.println("Invalid option. Please try again.\n");
                }
            }

        }
    }
}