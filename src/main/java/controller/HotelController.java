package controller;

import model.hotel.Hotel;
import model.room.Room;
import org.springframework.web.bind.annotation.*;
import service.hotel.IHotelService;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final IHotelService hotelService;

    public HotelController(IHotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<Hotel> getHotels(@RequestParam(required = false) String city) {
        if (city != null && !city.isBlank()) {
            return hotelService.findHotelsByCity(city);
        }
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public Hotel getHotel(@PathVariable int id) {
        return hotelService.getHotel(id);
    }

    @PostMapping
    public Hotel addHotel(@RequestBody AddHotelRequest req) {
        return hotelService.addHotel(req.name(), req.city(), req.stars());
    }

    @GetMapping("/{id}/rooms")
    public List<Room> getRooms(@PathVariable int id) {
        return hotelService.getRoomsForHotel(id);
    }

    @GetMapping("/available-rooms")
    public List<String> getAvailableRooms(@RequestParam double maxPrice) {
        return hotelService.getAvailableRoomDescriptionsUnderPrice(maxPrice);
    }

    @PostMapping("/{id}/rooms/standard")
    public Room addStandardRoom(@PathVariable int id, @RequestBody AddStandardRoomRequest req) {
        return hotelService.addStandardRoom(id, req.number(), req.capacity(), req.pricePerNight(), req.balcony());
    }

    @PostMapping("/{id}/rooms/suite")
    public Room addSuiteRoom(@PathVariable int id, @RequestBody AddSuiteRoomRequest req) {
        return hotelService.addSuiteRoom(id, req.number(), req.capacity(), req.pricePerNight(), req.livingArea());
    }

    record AddHotelRequest(String name, String city, int stars) {}
    record AddStandardRoomRequest(String number, int capacity, double pricePerNight, boolean balcony) {}
    record AddSuiteRoomRequest(String number, int capacity, double pricePerNight, boolean livingArea) {}
}
