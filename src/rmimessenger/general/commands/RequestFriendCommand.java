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
 * Dieses Command erzeugt eine Freundschaftsanfrage
 *
 * @author Josef Sochovsky
 * @version 1.0
 */
public class RequestFriendCommand extends Command {

    private String recipient;
    private String sender;
    private String hashpw;

    /**
     * Konstuktor um eine Freundschaftsanfrage zu erzeugen
     *
     * @param uname Username des Senders der Anfrage
     * @param hashpw
     * @param input Username des Empfaengers der Anfrage
     */
    public RequestFriendCommand(String uname, String hashpw, String input) {
        if (uname == null || hashpw == null) {
            throw new IllegalArgumentException("Insufficient Access");
        }
        if (input == null) {
            throw new IllegalArgumentException("request: Invalid friend parameter");
        }
        this.recipient = input;
        this.sender = uname;
        this.hashpw = hashpw;
    }

    /**
     * Ueberprueft die Eingaben und erzeugt die Freundschaftsanfrage und </br>
     * informiert den Empfaenger der Freundschaftsanfrage
     *
     * @param server
     * @return benachrigt den User ueber einen positiven Ausgang der
     * Freundschaftsanfrage
     * @throws RemoteException benachrigt den User ueber einen negativen Ausgang
     * der Freundschaftsanfrage
     */
    @Override
    public Response execute(IServer server) throws RemoteException {
        if (sender.equals(recipient)) {
            throw new RecipientNotAvailableRemoteException("Users can't add themselves as friends");
        }

        if (!server.getClientList().get(sender).getPwHash().equals(hashpw)) {
            throw new LoginRemoteException("The given password doesn't match the stored pasword");
        }
        if (!server.getClientList().containsKey(recipient)) {
            throw new RecipientNotAvailableRemoteException("The named recipient isn't availaible in the User-ConcurrentHashMap");
        }
        User recipientUser = server.getClientList().get(recipient);
        User senderUser = server.getClientList().get(sender);
        recipientUser.getIncomingRequests().add(senderUser);
        senderUser.getOutgoingRequests().add(server.getClientList().get(recipient));
        recipientUser.notify((Notification) server.getNotificationFactory().create("FriendshipNotification", sender, Friendship.REQUESTED));
        return server.getNotificationFactory().create("StringResponse", "FriendshipNotification successfully sent");
    }
}
