package rmimessenger.general.commands;

import java.rmi.RemoteException;
import java.util.Calendar;
import rmimessenger.general.FriendStatus;
import rmimessenger.general.IClient;
import rmimessenger.general.IServer;
import rmimessenger.general.MessengerHelper;
import rmimessenger.general.User;
import rmimessenger.general.exceptions.LoginRemoteException;
import rmimessenger.general.responses.Response;
import rmimessenger.general.responses.notifications.Notification;

/**
 * Dieses Command meldet einen User am Server an
 *
 * @author Josef Sochovsky
 * @version 1.0
 */
public class LoginCommand extends Command {

    //Callback interface des Clients
    private IClient callBack;
    //Username und Passwort des Benutzers
    private String uname;
    private String pwHash;

    /**
     * Default-Konstruktor uebergibt dem Server ein Callback interface, das
     * Passwort </br> und den Benutzernamen
     *
     * @param callBack Objekt das gecallbackt werden kann
     * @param uname der Nutzername des anzumeldenden Nutzers
     * @param pw das Password daN
     */
    public LoginCommand(IClient callBack, String uname, String pw) {
        this.callBack = callBack;
        this.uname = uname;
        this.pwHash = MessengerHelper.hashPassword(pw);
    }

    public LoginCommand(IClient callBack, String input) throws IllegalArgumentException {
        this.callBack = callBack;
        if (input == null) throw new IllegalArgumentException("login: 2 Params needed");
        String[] i = input.split(" ");
        if (i.length != 2) throw new IllegalArgumentException("login: 2 Params needed");
        this.uname = i[0];
        this.pwHash = MessengerHelper.hashPassword(i[1]);
    }

    /**
     * Ueberprueft die Eingaben des Nutzers, also gleicht die Informationen
     * </br> mit der Clientliste ab. Wenn alles richtig war, wird das momentan
     * </br> gespeicherte Callback ueberschrieben.
     *
     * @param server
     * @return benachrigt den User ueber einen positiven Ausgang des Logins
     * @throws RegisterRemoteException benachrigt den User ueber einen negativen
     * Ausgang des Logins
     */
    @Override
    public Response execute(IServer server) throws RemoteException {
        User user = server.getClientList().get(getUname());
        if (user != null) {
            if (user.getPwHash().equals(getPwHash())) {
                user.setCallBack(callBack);
                user.setLastLogin(Calendar.getInstance());
                user.notifyFriends((Notification) (server.getNotificationFactory().create("FriendStatusNotification", uname, FriendStatus.ONLINE)));
            } else {
                throw new LoginRemoteException("The given password doesn't match the stored password");
            }

        } else {
            throw new LoginRemoteException("No user '" + uname + "' found");
        }
        return server.getResponseFactory().create("StringResponse", user.getFormattedUserInfo(false) + user.getFormattedEvents());
    }

    /**
     * @return the uname
     */
    public String getUname() {
        return uname;
    }

    /**
     * @return the pwHash
     */
    public String getPwHash() {
        return pwHash;
    }
}
