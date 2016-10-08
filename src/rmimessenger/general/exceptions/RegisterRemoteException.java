package rmimessenger.general.exceptions;

import java.rmi.RemoteException;

/**
 * Exception, die auftritt, wenn ein Fehler beim Registrieren.
 *
 * @author Gabriel Pawlowsky
 * @version 1.0
 */
public class RegisterRemoteException extends RemoteException {

    /**
     * Creates a new instance of
     * <code>RegisterRemoteException</code> without detail message.
     */
    public RegisterRemoteException() {
        super();
    }

    /**
     * Constructs an instance of
     * <code>RegisterRemoteException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public RegisterRemoteException(String msg) {
        super(msg);
    }
}
