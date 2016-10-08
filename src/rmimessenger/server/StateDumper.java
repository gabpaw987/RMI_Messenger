package rmimessenger.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import rmimessenger.general.User;

/**
 * Der StateDumper kann die Userdaten in Form einer ConcurrentHashMap in ein
 * File abspeichern und wiederherstellen.
 *
 * @author Timon Hoebert
 */
public class StateDumper {

    /**
     * Speichert die uebergebene Userdaten in das uebergebene File. Falls nicht
     * existente Unterordner angegeben werden, werden diese erstellt.
     *
     * @param clientList die Userdaten in Form einer ConcurrentHashMap
     * @param path der Pfad zum File in welches die Userdaten gespeichert werden
     * sollen.
     */
    public static void dumpData(ConcurrentHashMap<String, User> clientList, String path) {
        FileSystem fs = FileSystems.getDefault();
        Path from = fs.getPath(path);

        Path folder = from.getParent();
        if (folder != null) {
            try {
                Files.createDirectories(folder);
            } catch (IOException ex) {
                System.out.println("Error while Creating Folder " + folder + ": " + ex.getMessage());
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(from.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(clientList);
        } catch (FileNotFoundException ex) {
            System.out.println(from + " not found.");
        } catch (IOException ex) {
            System.out.println("Error while writing File " + from + ": " + ex.getMessage());
        }
    }

    /**
     * Liesst die Userdaten aus dem uebergebenen File und liefert diese zurueck.
     *
     * @param path der Pfad zum File aus welchem die Userdaten gelesen werden
     * sollen.
     * @return die Userdaten in Form einer ConcurrentHashMap
     */
    public static ConcurrentHashMap<String, User> loadData(String path) {
        FileSystem fs = FileSystems.getDefault();
        Path from = fs.getPath(path);
        try {
            FileInputStream fis = new FileInputStream(from.toFile());
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            if (o instanceof ConcurrentHashMap) {
                return (ConcurrentHashMap<String, User>) o;
            }
        } catch (FileNotFoundException ex) {
            System.out.println(from + " not found.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error while Casting Class to ConcurrentHashMap: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error while reading File " + from + ": " + ex.getMessage());
        }
        return null;
    }
}
