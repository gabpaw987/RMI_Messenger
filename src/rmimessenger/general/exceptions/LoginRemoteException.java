package rmimessenger.general.exceptions;

import java.rmi.RemoteException;

/**
 * Exception if a user is not logged in.
 *
 * @author Josef Sochovsky
 */
public class LoginRemoteException extends RemoteException {

    /**
     * Creates a new instance of
     * <code>LoginRemoteException</code> without detail message.
     */
    public LoginRemoteException() {
        super();
    }

    /**
     * Constructs an instance of
     * <code>LoginRemoteException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public LoginRemoteException(String msg) {
        super(msg);
    }
}
