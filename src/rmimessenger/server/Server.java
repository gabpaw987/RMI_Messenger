package rmimessenger.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;
import rmimessenger.general.IServer;
import rmimessenger.general.User;
import rmimessenger.general.commands.Command;
import rmimessenger.general.responses.Response;
import rmimessenger.general.responses.ResponseFactory;
import rmimessenger.general.responses.notifications.NotificationFactory;

/**
 * Diese Klasse stellt das Server-Objekt da, welches dann Remote verfuegbar sein
 * soll.
 *
 * @author Himanshu Mongia
 */
public class Server extends UnicastRemoteObject implements IServer {

    private ConcurrentHashMap<String, User> clientList;
    private ResponseFactory responseFactory;
    private NotificationFactory notificationFactory;
    private String host, name, dumpfile;
    private int port;

    /**
     * Der Server liest den vorherigen Status ein, um den letzten Zustand
     * wiederherzustellen. Wenn noetig wird eine ClientList initialisier und
     * danach werden Response und NotificationFactories intialisiert.
     *
     * @param dumpfile Das Zustandsfile
     * @throws RemoteException
     */
    public Server(String dumpfile) throws RemoteException {
        super();
        this.dumpfile = dumpfile;
        clientList = StateDumper.loadData(dumpfile);
        if (clientList == null) {
            clientList = new ConcurrentHashMap();
        } else {
            System.out.println(clientList.size() + " Users loaded");
        }
        responseFactory = new ResponseFactory();
        notificationFactory = new NotificationFactory();
    }

    /**
     * Diese Methode erzeugt eine Registry, wenn nicht vorhanden, und verbindet
     * sich mit den jeweiligen Parametern zur Registry.
     *
     *
     * @param host Hostname
     * @param port Portnummer
     * @param name Servicename
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public void start(String host, int port, String name) throws MalformedURLException, RemoteException {
        this.host = host;
        this.port = port;
        this.name = name;
        String registryURL = "rmi://" + host + ":" + port + "/" + name;
        System.out.println("Starting Server @" + registryURL);
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(port);
            // Dieser Aufruft wirft eine Exception, falls die Registry nicht 
            // bereits existiert
            registry.list();
        } catch (RemoteException ex) {
            // Keine Registry am Port vorhanden:
            registry = LocateRegistry.createRegistry(port);
        }
        Naming.rebind(registryURL, this);
    }

    /**
     * Diese Methode speichert den Status ab, beendet die Verbindung zur
     * registry und somit kann der Server beendet werden.
     *
     * @throws NoSuchObjectException
     * @throws RemoteException
     * @throws NotBoundException wenn das abzumeldende Objekt nicht in der
     * registry gefunden werden kann
     * @throws MalformedURLException
     */
    public void stop() throws NoSuchObjectException, RemoteException, NotBoundException, MalformedURLException {
        for (User u : clientList.values()) u.setCallBack(null);
        StateDumper.dumpData(clientList, dumpfile);
        String registryURL = "rmi://" + host + ":" + port + "/" + name;
        Naming.unbind(registryURL);
        UnicastRemoteObject.unexportObject(this, true);
    }

    /**
     * Diese Methode ist dafuer zustaendig die erhaltenene Commands vom Client
     * zu verarbeiten.
     *
     * @param c Command
     * @return eine Antwort (Response)
     * @throws RemoteException
     */
    @Override
    public Response doCommand(Command c) throws RemoteException {
        return c.execute(this);
    }

    /**
     * @return the clientList
     */
    @Override
    public ConcurrentHashMap<String, User> getClientList() throws RemoteException {
        return clientList;
    }

    @Override
    public ResponseFactory getResponseFactory() {
        return responseFactory;
    }

    @Override
    public NotificationFactory getNotificationFactory() {
        return notificationFactory;
    }
}
