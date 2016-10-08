

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
import rmimessenger.general.commands.GetUserInfoCommand;
import rmimessenger.general.commands.LoginCommand;
import rmimessenger.general.exceptions.GetUserInfoRemoteException;
import rmimessenger.server.Server;

/**
 * Diese Testklasse testet das GetUserInfoCommand.
 *
 * @author Gabriel Pawlowsky
 * @version 1.0
 */
public class GetUserInfoCommandTest {

    Server server;

    /**
     * Registriert zwei User und loggt einen ein, um <br> vom Anderen
     */
    @Before
    public void setUp() throws RemoteException {
        this.server = new Server("data/messenger.state");
        Calendar date = Calendar.getInstance();
        server.getClientList().put("test", new User(null, "test", MessengerHelper.hashPassword("test"), "test", "test", (Calendar) date, "test", "test", new CopyOnWriteArrayList<String>()));
        server.getClientList().put("test2", new User(null, "test2", MessengerHelper.hashPassword("test2"), "test2", "test2", (Calendar) date, "test2", "test2", new CopyOnWriteArrayList<String>()));
        IClient icl = mock(IClient.class);
        LoginCommand instance = new LoginCommand(icl, "test", "test");
        server.doCommand(instance);
    }

    /**
     * Testet, ob der zweite User gefunden werden kann.
     */
    @Test
    public void testExecuteGetInfoOfSecondUser() throws RemoteException {
        System.out.println("execute");
        GetUserInfoCommand instance = new GetUserInfoCommand("test", MessengerHelper.hashPassword("test"), "test2");
        assertNotNull(server.doCommand(instance));
    }

    /**
     * Diese Methode soll testen, ob bei der Uebergabe falscher Userdaten<br>
     * und falschem Suchstring eine GetUserInfoRemoteException geworfen wird.
     */
    @Test(expected = GetUserInfoRemoteException.class)
    public void testExecute() throws Exception {
        System.out.println("execute");
        GetUserInfoCommand instance = new GetUserInfoCommand("test", "test", "test");
        server.doCommand(instance);
    }
}
