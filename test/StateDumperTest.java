

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import rmimessenger.general.User;
import rmimessenger.server.StateDumper;

/**
 * Der StateDumperTest testet die Funktionalitaet des serialisierten
 * Abspeicherns der Userdaten in ein File, sowie dessen Wiederherstellung.
 *
 * @author Timon Hoebert
 */
public class StateDumperTest {

    private ConcurrentHashMap<String, User> clientList;
    private User user;

    @Before
    public void setUp() {
        user = new User(null, "UNAME", "PW", "first", "last", Calendar.getInstance(), "profession", "email@example.com", null);
        clientList = new ConcurrentHashMap<>();
        clientList.put(user.getUname(), user);
    }

    /**
     * Testet das Abspeichern und Wiederherstellen mit einem einfache Filepfad.
     */
    @Test
    public void testSimpleDump() {
        String filename = "messenger.state";
        System.out.println("testSimpleDump @ " + filename);
        StateDumper.dumpData(clientList, filename);

        ConcurrentHashMap<String, User> result = StateDumper.loadData(filename);
        assertEquals(clientList.get(user.getUname()).getUname(), result.get(user.getUname()).getUname());

        File f = new File(filename);
        f.delete();
    }

    /**
     * Testet das Abspeichern und Wiederherstellen mit einem komplexen Filepfad
     * inklusive Unterordner.
     */
    @Test
    public void testKomplexPathDump() {

        String filename = "data/messenger.state";
        System.out.println("testKomplexPathDump @ " + filename);
        StateDumper.dumpData(clientList, filename);

        ConcurrentHashMap<String, User> result = StateDumper.loadData(filename);
        assertEquals(clientList.get(user.getUname()).getUname(), result.get(user.getUname()).getUname());

        File f = new File(filename);
        f.delete();
    }
}
