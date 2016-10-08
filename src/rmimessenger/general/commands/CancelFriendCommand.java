package rmimessenger.general.commands;

import java.rmi.RemoteException;
import rmimessenger.general.Friendship;
import rmimessenger.general.IServer;
import rmimessenger.general.User;
import rmimessenger.general.exceptions.LoginRemoteException;
import rmimessenger.general.exceptions.RecipientNotAvailableRemoteException;
import rmimessenger.general.responses.Response;
import rmimessenger.general.responses.notifications.Notification;

/**
 * Dieses Command loescht eine vorhandene Freundschaft
 *
 * @author Josef Sochovsky
 * @version 1.0
 */
public class CancelFriendCommand extends Command {

    private String recipient;
    private String sender;
    private String hashpw;

    /**
     * Konstruktor um die Freundschaft zu loeschen
     *
     * @param uname Sender der zu loeschenden Freundschaft
     * @param hashpw
     * @param recipient Empfaenger der zu loeschenden Freundschaft
     */
    public CancelFriendCommand(String uname, String hashpw, String recipient) {
        if (uname == null || hashpw == null) {
            throw new IllegalArgumentException("Insufficient Access");
        }
        if (recipient == null) {
            throw new IllegalArgumentException("cancel: Invalid friend parameter");
        }

        this.recipient = recipient;
        this.sender = uname;
        this.hashpw = hashpw;
    }

    /**
     * Ueberprueft die Eingaben und loescht, wenn alle Eingaben korrekt waren,
     * </br>die Freundschaft
     *
     * @param server
     * @return benachrigt den User ueber einen positiven Ausgang des Loeschens
     * @throws RemoteException benachrigt den User ueber einen negativen Ausgang
     * des Loeschens
     */
    @Override
    public Response execute(IServer server) throws RemoteException {
        if (!server.getClientList().containsKey(recipient)) {
            throw new RecipientNotAvailableRemoteException("The named recipient isn't availaible in the User-ConcurrentHashMap");
        }
        User senderUser = server.getClientList().get(sender);
        User recipientUser = server.getClientList().get(recipient);
        if (!senderUser.getPwHash().equals(hashpw)) {
            throw new LoginRemoteException("The given password doesn't match the stored password");
        }
        senderUser.getFriends().remove(recipientUser.getUname());
        recipientUser.getFriends().remove(senderUser.getUname());
        recipientUser.notify((Notification) server.getNotificationFactory().create("FriendshipNotification", sender, Friendship.CANCELLED));
        return server.getResponseFactory().create("StringResponse", "Friendship successfully candeled");
    }
}
