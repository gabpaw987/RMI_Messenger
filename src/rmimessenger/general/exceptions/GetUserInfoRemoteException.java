package rmimessenger.general.exceptions;

import java.rmi.RemoteException;

/**
 * Exception, die auftritt, wenn ein Fehler im GetUserInfoCommand passiert.
 *
 * @author Gabriel Pawlowsky
 * @version 1.0
 */
public class GetUserInfoRemoteException extends RemoteException {

    /**
     * Creates a new instance of
     * <code>LoginRemoteException</code> without detail message.
     */
    public GetUserInfoRemoteException() {
        super();
    }

    /**
     * Constructs an instance of
     * <code>LoginRemoteException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public GetUserInfoRemoteException(String msg) {
        super(msg);
    }
}
