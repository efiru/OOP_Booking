package service.guest;

import model.person.Guest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestService implements IGuestService {
    private final Map<Integer, Guest> guests = new HashMap<>();
    private int nextGuestId = 1;

    @Override
    public Guest registerGuest(String name, String email) {
        Guest guest = new Guest(nextGuestId++, name, email, 0);
        guests.put(guest.getId(), guest);
        return guest;
    }

    @Override
    public List<Guest> getAllGuests() {
        return new ArrayList<>(guests.values());
    }

    @Override
    public Guest getGuest(int guestId) {
        Guest guest = guests.get(guestId);
        if (guest == null) {
            throw new IllegalArgumentException("Nu exista client cu id-ul " + guestId + ".");
        }
        return guest;
    }
}
