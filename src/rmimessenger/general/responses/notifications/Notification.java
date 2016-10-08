package rmimessenger.general.responses.notifications;

import java.util.Calendar;
import rmimessenger.general.responses.Response;

/**
 * Eine spezielle Art der Response
 *
 * @author Himanshu Mongia
 */
public abstract class Notification implements Response {

    private String user;
    private Calendar time;

    /**
     * Im Konstruktor wird einfach nur der User und der Zeitpunkt gespeichert.
     *
     * @param user Username
     */
    public Notification(String user) {
        this(user, Calendar.getInstance());
    }

    /**
     * Im Konstruktor wird einfach nur der User und der Zeitpunkt gespeichert.
     *
     * @param user Username
     * @param time Zeitpunkt
     */
    public Notification(String user, Calendar time) {
        this.user = user;
        this.time = time;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the time
     */
    public Calendar getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Calendar time) {
        this.time = time;
    }
}
