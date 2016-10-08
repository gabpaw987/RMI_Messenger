package rmimessenger.general.commands;

import java.rmi.RemoteException;
import rmimessenger.general.Friendship;
import rmimessenger.general.IServer;
import rmimessenger.general.User;
import rmimessenger.general.exceptions.LoginRemoteException;
import rmimessenger.general.exceptions.NoFriendRequestFoundRemoteException;
import rmimessenger.general.responses.Response;
import rmimessenger.general.responses.notifications.Notification;

/**
 * Dieses Command akzeptiert eine Freundschaftsanfrage eines Benutzers
 *
 * @author Josef Sochovsky
 * @version 1.0
 */
public class AcceptFriendCommand extends Command {

    private String otherUser;
    private String hashpw;
    private String uname;

    /**
     * Konstruktor um eine Freundschaftsanfrage zu akzeptieren
     *
     * @param uname Username des Benutzers der die Freundschaftsanfrage erhalten
     * </br> hat
     * @param hashpw
     * @param otherUser Username des Benutzers der die Freundschaftsanfrage
     * </br> gesendet hat
     */
    public AcceptFriendCommand(String uname, String hashpw, String otherUser) {
        if (uname == null || hashpw == null) throw new IllegalArgumentException("Insufficient Access");
        if (otherUser == null) throw new IllegalArgumentException("accept: Invalid friend parameter");
        this.hashpw = hashpw;
        this.otherUser = otherUser;
        this.uname = uname;
    }

    /**
     * Ueberprueft die Anfrage und fuehrt die Umwandlung in eine Freundschaft
     * durch
     *
     * @param server
     * @return benachrigt den User ueber einen positiven Ausgang der Umwandlung
     * @throws RemoteException benachrigt den User ueber einen negativen Ausgang
     * der Umwandlung
     */
    @Override
    public Response execute(IServer server) throws RemoteException {
        boolean found = false;
        User user = server.getClientList().get(uname);
        if (!user.getPwHash().equals(hashpw)) {
            throw new LoginRemoteException("The given password doesn't match the stored password");
        }
        // es wird nach der Freundschaftsanfrage gesucht, um zu verifizieren, dass es den User Anfrage auch gibt
        for (User u : user.getIncomingRequests()) {
            if (u.getUname().equals(otherUser)) {
                found = true;
            }
        }
        if (!found) {
            throw new NoFriendRequestFoundRemoteException("There is no matching Friendrequest for the give users: "
                    + uname + "&" + otherUser);
        }
        User other = server.getClientList().get(otherUser);
        // setzen saemtlicher Attribute die die Anfrage zur Freundschaft macht
        other.getOutgoingRequests().remove(user);
        user.getIncomingRequests().remove(other);
        other.getFriends().put(user.getUname(), user);
        user.getFriends().put(other.getUname(), other);
        other.notify((Notification) server.getNotificationFactory().create("FriendshipNotification", uname, Friendship.ACCEPTED));
        return server.getResponseFactory().create("StringResponse", "Friendship accepted");
    }
}
