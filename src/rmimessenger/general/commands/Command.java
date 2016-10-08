package rmimessenger.general.commands;

import java.io.Serializable;
import java.rmi.RemoteException;
import rmimessenger.general.IServer;
import rmimessenger.general.responses.Response;

/**
 * Das Command wird vom Client zum Server gesendet und dort via execute
 * ausgefuehrt.
 *
 * @author Timon Hoebert
 */
public abstract class Command implements Serializable {

    /**
     * Fuehrt das Command am Server aus.
     *
     * @param server die Serverreferenz vom Aufrufer
     * @return die Antwort als Response
     * @throws RemoteException eine Remoteexception
     */
    public abstract Response execute(IServer server) throws RemoteException;
}
