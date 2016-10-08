package rmimessenger.general.commands;

import java.rmi.RemoteException;
import rmimessenger.general.IServer;
import rmimessenger.general.User;
import rmimessenger.general.exceptions.GetUserInfoRemoteException;
import rmimessenger.general.responses.Response;

/**
 * Dieses Kommando, wird dazu benutzt, um alle oeffentlichen Informationen<br>
 * eines anderen Users abzufragen.
 *
 * @author Gabriel Pawlowsky
 * @version 1.0
 */
public class GetUserInfoCommand extends Command {

    private String uname;
    private String pwHash;
    private String searchedName;

    /**
     * Komstruktor, der die entsprechenden Werte setzt.
     *
     * @param uname der Username des aufrufenden Users
     * @param pwHash das Passwort des selben Users
     * @param searchedName der Username, dessen Informationen abgerufen werden
     * sollen
     */
    public GetUserInfoCommand(String uname, String pwHash, String searchedName) {
        if (uname == null || pwHash == null) throw new IllegalArgumentException("Insufficient Access");
        if (searchedName == null) throw new IllegalArgumentException("getInfo: Invalid friend parameter");
        this.uname = uname;
        this.pwHash = pwHash;
        this.searchedName = searchedName;
    }

    /**
     * Execute-Methode des Kommandos. Diese sieht nach ob der angefragte
     * Benutzer<br> vorhanden ist und gitb gegebenenfalls alle seine
     * oeffentlichen Informationen<br> in einem StringResponse zurueck.
     *
     * @param server die aufrufende Serverinstanz
     * @return ein StringResponse mit allen oeffentlichen Informationen des
     * Users
     * @throws RemoteException tritt z.B. auf wenn der U_ser nicht vorhanden ist
     */
    @Override
    public Response execute(IServer server) throws RemoteException {
        String userInfo;
        User user = server.getClientList().get(this.uname);
        if (user != null && user.getPwHash().equals(this.pwHash) && server.getClientList().get(this.searchedName) != null) {
            userInfo = server.getClientList().get(this.searchedName).getFormattedUserInfo(true);
        } else {
            throw new GetUserInfoRemoteException("Wrong username or sender specified!");
        }
        return server.getResponseFactory().create("StringResponse", userInfo);
    }
}
