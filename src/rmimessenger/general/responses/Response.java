package rmimessenger.general.responses;

import java.io.Serializable;
import rmimessenger.general.IClient;
import rmimessenger.general.commands.Command;

/**
 * Das Command wird vom Server zum Client gesendet und dort via execute
 * ausgefuehrt.
 *
 * @author Timon Hoebert
 */
public interface Response extends Serializable {

    /**
     * Fuehrt das Command am Server aus.
     *
     * @param server die Serverreferenz vom Aufrufer
     * @return die Antwort als Response
     * @throws RemoteException eine Remoteexception
     */
    /**
     * Fuehrt das Command am Client aus.
     *
     * @param client der aufrufende Client
     * @return die Antwort als Response
     */
    public Command execute(IClient client);
}