package service.guest;

import model.person.Guest;
import repository.guest.GuestDao;

import java.util.List;

public class GuestService implements IGuestService {
    private final GuestDao guestDao;

    public GuestService(GuestDao guestDao) {
        this.guestDao = guestDao;
    }

    @Override
    public Guest registerGuest(String name, String email) {
        return guestDao.insert(new Guest(0, name, email, 0));
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestDao.findAll();
    }

    @Override
    public Guest getGuest(int guestId) {
        Guest guest = guestDao.findById(guestId);
        if (guest == null) {
            throw new IllegalArgumentException("Nu exista client cu id-ul " + guestId + ".");
        }
        return guest;
    }
}
