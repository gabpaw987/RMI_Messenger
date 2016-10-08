package rmimessenger.general.commands;

import java.rmi.RemoteException;
import rmimessenger.general.IServer;
import rmimessenger.general.responses.Response;

/**
 * Dieses Kommando wird aufgreufen wenn der Client seine Software beenden
 * moechte.<br> Ist der User dabei noch eingeloggt muss er im Laufe dieses
 * Kommandos ausgeloggt<br> werden.
 *
 * @author Gabriel Pawlowsky
 * @version 1.0
 */
public class StopCommand extends Command {

    private String uname;
    private String pwHash;

    /**
     * Konstruktor, der alle Attribute setzt.
     *
     * @param uname Username des aufrufenden Users
     * @param pwHash Passwort des selben Users
     */
    public StopCommand(String uname, String pwHash) {
        this.uname = uname;
        this.pwHash = pwHash;
    }

    /**
     * Execute-Methode des Kommandos. Dabei wird einfach nur der Users
     * ausgeloggt,<br> wenn er noch eingeloggt ist. Der Client wird ausserhalb
     * geschlossen.
     *
     * @param server die aufrufende Serverinstanz
     * @return StringResponse die Aufschluesse ueber das Ergebnis gibt.
     * @throws RemoteException tritt z.B. auf, wenn der User existiert, aber<br>
     * nicht ausgeloggt werden kann
     */
    @Override
    public Response execute(IServer server) throws RemoteException {
        //loggt den User aus, falls er eingeloggt war.
        if (uname == null) return null;
        if (server.getClientList().containsKey(this.uname) && server.getClientList().get(this.uname).getCallBack() != null
                && this.pwHash.equals(server.getClientList().get(this.uname).getPwHash()))
            return new LogOutCommand(this.uname, this.pwHash).execute(server);
        else
            return server.getResponseFactory().create("StringResponse", "User was not logged in.");
    }
}
