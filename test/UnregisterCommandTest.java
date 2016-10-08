

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import rmimessenger.general.IClient;
import rmimessenger.general.MessengerHelper;
import rmimessenger.general.User;
import rmimessenger.general.commands.LoginCommand;
import rmimessenger.general.commands.UnregisterCommand;
import rmimessenger.general.exceptions.RegisterRemoteException;
import rmimessenger.server.Server;

/**
 * Testet das UnregisterCommand
 *
 * @author Gabriel Pawlowsky
 * @version 1.0
 */
public class UnregisterCommandTest {

    Server server;

    /**
     * Registriert den User und loggt ihn ein, um ihn spaeter loeschen zu
     * koennen.
     */
    @Before
    public void setUp() throws RemoteException {
        this.server = new Server("data/messenger.state");
        Calendar date = Calendar.getInstance();
        server.getClientList().put("test", new User(null, "test", MessengerHelper.hashPassword("test"), "test", "test", (Calendar) date, "test", "test", new CopyOnWriteArrayList<String>()));
        IClient icl = mock(IClient.class);
        LoginCommand instance = new LoginCommand(icl, "test", "test");
        server.doCommand(instance);
    }

    /**
     * Testet, ob der User erfolgreich geloescht und ausgeloggt wird.
     */
    @Test
    public void testExecuteWhenLoggedIn() throws RemoteException {
        System.out.println("execute");
        UnregisterCommand instance = new UnregisterCommand("test", MessengerHelper.hashPassword("test"));
        server.doCommand(instance);
        assertNull(server.getClientList().get("test"));
    }

    /**
     * Testet, ob das UnregisterCommand eine RegisterRemoteException wirft,
     * wenn<br> der User der geloescht werden soll gar nicht existiert.
     */
    @Test(expected = RegisterRemoteException.class)
    public void testExecuteOnException() throws Exception {
        System.out.println("execute");
        UnregisterCommand instance = new UnregisterCommand("test2", "test2");
        server.doCommand(instance);
    }
}
