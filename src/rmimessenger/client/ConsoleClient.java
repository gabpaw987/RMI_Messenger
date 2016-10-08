package rmimessenger.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import rmimessenger.general.MessengerProperties;

/**
 * Diese Klasse implementiert die Konsolenschnittstelle zum Client.
 *
 * @author Himanshu Mongia
 */
public class ConsoleClient {

    /**
     * Die main-Methode ruft den Properties-Reader auf, und startet mit den
     * jeweiligen Werten den Client.
     *
     * @param args Konsolenargumente
     */
    public static void main(String args[]) {
        MessengerProperties mp = new MessengerProperties();
        Client c = null;
        try {
            if (mp.readPropertiesFromFile()) {
                c = new Client();
                boolean run;
                run = c.start();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (run) {
                    try {
                        System.out.println();
                        System.out.println("Please enter a command:");
                        run = c.command(br.readLine());
                    } catch (IllegalArgumentException ex) {
                        System.err.println(ex.getMessage());
                    } catch (Exception ex) {
                        if (ex.getCause() == null) {
                            System.err.println(ex.getMessage());
                        } else {
                            System.err.println(ex.getCause().getMessage());
                        }
                    }
                }
            }
        } catch (NotBoundException ex) {
            System.err.println("The required service could not be obtained from the registry.");
        } catch (IOException ex) {
            if (ex.getCause() == null) {
                System.err.println("Connection Error " + ex.getMessage());
            } else {
                System.err.println("Connection Error " + ex.getCause().getMessage());
            }

        } finally {
            c.stop();
        }
    }
}