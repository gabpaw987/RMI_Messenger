package rmimessenger.general.commands;

import java.rmi.RemoteException;
import rmimessenger.general.IServer;
import rmimessenger.general.User;
import rmimessenger.general.exceptions.RegisterRemoteException;
import rmimessenger.general.responses.Response;

/**
 * Dieses Kommando wird aufgerufen, wenn ein User seinen Account loeachen
 * moechte.<br> Dabei wird einfach nur der Account-Eintrag aus dem Server
 * entfernt und der <br> User davor gegebenenfalls noch ausgeloggt.
 *
 * @author Gabriel Pawlowsky
 * @version 1.0
 */
public class UnregisterCommand extends Command {

    private String uname;
    private String pwHash;

    /**
     * Konstruktor, der alle Attribute setzt.
     *
     * @param uname der Username des aufrufenden Users
     * @param password Passwort des selben Users
     */
    public UnregisterCommand(String uname, String pwHash) {
        if (uname == null || pwHash == null) {
            throw new IllegalArgumentException("Insufficient Access");
        }

        this.uname = uname;
        this.pwHash = pwHash;
    }

    /**
     * Execute-Methode des Kommandos. Diese loggt ggf. den User aus und<br>
     * loescht danach seinen Accopunt aus dem Server.
     *
     * @param server die aufrufende Serverinstanz
     * @return eine StringResponse, die Auskunft ueber die Vorgangsweise gibt.
     * @throws RemoteException tritt z.B. auf wenn der User gar nicht
     * registriert war.
     */
    @Override
    public Response execute(IServer server) throws RemoteException {
        User user = server.getClientList().get(this.uname);
        if (user != null && user.getPwHash().equals(this.pwHash)) {
            (new CommandFactory()).create("LogOutCommand", this.uname, this.pwHash).execute(server);
            server.getClientList().remove(this.uname);
            for (User client : server.getClientList().values()) {
                if (client.getFriends() != null) {
                    if (client.getFriends().size() > 0 && client.getFriends().containsKey(this.uname)) {
                        client.getFriends().remove(this.uname);
                    }
                }
                if (client.getIncomingRequests() != null) {
                    if (client.getIncomingRequests().size() > 0 && client.getIncomingRequests().contains(user)) {
                        client.getIncomingRequests().remove(user);
                    }
                }
                if (client.getOutgoingRequests() != null) {
                    if (client.getOutgoingRequests().size() > 0 && client.getOutgoingRequests().contains(user)) {
                        client.getOutgoingRequests().remove(user);
                    }
                }
            }
        } else {
            throw new RegisterRemoteException("User not found");
        }
        return server.getResponseFactory().create("StringResponse", "User successfully unregistered!");
    }
}
