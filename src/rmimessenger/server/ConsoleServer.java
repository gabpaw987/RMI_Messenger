package rmimessenger.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import rmimessenger.general.MessengerProperties;

/**
 * Diese Klasse implementiert die Konsolenschnittstelle zum Server.
 *
 * @author Himanshu Mongia
 */
public class ConsoleServer {

    /**
     * Die main-Methode ruft den Properties-Reader auf, und startet mit den
     * jeweiligen Werten den Server.
     *
     * @param args Konsolenargumente
     */
    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "file:java.policy");
            System.setSecurityManager(new RMISecurityManager());
        }
        MessengerProperties mp = new MessengerProperties();
        try {
            if (mp.readPropertiesFromFile()) {
                Server s = new Server(mp.getFilename());
                s.start(mp.getHost(), mp.getPort(), mp.getName());
                // registry.bind(mp.getName(), s); its kind of the same
                System.out.println("Server up. Hit enter to exit.");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String line = "line";
                while (line != null && line.length() != 0) {
                    if (!line.equals("line")) {
                        System.out.println("Hit ENTER to exit.");
                    }
                    line = br.readLine();
                }
                s.stop();
            }
        } catch (NotBoundException ex) {
            System.err.println("The server remote object could not be found in the registry.");
        } catch (AccessException ex) {
            System.err.println("Bind denied!");
        } catch (RemoteException ex) {
            System.err.println("Remote Error " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("I/O-Error " + ex.getMessage());
        }
    }
}