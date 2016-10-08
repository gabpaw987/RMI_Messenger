

import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;
import rmimessenger.general.IClient;
import rmimessenger.general.MessengerHelper;
import rmimessenger.general.User;
import rmimessenger.general.commands.LoginCommand;
import rmimessenger.general.exceptions.LoginRemoteException;
import rmimessenger.server.Server;

/**
 * Diese Testklasse testet das LoginCommand
 *
 * @author Josef Sochovsky
 * @version 1.0
 */
public class LoginCommandTest {

    /**
     * Das LoginCommand soll erkennen das die uebergebenen Nutzerdaten
     * nicht</br> richtig sind. Dazu muss eine </br>
     * <code> LoginRemoteException </code> geworfen werden.</br>
     */
    @Test(expected = LoginRemoteException.class)
    public void testExecute() throws Exception {
        System.out.println("execute");
        Server server = new Server("data/messenger.state");
        LoginCommand instance = new LoginCommand(null, "test", "test");
        server.doCommand(instance);
    }

    /**
     * User wird angemeldet und ueberprueft ob das Callback uebergeben wurde
     */
    @Test
    public void testLoginUser() throws Exception {
        System.out.println("execute");
        Server server = new Server("data/messenger.state");
        Calendar date = Calendar.getInstance();
        server.getClientList().put("test", new User(null, "test", MessengerHelper.hashPassword("test"), "test", "test", (Calendar) date, "test", "test", new CopyOnWriteArrayList<String>()));
        IClient icl = mock(IClient.class);
        LoginCommand instance = new LoginCommand(icl, "test", "test");
        server.doCommand(instance);
        assertNotNull(server.getClientList().get("test").getCallBack());
    }
}
