package rmimessenger.general.commands;

import java.rmi.RemoteException;
import rmimessenger.general.IServer;
import rmimessenger.general.User;
import rmimessenger.general.exceptions.LoginRemoteException;
import rmimessenger.general.responses.Response;

/**
 * Dieses Command ermoeglicht es nach einem User zu suchen. Dabei kann nicht<br>
 * nach dem Namen des Users sondern auch nach einigen anderen Daten gesucht
 * werden.
 *
 * @author Gabriel Pawlowsky
 * @version 1.0
 */
public class FindUserCommand extends Command {

    private String uname;
    private String pwHash;
    private String searchString;

    /**
     * Konstruktor, der die uebergebenen Werte setzt.
     *
     * @param uname der Username des ausfuehrenden Users
     * @param pwHash das Passwort des selben Users
     * @param searchString der String nach dem gesucht werden soll
     */
    public FindUserCommand(String uname, String pwHash, String searchString) {
        if (uname == null || pwHash == null) {
            throw new IllegalArgumentException("Insufficient Access");
        }
        if (searchString == null) {
            throw new IllegalArgumentException("find: Invalid search parameter");
        }
        this.uname = uname;
        this.pwHash = pwHash;
        //damit nach allen Gross- und Kleinschreibvarianten des Suchstrings gesucht werden kann
        this.searchString = searchString.toLowerCase();
    }

    /**
     * Execute-Methode des Commands. Diese bekommt den Server uebergeben und
     * <br> msucht damit nach allen passenden Users und gibt diese in einer <br>
     * StringResponse zurueck.
     *
     * @param server die aufrufende Serverinstanz
     * @return StringResponse mit allen gefundenen Usern
     * @throws RemoteException Wenn ein Fehler waehrend der Suche auftritt
     */
    @Override
    public Response execute(IServer server) throws RemoteException {
        if (!server.getClientList().containsKey(this.uname)) {
            throw new LoginRemoteException("User not available!");
        }
        if (!server.getClientList().get(uname).getPwHash().equals(pwHash)) {
            throw new LoginRemoteException("The given password doesn't match the stored password");
        }
        String usersFound = "";
        //Sucht in allen Users und allen Werte nach dem Suchstring
        for (User user : server.getClientList().values()) {
            if (user.getUname().toLowerCase().equals(this.searchString)
                    || user.getFirstName().toLowerCase().equals(this.searchString)
                    || user.getLastName().toLowerCase().equals(this.searchString)
                    || user.getProfession().toLowerCase().equals(this.searchString)
                    || user.getEmail().toLowerCase().equals(this.searchString)) {
                usersFound += user.getUname() + ":\n";
                usersFound += user.getFormattedUserInfo(true);
            } else {
                if (user.getHobbies() != null) {
                    for (String hobby : user.getHobbies()) {
                        if (hobby.toLowerCase().equals(this.searchString)) {
                            usersFound += user.getUname() + ":\n";
                            usersFound += user.getFormattedUserInfo(true);
                        }
                    }
                }
            }
        }
        return server.getResponseFactory().create("StringResponse", usersFound);
    }
}
