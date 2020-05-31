package Service.LocationService;
import Location.Location;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleLocationService implements LocationService{
    List<Location> locations;

    public List<Location> getLocations() {
        return locations;
    }

    public Location getLocation(String name) {
        for (Location location : locations) {
            if(location.getName().equals(name)) {
                return location;
            }
        }
        return null;
    }

    private static SimpleLocationService instance = null;

    private SimpleLocationService() {
        this.locations = new ArrayList<>();
    }

    public static SimpleLocationService getInstance() {
        if(instance == null) {
            instance = new SimpleLocationService();
        }
        return instance;
    }

    @Override
    public void pickLocation(Location location) {
        locations.add(location);
    }

    @Override
    public void discardLocation(Location location) {
        int id = location.getId();
        locations.removeIf(location1 -> location1.getId() == id);
    }

    public void writeToCSV() throws IOException {
        StringBuilder data = new StringBuilder("Name" + "," + "Number of events" + "\n");

        for(Location location : locations) {
            data.append(location.getName()).
                append(",").
                append(location.getNumberOfEvents()).
                append("\n");
        }

        FileWriter writer = new FileWriter("Locations.csv");
        writer.write(String.valueOf(data));
        writer.close();
    }

    public void readFromCSV() throws FileNotFoundException {
        Scanner scan = new Scanner(new File("Locations.csv"));

        String str = scan.nextLine();
        String []names = str.split(",");

        while(scan.hasNextLine()) {
            String []data = scan.nextLine().split(",");
            Location location = new Location(data[0], Integer.parseInt(data[1]));
            boolean find = false;
            for(Location l : locations) {
                if (location.getName().equals(l.getName())) {
                    find = true;
                    break;
                }
            }

            if(!find) {
                locations.add(location);
            }
        }
    }
}
