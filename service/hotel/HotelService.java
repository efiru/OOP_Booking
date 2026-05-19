package service.hotel;

import model.hotel.Hotel;
import model.room.Room;
import model.room.StandardRoom;
import model.room.SuiteRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class HotelService implements IHotelService {
    private final TreeSet<Hotel> hotels = new TreeSet<>();
    private final Map<Integer, Hotel> hotelById = new HashMap<>();
    private int nextHotelId = 1;
    private int nextRoomId = 1;

    @Override
    public Hotel addHotel(String name, String city, int stars) {
        Hotel hotel = new Hotel(nextHotelId++, name, city, stars);
        hotels.add(hotel);
        hotelById.put(hotel.getId(), hotel);
        return hotel;
    }

    @Override
    public Room addStandardRoom(int hotelId, String number, int capacity, double pricePerNight, boolean balcony) {
        Hotel hotel = getHotel(hotelId);
        Room room = new StandardRoom(nextRoomId++, number, capacity, pricePerNight, true, balcony);
        hotel.addRoom(room);
        return room;
    }

    @Override
    public Room addSuiteRoom(int hotelId, String number, int capacity, double pricePerNight, boolean livingArea) {
        Hotel hotel = getHotel(hotelId);
        Room room = new SuiteRoom(nextRoomId++, number, capacity, pricePerNight, true, livingArea);
        hotel.addRoom(room);
        return room;
    }

    @Override
    public List<Hotel> getAllHotels() {
        return new ArrayList<>(hotels);
    }

    @Override
    public List<Hotel> findHotelsByCity(String city) {
        List<Hotel> result = new ArrayList<>();
        for (Hotel hotel : hotels) {
            if (hotel.getCity().equalsIgnoreCase(city)) {
                result.add(hotel);
            }
        }
        return result;
    }

    @Override
    public Hotel getHotel(int hotelId) {
        Hotel hotel = hotelById.get(hotelId);
        if (hotel == null) {
            throw new IllegalArgumentException("Nu exista hotel cu id-ul " + hotelId + ".");
        }
        return hotel;
    }

    @Override
    public List<Room> getRoomsForHotel(int hotelId) {
        return new ArrayList<>(getHotel(hotelId).getRooms());
    }

    @Override
    public List<Room> getAvailableRoomsUnderPrice(double maxPrice) {
        List<Room> result = new ArrayList<>();
        for (Hotel hotel : hotels) {
            for (Room room : hotel.getRooms()) {
                if (room.isAvailable() && room.getPricePerNight() <= maxPrice) {
                    result.add(room);
                }
            }
        }
        return result;
    }

    @Override
    public List<String> getAvailableRoomDescriptionsUnderPrice(double maxPrice) {
        List<String> result = new ArrayList<>();
        for (Hotel hotel : hotels) {
            for (Room room : hotel.getRooms()) {
                if (room.isAvailable() && room.getPricePerNight() <= maxPrice) {
                    result.add("Hotel " + hotel.getName() + " (" + hotel.getCity() + ") - " + room);
                }
            }
        }
        return result;
    }

    @Override
    public Room getRoomInHotel(int hotelId, int roomId) {
        Hotel hotel = getHotel(hotelId);
        for (Room room : hotel.getRooms()) {
            if (room.getId() == roomId) {
                return room;
            }
        }
        throw new IllegalArgumentException("Nu exista camera cu id-ul " + roomId + " in hotelul selectat.");
    }
}
