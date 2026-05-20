package service.guest;

import model.person.Guest;
import repository.guest.GuestDao;
import service.audit.AuditService;

import java.util.List;

public class GuestService implements IGuestService {
    private final GuestDao guestDao;
    private final AuditService auditService;

    public GuestService(GuestDao guestDao, AuditService auditService) {
        this.guestDao = guestDao;
        this.auditService = auditService;
    }

    @Override
    public Guest registerGuest(String name, String email) {
        auditService.log("registerGuest");
        return guestDao.insert(new Guest(0, name, email, 0));
    }

    @Override
    public List<Guest> getAllGuests() {
        auditService.log("getAllGuests");
        return guestDao.findAll();
    }

    @Override
    public Guest getGuest(int guestId) {
        auditService.log("getGuest");
        Guest guest = guestDao.findById(guestId);
        if (guest == null) {
            throw new IllegalArgumentException("Nu exista client cu id-ul " + guestId + ".");
        }
        return guest;
    }
}
