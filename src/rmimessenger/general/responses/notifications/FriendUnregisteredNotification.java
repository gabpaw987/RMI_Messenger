package rmimessenger.general.responses.notifications;

import rmimessenger.general.IClient;
import rmimessenger.general.commands.Command;

/**
 * FriendUnregisteredNotification zeigt dass ein User sich abgemeldet hat.
 *
 * @author Himanshu Mongia
 */
public class FriendUnregisteredNotification extends Notification {

    /**
     * Im Konstruktor wird einfach nur der Super-Konstruktor mit Username als
     * Argument aufgerufen.
     *
     * @param uname Username
     */
    public FriendUnregisteredNotification(String uname) {
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
        System.out.println("Your friend " + this.getUser() + " unregistered!");
        return null;
    }
}