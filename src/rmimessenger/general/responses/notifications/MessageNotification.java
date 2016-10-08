package rmimessenger.general.responses.notifications;

import rmimessenger.general.IClient;
import rmimessenger.general.commands.Command;

/**
 * MessageNotification zeigt eine Nachricht von einem Freund.
 *
 * @author Himanshu Mongia
 */
public class MessageNotification extends Notification {

    private String message;

    /**
     * Im Konstruktor wird einfach nur der Super-Konstruktor mit Username als
     * Argument aufgerufen.
     *
     * @param uname Username
     * @param message die Nachricht
     */
    public MessageNotification(String uname, String message) {
        super(uname);
        this.message = message;
    }

    /**
     * Die Execute-Methode wird am Client aufgerufen und verursacht eine
     * Ausgabe.
     *
     * @param client Client
     * @return null
     */
    @Override
    public Command execute(IClient client) {
        System.out.println("Message from Friend " + this.getUser() + ": " + message);
        return null;
    }

    public String getMessage() {
        return message;
    }
}
