package service.hotel;

import model.hotel.Hotel;
import model.room.Room;
import model.room.StandardRoom;
import model.room.SuiteRoom;
import repository.hotel.HotelDao;
import repository.room.RoomDao;
import service.audit.AuditService;

import java.util.ArrayList;
import java.util.List;

public class HotelService implements IHotelService {
    private final HotelDao hotelDao;
    private final RoomDao roomDao;
    private final AuditService auditService;

    public HotelService(HotelDao hotelDao, RoomDao roomDao, AuditService auditService) {
        this.hotelDao = hotelDao;
        this.roomDao = roomDao;
        this.auditService = auditService;
    }

    @Override
    public Hotel addHotel(String name, String city, int stars) {
        auditService.log("addHotel");
        return hotelDao.insert(new Hotel(0, name, city, stars));
    }

    @Override
    public Room addStandardRoom(int hotelId, String number, int capacity, double pricePerNight, boolean balcony) {
        auditService.log("addStandardRoom");
        getHotel(hotelId);
        return roomDao.insert(hotelId, new StandardRoom(0, number, capacity, pricePerNight, true, balcony));
    }

    @Override
    public Room addSuiteRoom(int hotelId, String number, int capacity, double pricePerNight, boolean livingArea) {
        auditService.log("addSuiteRoom");
        getHotel(hotelId);
        return roomDao.insert(hotelId, new SuiteRoom(0, number, capacity, pricePerNight, true, livingArea));
    }

    @Override
    public List<Hotel> getAllHotels() {
        auditService.log("getAllHotels");
        List<Hotel> hotels = hotelDao.findAll();
        for (Hotel hotel : hotels) {
            loadRooms(hotel);
        }
        return hotels;
    }

    @Override
    public List<Hotel> findHotelsByCity(String city) {
        auditService.log("findHotelsByCity");
        List<Hotel> result = new ArrayList<>();
        for (Hotel hotel : hotelDao.findAll()) {
            if (hotel.getCity().equalsIgnoreCase(city)) {
                loadRooms(hotel);
                result.add(hotel);
            }
        }
        return result;
    }

    @Override
    public Hotel getHotel(int hotelId) {
        auditService.log("getHotel");
        Hotel hotel = hotelDao.findById(hotelId);
        if (hotel == null) {
            throw new IllegalArgumentException("Nu exista hotel cu id-ul " + hotelId + ".");
        }
        loadRooms(hotel);
        return hotel;
    }

    @Override
    public List<Room> getRoomsForHotel(int hotelId) {
        auditService.log("getRoomsForHotel");
        getHotel(hotelId);
        return roomDao.findAllByHotelId(hotelId);
    }

    @Override
    public List<Room> getAvailableRoomsUnderPrice(double maxPrice) {
        auditService.log("getAvailableRoomsUnderPrice");
        List<Room> result = new ArrayList<>();
        for (Hotel hotel : hotelDao.findAll()) {
            for (Room room : roomDao.findAllByHotelId(hotel.getId())) {
                if (room.isAvailable() && room.getPricePerNight() <= maxPrice) {
                    result.add(room);
                }
            }
        }
        return result;
    }

    @Override
    public List<String> getAvailableRoomDescriptionsUnderPrice(double maxPrice) {
        auditService.log("getAvailableRoomDescriptionsUnderPrice");
        List<String> result = new ArrayList<>();
        for (Hotel hotel : hotelDao.findAll()) {
            for (Room room : roomDao.findAllByHotelId(hotel.getId())) {
                if (room.isAvailable() && room.getPricePerNight() <= maxPrice) {
                    result.add("Hotel " + hotel.getName() + " (" + hotel.getCity() + ") - " + room);
                }
            }
        }
        return result;
    }

    @Override
    public Room getRoomInHotel(int hotelId, int roomId) {
        auditService.log("getRoomInHotel");
        for (Room room : roomDao.findAllByHotelId(hotelId)) {
            if (room.getId() == roomId) {
                return room;
            }
        }
        throw new IllegalArgumentException("Nu exista camera cu id-ul " + roomId + " in hotelul selectat.");
    }

    private void loadRooms(Hotel hotel) {
        for (Room room : roomDao.findAllByHotelId(hotel.getId())) {
            hotel.addRoom(room);
        }
    }
}
