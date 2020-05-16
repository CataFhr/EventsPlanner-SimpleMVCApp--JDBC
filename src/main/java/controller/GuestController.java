package controller;

import dao.GuestDao;
import model.Guest;

import java.util.List;
import java.util.Optional;

public class GuestController {

    private GuestDao guestDao;

    private GuestController() {
        guestDao = new GuestDao(ConnectionManager.getInstance().getConnection());
    }

    private static final class SingletonHolder {
        private static final GuestController INSTANCE = new GuestController();
    }

    public static GuestController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public List<Guest> findByEvent(int eventId) {
        return guestDao.findByEvent(eventId);
    }

    public boolean addGuest(Guest guest) {
        Optional<Guest> guestOptional = guestDao.findByPhoneNumber(guest.getPhoneNumber(), guest.getEventId());
        if (guestOptional.isEmpty()) {
            return guestDao.addGuest(guest);
        } else {
            return false;
        }
    }

}
