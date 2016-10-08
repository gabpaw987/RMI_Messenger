package rmimessenger.general.commands;

import java.rmi.RemoteException;
import rmimessenger.general.IServer;
import rmimessenger.general.exceptions.RecipientNotAvailableRemoteException;
import rmimessenger.general.responses.Response;
import rmimessenger.general.responses.ResponseFactory;
import rmimessenger.general.responses.notifications.Notification;

/**
 * Dieses Command uebergibt eine Nachricht von einem Benutzer zu einem Benutzer
 *
 * @author Josef Sochovsky
 * @version 1.0
 */
public class SendMessageCommand extends Command {

    private String recipient;
    private String sender;
    private String hashpw;
    private String message;

    /**
     * Konstruktor um eine Nachricht zu uebertragen
     *
     * @param uname Sender der Nachricht
     * @param hashpw
     * @param recipient Empfaenger der Nachricht
     * @param message Nachricht die uebertragen werden soll
     */
    public SendMessageCommand(String uname, String hashpw, String recipient, String message) {
        this.recipient = recipient;
        this.sender = uname;
        this.hashpw = (hashpw);
        this.message = message;
    }

    public SendMessageCommand(String uname, String hashpw, String input) throws IllegalArgumentException {
        if (uname == null || hashpw == null) throw new IllegalArgumentException("Insufficient Access");
        if (input == null) throw new IllegalArgumentException("send: Invalid friend parameter");

        this.sender = uname;
        this.hashpw = hashpw;

        int index = input.indexOf(" ");
        if (index == -1) {
            throw new IllegalArgumentException("send: 2 Params needed");
        }
        this.recipient = input.substring(0, index);
        this.message = input.substring(index + 1);
    }

    /**
     * Ueberprueft die Angaben der Nutzerdaten und uebertraegt dann die </br>
     * Nachricht
     *
     * @param server
     * @return benachrigt den User ueber einen positiven Ausgang der
     * Uebertragung
     * @throws RemoteException benachrigt den User ueber einen negativen Ausgang
     * der Uebertragung
     */
    @Override
    public Response execute(IServer server) throws RemoteException {
        // ueberpruefen ob die angegeben User existieren 
        if (!server.getClientList().containsKey(recipient) || !server.getClientList().get(sender).getPwHash().equals(hashpw)) {
            throw new RecipientNotAvailableRemoteException("The named recipient isn't availaible in the User-ConcurrentHashMap");
        }
        server.getClientList().get(recipient).notify((Notification) server.getNotificationFactory().create("MessageNotification", sender, message));
        return new ResponseFactory().create("StringResponse", "Message successfully sent");
    }
}
