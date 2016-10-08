

import java.rmi.RemoteException;
import org.junit.Test;
import rmimessenger.general.commands.SendMessageCommand;
import rmimessenger.general.exceptions.RecipientNotAvailableRemoteException;
import rmimessenger.server.Server;

/**
 * Eine Testklasse, die das SendMessageCommand sendet
 *
 * @author Josef Sochosvky
 */
public class SendMessageCommandTest {

    /**
     * Diese Testmethode instanziert einen Server und hat somit eine leere</br>
     * ConcurrentHashMap, danach soll ein SendMessageCommand ausgefuehrt</br>
     * werden, jedoch ist dieses mit Userdaten nicht gefuellt, die nicht</br>
     * existieren</br>
     */
    @Test(expected = RecipientNotAvailableRemoteException.class)
    public void testExecute() throws RemoteException {
        System.out.println("execute");
        Server server;
        server = new Server("data/messenger.state");
        server.doCommand(new SendMessageCommand("test", "fail", "test Nachrichtenkopf"));
    }

    /**
     * Diese Testmethode uebergibt dem Konstruktor von SendMessageCommand </br>
     * falsche Daten und erwartet eine IllegalArgumentException </br>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstruct() throws RemoteException {
        System.out.println("Konstruktor");
        SendMessageCommand sendMessageCommand = new SendMessageCommand("test", "fail", "test");
    }
}
