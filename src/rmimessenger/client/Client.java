package rmimessenger.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmimessenger.general.IClient;
import rmimessenger.general.IServer;
import rmimessenger.general.MessengerProperties;
import rmimessenger.general.commands.Command;
import rmimessenger.general.commands.CommandFactory;
import rmimessenger.general.commands.LogOutCommand;
import rmimessenger.general.commands.LoginCommand;
import rmimessenger.general.commands.StopCommand;
import rmimessenger.general.commands.TextCommandFactory;
import rmimessenger.general.commands.UnregisterCommand;
import rmimessenger.general.responses.Response;

/**
 * Diese Klasse implementiert das Remote Interface IClient.
 *
 * @author Himanshu Mongia
 */
public class Client extends UnicastRemoteObject implements IClient {

    private String currentUser;
    private String currentUserPwHash;
    private IServer server;
    private TextCommandFactory textFactory;
    private CommandFactory commandFactory;
    private MessengerProperties properties;
    private String registryURL;

    /**
     * Der Client-Konstruktor intialisiert die TextCommandFactory,
     * CommandFactory und die MessengerProperties-Klasse.
     *
     * @throws RemoteException
     */
    public Client() throws RemoteException {
        super();
        textFactory = new TextCommandFactory();
        commandFactory = new CommandFactory();
        properties = new MessengerProperties();
    }

    /**
     * Diese Methode liest die Properties aus dem File hinaus, und versucht sich
     * mit den jeweiligen Parametern zum Server zu verbinden.
     *
     * @return Erfolg oder nicht
     * @throws IOException
     * @throws NotBoundException
     * @throws NotBoundException
     * {@link #connect(String host, int port, String name)}
     */
    public boolean start() throws IOException, NotBoundException {
        if (properties.readPropertiesFromFile()) {
            return connect(properties.getHost(), properties.getPort(), properties.getName());
        }
        return false;
    }

    /**
     * Diese Methode unexportiert das Callback Objekt und somit kann der Client
     * beendet werden.
     */
    public void stop() {
        System.out.println("Stopping.");
        try {
            //Naming.unbind(registryURL);
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            System.err.println("An error occured while unexporting the Callback.");
        }
    }

    /**
     * Diese Methode verbindet sich zum Server-Objekt durch die Registry und
     * setzt die noetigen SecurityManager und Zugriffsrechte dafuer.
     *
     * @param host Hostname
     * @param port Portnummer
     * @param name Objekt/Servicename
     * @return Erfolg oder nicht
     * @throws NotBoundException wird geworfen wenn der gewuenschte Service-Name
     * nicht in der Registry zu finden ist
     * @throws MalformedURLException
     * @throws RemoteException
     */
    private boolean connect(String host, int port, String name) throws NotBoundException, MalformedURLException, RemoteException {
        registryURL = "rmi://" + host + ":" + port + "/" + name;
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "file:java.policy");
            System.setSecurityManager(new RMISecurityManager());
        }
        // find the remote object and cast it to an interface object
        System.out.println("Looking up " + registryURL);
        server = (IServer) Naming.lookup(registryURL);
        return true;
    }

    /**
     * Diese Methode kann vom ConsoleClient zur Uebertragung der eingegebenen
     * Commands verwendet werden. In der Methode wird einfach nur die Eingabe an
     * die TextFactory weiter geleitet, und auf spezielle Commands wie Login und
     * Stop wird passend reagiert. Anschliessend wird eine Response verarbeitet.
     *
     * @param s Die Eingabe als String
     * @return Erfolg oder nicht
     * @throws RemoteException
     */
    public boolean command(String s) throws RemoteException {
        Command c = textFactory.create(this, s);
        if (c != null) {
            if (c instanceof LoginCommand) {
                LoginCommand lc = (LoginCommand) c;
                currentUser = lc.getUname();
                currentUserPwHash = lc.getPwHash();
            } else if (c instanceof LogOutCommand || c instanceof UnregisterCommand) {
                this.currentUser = null;
                this.currentUserPwHash = null;
            }
            try {
                doResponse(server.doCommand(c));
            } catch (ConnectException ex) {
                if (!(c instanceof StopCommand)) {
                    throw ex;
                }
            }
            if (c instanceof StopCommand) {
                return false;
            }
        }
        return true;
    }

    /**
     * Diese Methode ist dafuer zustaendig damit passende Ausgaben fuer die zum
     * Server geschickten Commands am Client ausgegeben werden koennen.
     *
     * @param r Response
     * @return null
     * @throws RemoteException
     */
    @Override
    public Command doResponse(Response r) throws RemoteException {
        if (r != null) {
            return r.execute(this);
        } else {
            return null;
        }
    }

    @Override
    public TextCommandFactory getTextFactory() {
        return textFactory;
    }

    @Override
    public CommandFactory getCommandFactory() {
        return commandFactory;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getCurrentUserPwHash() {
        return currentUserPwHash;
    }
}