package service.guest;

import model.person.Guest;

import java.util.List;

public interface IGuestService {
    Guest registerGuest(String name, String email);
    List<Guest> getAllGuests();
    Guest getGuest(int guestId);
}
