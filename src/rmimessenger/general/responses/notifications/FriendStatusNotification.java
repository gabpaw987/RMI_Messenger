package rmimessenger.general.responses.notifications;

import rmimessenger.general.FriendStatus;
import rmimessenger.general.IClient;
import rmimessenger.general.commands.Command;

/**
 * FriendStatusNotification wird geschickt wenn ein Freund offline/online geht.
 *
 * @author Himanshu Mongia
 */
public class FriendStatusNotification extends Notification {

    private FriendStatus s;

    /**
     * Im Konstruktor wird einfach nur der Super-Konstruktor mit Username als
     * Argument aufgerufen.
     *
     * @param uname Username
     * @param s FriendStatus
     */
    public FriendStatusNotification(String uname, FriendStatus s) {
        super(uname);
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
        System.out.println("Friend " + this.getUser() + " went " + s.toString().toLowerCase());
        return null;
    }
}
