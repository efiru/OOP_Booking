package service.hotel;

import model.hotel.Hotel;
import model.room.Room;

import java.util.List;

public interface IHotelService {
    Hotel addHotel(String name, String city, int stars);
    Room addStandardRoom(int hotelId, String number, int capacity, double pricePerNight, boolean balcony);
    Room addSuiteRoom(int hotelId, String number, int capacity, double pricePerNight, boolean livingArea);
    List<Hotel> getAllHotels();
    List<Hotel> findHotelsByCity(String city);
    Hotel getHotel(int hotelId);
    List<Room> getRoomsForHotel(int hotelId);
    List<Room> getAvailableRoomsUnderPrice(double maxPrice);
    List<String> getAvailableRoomDescriptionsUnderPrice(double maxPrice);
    Room getRoomInHotel(int hotelId, int roomId);
}
