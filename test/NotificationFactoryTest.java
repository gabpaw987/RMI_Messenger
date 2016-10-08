

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rmimessenger.general.FriendStatus;
import rmimessenger.general.Friendship;
import rmimessenger.general.responses.Response;
import rmimessenger.general.responses.notifications.FriendStatusNotification;
import rmimessenger.general.responses.notifications.FriendUnregisteredNotification;
import rmimessenger.general.responses.notifications.FriendshipNotification;
import rmimessenger.general.responses.notifications.MessageNotification;
import rmimessenger.general.responses.notifications.NotificationFactory;

/**
 * Die NotificationFactoryTest-Klasse testet die NotificationFactory und somit
 * auch alle Notifications, die vorhanden sind.
 *
 * @author Himanshu Mongia
 */
public class NotificationFactoryTest {

    NotificationFactory instance;

    /**
     * Der Konstruktor erzeugt eine NotificationFactory
     */
    public NotificationFactoryTest() {
        instance = new NotificationFactory();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Dieser Metohde testet die FriendStatusNotification, in dem die
     * create-Methode der Factory mit den erwarteten Argumenten aufgerufen, und
     * dann der Typ des Rueckgabeobjekts mit dem erwarteten Typ verglichen wird.
     */
    @Test
    public void testCreateFriendStatusNotification() {
        System.out.println("create FriendStatusNotification");
        Response result = instance.create("FriendStatusNotification", "user", FriendStatus.ONLINE);
        assertEquals(FriendStatusNotification.class, result.getClass());
    }

    /**
     * Dieser Metohde testet die FriendshipNotification, in dem die
     * create-Methode der Factory mit den erwarteten Argumenten aufgerufen, und
     * dann der Typ des Rueckgabeobjekts mit dem erwarteten Typ verglichen wird.
     */
    @Test
    public void testCreateFriendshipNotification() {
        System.out.println("create FriendshipNotification");
        Response result = instance.create("FriendshipNotification", "user", Friendship.REQUESTED);
        assertEquals(FriendshipNotification.class, result.getClass());
    }

    /**
     * Dieser Metohde testet die MessageNotification, in dem die create-Methode
     * der Factory mit den erwarteten Argumenten aufgerufen, und dann der Typ
     * des Rueckgabeobjekts mit dem erwarteten Typ verglichen wird.
     */
    @Test
    public void testCreateMessageNotification() {
        System.out.println("create MessageNotification");
        Response result = instance.create("MessageNotification", "user", "message");
        assertEquals(MessageNotification.class, result.getClass());
    }

    /**
     * Dieser Metohde testet die FriendUnregisteredNotification, in dem die
     * create-Methode der Factory mit den erwarteten Argumenten aufgerufen, und
     * dann der Typ des Rueckgabeobjekts mit dem erwarteten Typ verglichen wird.
     */
    @Test
    public void testCreateFriendUnregisteredNotification() {
        System.out.println("create FriendUnregisteredNotification");
        Response result = instance.create("FriendUnregisteredNotification", "user");
        assertEquals(FriendUnregisteredNotification.class, result.getClass());
    }
}
