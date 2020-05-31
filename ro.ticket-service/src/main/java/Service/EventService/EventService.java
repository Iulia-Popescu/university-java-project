package Service.EventService;
import Event.Event;

public interface EventService {
    void setUpArtEvent(Event event);
    void setUpMusicEvent(Event event);
    void cancelArtEvent(Event event);
    void cancelMusicEvent(Event event);
}
