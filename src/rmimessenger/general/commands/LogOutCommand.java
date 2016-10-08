package rmimessenger.general.commands;

import java.rmi.RemoteException;
import java.util.Calendar;
import rmimessenger.general.FriendStatus;
import rmimessenger.general.IServer;
import rmimessenger.general.User;
import rmimessenger.general.exceptions.LoginRemoteException;
import rmimessenger.general.responses.Response;
import rmimessenger.general.responses.notifications.Notification;

/**
 * Diese Kommando wird aufgerufen, wenn ein User sich ausloggen moechte.<br> Es
 * loggt ihn im System aus und benachrichtigt alle seine Freude darueber.<br>
 *
 * @author Gabriel Pawlowsky
 * @version 1.0
 */
public class LogOutCommand extends Command {

    private String uname;
    private String pwHash;

    /**
     * Kosntruktor, der alle Attribute setzt.
     *
     * @param uname der Username des aufrufenden Users
     * @param pwHash das Passwort des selben Users
     */
    public LogOutCommand(String uname, String pwHash) {
        if (uname == null || pwHash == null) throw new IllegalArgumentException("Insufficient Access");

        this.uname = uname;
        this.pwHash = pwHash;
    }

    /**
     * Die Execute-Methode des Kommandos. Diese loggt den Benutzer aus<br> und
     * benachrichtigt alle seine Freunde darueber.
     *
     * @param server die aufrufende Serverinstanz
     * @return ein StringResponse, das angibt, dass das Ausloggen erfolgreich
     * war
     * @throws RemoteException wird z.B. geworfen wenn der User niocht
     * ausgeloggt<br> werden kann (z.B. falsche Informationen)
     */
    @Override
    public Response execute(IServer server) throws RemoteException {
        if (uname == null) {
            throw new LoginRemoteException("User is not available.");
        }
        User user = server.getClientList().get(this.uname);
        if (user != null && user.getPwHash().equals(this.pwHash)) {
            user.setCallBack(null);
            user.setLastLogin(Calendar.getInstance());
            user.notifyFriends((Notification) (server.getNotificationFactory().create("FriendStatusNotification", uname, FriendStatus.OFFLINE)));
        } else {
            throw new LoginRemoteException("User is not available.");
        }
        return server.getResponseFactory().create("StringResponse", "Logout was successful.");
    }
}
