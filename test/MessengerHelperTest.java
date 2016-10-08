

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import rmimessenger.general.MessengerHelper;

/**
 * Testklasse fuer den MessengerHelper
 *
 * @author Josef Sochovsky
 * @version 1.0
 */
public class MessengerHelperTest {

    /**
     * Diese Testmethode hasht "test" selber und vergleicht das Ergebnis vom
     * </br> MessengerHelper mit einem eigens angefertigten Hash</br>
     */
    @Test
    public void testHashPassword() {
        System.out.println("hashPassword");
        String s = "test";
        String expResult = null;
        try {
            expResult = new String(MessageDigest.getInstance("SHA-1").digest(s.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            fail();
        }
        String result = MessengerHelper.hashPassword(s);
        assertEquals(expResult, result);
    }
}
