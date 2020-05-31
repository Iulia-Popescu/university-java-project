package Service.EventService;
import Event.Event;
import Event.ArtEvent.ArtEvent;
import Event.MusicEvent.MusicEvent;
import Location.Location;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleEventService implements EventService {
    private List<Event> artEvents;
    private List<Event> musicEvents;
    private Location location;

    public SimpleEventService(Location location) {
        this.artEvents = new ArrayList<>();
        this.musicEvents = new ArrayList<>();
        this.location = location;
    }

    public List<Event> getArtEvents() {
        return artEvents;
    }

    public List<Event> getMusicEvents() { return musicEvents; }

    public Event getArtEvent(String name) {
        for (Event event : artEvents) {
            if(event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    public Event getMusicEvent(String name) {
        for (Event event : musicEvents) {
            if(event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    @Override
    public void setUpArtEvent(Event event) {
        this.artEvents.add(event);
        this.location.setNumberOfEvents(location.getNumberOfEvents() + 1);
    }

    @Override
    public void setUpMusicEvent(Event event) {
        this.musicEvents.add(event);
        this.location.setNumberOfEvents(location.getNumberOfEvents() + 1);
    }

    @Override
    public void cancelArtEvent(Event event) {
        int id = event.getId();
        this.artEvents.removeIf(event1 -> event1.getId() == id);
        this.location.setNumberOfEvents(location.getNumberOfEvents() - 1);
    }

    @Override
    public void cancelMusicEvent(Event event) {
        int id = event.getId();
        this.musicEvents.removeIf(event1 -> event1.getId() == id);
        this.location.setNumberOfEvents(location.getNumberOfEvents() - 1);
    }

    public void writeArtEventsToCSV() throws IOException {
        StringBuilder data = new StringBuilder("Name" + "," + "Date" + "," + "Number of normal tickets" + "," + "Number of VIP tickets" + "\n");

        for(Event event : artEvents) {
            if(event.getClass() == ArtEvent.class) {
                data.append(event.getName()).
                        append(",").
                        append(event.getDate()).
                        append(",").
                        append(event.getNumberOfNormalTickets()).
                        append(",").
                        append(event.getNumberOfVIPTickets()).
                        append("\n");
            }
        }

        FileWriter writer = new FileWriter("Art Events.csv");
        writer.write(data.toString());
        writer.close();
    }

    public void readArtEventsFromCSV() throws FileNotFoundException {
        Scanner scan = new Scanner(new File("Art Events.csv"));

        String str = scan.nextLine();
        String []names = str.split(",");

        while(scan.hasNextLine()) {
            String []data = scan.nextLine().split(",");
            Event artEvent = new ArtEvent(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
            boolean find = false;
            for(Event a : artEvents) {
                if (artEvent.getName().equals(a.getName())) {
                    find = true;
                    break;
                }
            }

            if(!find) {
                artEvents.add(artEvent);
            }
        }
    }

    public void writeMusicEventsToCSV() throws IOException {
        StringBuilder data = new StringBuilder("Name" + "," + "Date" + "," + "Number of normal tickets" + "," + "Number of VIP tickets" + "\n");

        for(Event event : musicEvents) {
            if(event.getClass() == MusicEvent.class) {
                data.append(event.getName()).
                        append(",").
                        append(event.getDate()).
                        append(",").
                        append(event.getNumberOfNormalTickets()).
                        append(",").
                        append(event.getNumberOfVIPTickets()).
                        append("\n");
            }
        }

        FileWriter writer = new FileWriter("Music Events.csv");
        writer.write(data.toString());
        writer.close();
    }

    public void readMusicEventsFromCSV() throws FileNotFoundException {
        Scanner scan = new Scanner(new File("Music Events.csv"));

        String str = scan.nextLine();
        String []names = str.split(",");

        while(scan.hasNextLine()) {
            String []data = scan.nextLine().split(",");
            Event musicEvent = new MusicEvent(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
            boolean find = false;
            for(Event m : musicEvents) {
                if (musicEvent.getName().equals(m.getName())) {
                    find = true;
                    break;
                }
            }

            if(!find) {
                musicEvents.add(musicEvent);
            }
        }
    }
}
