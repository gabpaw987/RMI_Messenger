package rmimessenger.general.responses.notifications;

import rmimessenger.general.Friendship;
import rmimessenger.general.IClient;
import rmimessenger.general.commands.Command;

/**
 * FriendshipNotification repraesentiert eine Freundschaftsanfrage.
 *
 * @author Himanshu Mongia
 */
public class FriendshipNotification extends Notification {

    private Friendship s;

    /**
     * Im Konstruktor wird einfach nur der Super-Konstruktor mit Username als
     * Argument aufgerufen.
     *
     * @param uname Username
     * @param s Friendship
     */
    public FriendshipNotification(String uname, Friendship s) {
        super(uname);
        this.s = s;
    }

    public Friendship getS() {
        return s;
    }

    public void setS(Friendship s) {
        this.s = s;
    }

    /**
     * Die Execute-Methode wird am Client aufgerufen und verursacht eine
     * Ausgabe.
     *
     * @param client Client
     * @return null
     */
    @Override
    public Command execute(IClient client) {
        System.out.println("Friedship " + s.toString().toLowerCase() + " from " + this.getUser());
        return null;
    }
}
