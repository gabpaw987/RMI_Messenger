

import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;
import rmimessenger.general.IClient;
import rmimessenger.general.MessengerHelper;
import rmimessenger.general.User;
import rmimessenger.general.commands.RegisterCommand;
import rmimessenger.server.Server;

/**
 * Testet das RegisterCommand
 *
 * @author Josef Sochovsky
 * @version 1.0
 */
public class RegisterCommandTest {

    /**
     * Uebergibt dem RegisterCommand einen falschen String
     */
    @Test(expected = IllegalArgumentException.class)
    public void testStringConstructor() throws Exception {
        System.out.println("String-Constructor");
        RegisterCommand instance = new RegisterCommand("Wrong Text");
    }

    /**
     * User wird registriert und ueberprueft er in der Lister vorhanden
     */
    @Test
    public void testRegisterUserWithoutHobbies() throws Exception {
        System.out.println("execute");
        Server server = new Server("data/messenger.state");
        Calendar date = Calendar.getInstance();
        server.getClientList().put("test", new User(null, "test", MessengerHelper.hashPassword("test"), "test", "test", (Calendar) date, "test", "test", new CopyOnWriteArrayList<String>()));
        IClient icl = mock(IClient.class);
        RegisterCommand instance = new RegisterCommand("a a a a 01.07.1994 asd asd");
        server.doCommand(instance);
        assertNotNull(server.getClientList().get("a"));
    }

    /**
     * User wird registriert und ueberprueft er in der Lister vorhanden
     */
    @Test
    public void testRegisterUserWithHobbies() throws Exception {
        System.out.println("execute");
        Server server = new Server("data/messenger.state");
        Calendar date = Calendar.getInstance();
        server.getClientList().put("test", new User(null, "test", MessengerHelper.hashPassword("test"), "test", "test", (Calendar) date, "test", "test", new CopyOnWriteArrayList<String>()));
        IClient icl = mock(IClient.class);
        RegisterCommand instance = new RegisterCommand("a a a a 01.07.1994 asd asd asd,asd,asd,asd,asd,asd");
        server.doCommand(instance);
        assertNotNull(server.getClientList().get("a"));
    }
}
