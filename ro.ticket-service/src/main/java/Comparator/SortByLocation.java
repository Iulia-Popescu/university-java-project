package Comparator;

import Location.Location;

import java.util.Comparator;

public class SortByLocation implements Comparator<Location> {
    public int compare(Location a, Location b) {
        return a.getName().compareTo(b.getName());
    }
}
