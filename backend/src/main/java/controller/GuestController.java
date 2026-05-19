package controller;

import model.person.Guest;
import org.springframework.web.bind.annotation.*;
import service.guest.IGuestService;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final IGuestService guestService;

    public GuestController(IGuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public List<Guest> getAllGuests() {
        return guestService.getAllGuests();
    }

    @PostMapping
    public Guest registerGuest(@RequestBody RegisterGuestRequest req) {
        return guestService.registerGuest(req.name(), req.email());
    }

    record RegisterGuestRequest(String name, String email) {}
}
