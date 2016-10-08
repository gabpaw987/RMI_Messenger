package rmimessenger.general;

import java.rmi.Remote;
import java.util.concurrent.ConcurrentHashMap;
import rmimessenger.general.commands.Command;
import rmimessenger.general.responses.Response;
import rmimessenger.general.responses.ResponseFactory;
import rmimessenger.general.responses.notifications.NotificationFactory;

/**
 * This is a remote interface for illustrating RMI client callback.
 *
 * @author Timon Hoebert
 */
public interface IServer extends Remote {

    /**
     * Executes the given Command
     *
     * @param c the executed Command
     * @return the Response
     * @throws java.rmi.RemoteException
     */
    public Response doCommand(Command c) throws java.rmi.RemoteException;

    public ConcurrentHashMap<String, User> getClientList() throws java.rmi.RemoteException;

    public ResponseFactory getResponseFactory() throws java.rmi.RemoteException;

    public NotificationFactory getNotificationFactory() throws java.rmi.RemoteException;
}
