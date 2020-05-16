package controller;

import dao.EventDao;
import model.Event;

import java.util.List;
import java.util.Optional;

public class EventController {

    private EventDao eventDao;

    private EventController() {
        eventDao = new EventDao(ConnectionManager.getInstance().getConnection());
    }

    private static final class SingletonHolder {
        private static final EventController INSTANCE = new EventController();
    }

    public static EventController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public boolean addEvent(Event event) {
        Optional<Event> eventOptional = eventDao.findByDate(event.getDate());
        if (eventOptional.isEmpty()) {
            return eventDao.addEvent(event);
        } else {
            return false;
        }
    }

    public boolean deleteEvent(int id) {
        return eventDao.deleteEvent(id);
    }

    public List<Event> findAll() {
        return eventDao.findAll();
    }

}
