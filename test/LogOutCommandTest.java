

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
import rmimessenger.general.commands.LogOutCommand;
import rmimessenger.general.commands.LoginCommand;
import rmimessenger.general.exceptions.LoginRemoteException;
import rmimessenger.server.Server;

/**
 * Diese Testklasse testet das LogOutCommand.
 *
 * @author Gabriel Pawlowsky
 * @version 1.0
 */
public class LogOutCommandTest {

    Server server;

    /**
     * Registriert den User und loggt ihn ein, um ihn spaeter ausloggen zu
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
     * Testet, ob der User erfolgreich ausgeloggt wird.
     */
    @Test
    public void testExecuteWhenLoggedIn() throws RemoteException {
        System.out.println("execute");
        LogOutCommand instance = new LogOutCommand("test", MessengerHelper.hashPassword("test"));
        server.doCommand(instance);
        assertNull(server.getClientList().get("test").getCallBack());
    }

    /**
     * Diese Methode soll testen, ob bei der Uebergabe falscher Userdaten
     * eine<br> RemoteException geworfen wird.
     */
    @Test(expected = LoginRemoteException.class)
    public void testExecuteOnException() throws Exception {
        System.out.println("execute");
        LogOutCommand instance = new LogOutCommand("test", "test");
        server.doCommand(instance);
    }
}
