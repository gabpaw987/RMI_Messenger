package rmimessenger.general;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

/**
 * Der MessengerHelper hat den simplen Zweck aus dem Userpasswort einen Hash zu
 * </br> gererieren
 *
 * @author Josef Sochovsky
 * @version 1.0
 */
public class MessengerHelper {

    public static final SimpleDateFormat DATEFORMAT = new java.text.SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat TIMEFORMAT_SHORT = new java.text.SimpleDateFormat("dd.MM.yyyy @ hh:mm");
    public static final SimpleDateFormat TIMEFORMAT_LONG = new java.text.SimpleDateFormat("dd.MM.yyyy @ hh:mm:ss");

    /**
     * Die Methode generiert aus der String-Eingabe einen Hash und konvertiert
     * </br> einen String aus dem Byte-Array
     *
     * @param s der zu verschluesselnde Klartext
     * @return der Hashwert der Eingabe
     */
    public static String hashPassword(String s) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("No such Algorithm " + ex.getMessage());
        }
        return new String(md.digest(s.getBytes()));
    }
}
