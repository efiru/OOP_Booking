package model.hotel;

import model.room.Room;

import java.util.ArrayList;
import java.util.List;

public class Hotel implements Comparable<Hotel> {
    private int id;
    private String name;
    private String city;
    private int stars;
    private List<Room> rooms;

    public Hotel(int id, String name, String city, int stars) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.stars = stars;
        this.rooms = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    @Override
    public int compareTo(Hotel other) {
        int cityCompare = city.compareToIgnoreCase(other.city);
        if (cityCompare != 0) {
            return cityCompare;
        }

        int nameCompare = name.compareToIgnoreCase(other.name);
        if (nameCompare != 0) {
            return nameCompare;
        }

        return Integer.compare(id, other.id);
    }

    @Override
    public String toString() {
        return "Hotel{id=" + id + ", name='" + name + "', city='" + city
                + "', stars=" + stars + ", rooms=" + rooms.size() + "}";
    }
}
