package rmimessenger.general.commands;

import java.rmi.RemoteException;
import rmimessenger.general.Friendship;
import rmimessenger.general.IServer;
import rmimessenger.general.User;
import rmimessenger.general.exceptions.NoFriendRequestFoundRemoteException;
import rmimessenger.general.responses.Response;
import rmimessenger.general.responses.notifications.Notification;

/**
 * Dieses Kommandow wird gesendet, wenn ein User eine Freundschaftsanfrage<br>
 * eines anderen Users ablehnt. Es benachrichtigt auch den Sender der <br>
 * Freundschaftsanfrage darueber, dass diese abgelehnt wurde.
 *
 * @author Gabriel Pawlowsky
 * @version 1.0
 */
public class RejectFriendCommand extends Command {
    //Daten des Senders der Freundschaftsanfrage

    private String sender;
    private String pwHash;
    //Empfaenger der Freundschaftsanfrage, nicht des Kommandos selbst
    private String recipient;

    /**
     * Kosntruktor, der die Attribute setzt.
     *
     * @param uname Empfaenger der Freundschaftsanfrage
     * @param pwHash Passwort des selben Users
     * @param sender der Username des Senders des Kommandos
     */
    public RejectFriendCommand(String uname, String pwHash, String sender) {
        if (uname == null || pwHash == null) throw new IllegalArgumentException("Insufficient Access");
        if (sender == null) throw new IllegalArgumentException("reject: Invalid friend parameter");
        this.pwHash = pwHash;
        this.sender = sender;
        this.recipient = uname;
    }

    /**
     * Execute-Methode des Kommandos. Diese lehnt die Freundschaftsanfrage
     * ab<br> und benachrichtigt den Sender der Freundschaftsanfrage ueber das
     * Ereignis.
     *
     * @param server die aufrufende Serverinstanz
     * @return ein Stringresponse, das angibt, dass die Freundschaftsanfrage
     * abgelehnt wurde
     * @throws RemoteException tritt z.B. auf wenn das Kommando mit
     * inkonstistenten Daten befuellt wurde.
     */
    @Override
    public Response execute(IServer server) throws RemoteException {
        boolean found = false;
        User recipient = server.getClientList().get(this.recipient);
        User sender = server.getClientList().get(this.sender);

        for (User u : recipient.getIncomingRequests()) {
            if (u.getUname().equals(this.sender)) {
                found = true;
            }
        }
        if (!found) {
            throw new NoFriendRequestFoundRemoteException();
        }

        sender.getOutgoingRequests().remove((recipient));
        recipient.getIncomingRequests().remove((sender));

        sender.notify((Notification) server.getNotificationFactory().create("FriendshipNotification", recipient, Friendship.REJECTED));
        return server.getResponseFactory().create("StringResponse", "Friendship rejected");
    }
}
