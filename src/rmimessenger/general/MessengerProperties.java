package rmimessenger.general;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * MessengerProperties liest diverse Einstellungen aus einem Properties-File ein
 * und stellt diese dem Client und dem Server zur Verfuegung.
 *
 * @author Himanshu Mongia
 */
public class MessengerProperties {

    private static final String PROPERTIESFILE = "messenger.properties";
    private static final String REGISTRYHOST = "registry.host";
    private static final String REGISTRYOBJECTNAME = "registry.object.name";
    private static final String REGISTRYPORT = "registry.port";
    private static final String SERVERSTOREFILE = "server.store.file";
    private String host, name, filename;
    private int port;

    /**
     * In dieser Methode werden die einzelnen Einstellungen aus der Datei
     * gelesen und als Attribute gespeichert.
     */
    public boolean readPropertiesFromFile() throws IOException {
        InputStream is = ClassLoader.getSystemResourceAsStream(PROPERTIESFILE);
        if (is != null) {
            Properties props = new Properties();
            try {
                props.load(is);
                this.host = props.getProperty(REGISTRYHOST);
                this.name = props.getProperty(REGISTRYOBJECTNAME);
                this.port = Integer.parseInt(props.getProperty(REGISTRYPORT));
                this.filename = props.getProperty(SERVERSTOREFILE);
                return true;
            } catch (IOException ex) {
                System.err.println("An error occured while reading the"
                        + " properties file!");
            } finally {
                is.close();
            }
        } else {
            System.err.println("The properties file could not be found!");
        }
        return false;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }
}