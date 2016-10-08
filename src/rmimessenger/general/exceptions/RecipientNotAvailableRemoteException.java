package rmimessenger.general.exceptions;

import java.rmi.RemoteException;

/**
 * Exception if the Recipient is not available
 *
 * @author Josef Sochovsky
 */
public class RecipientNotAvailableRemoteException extends RemoteException {

    /**
     * Creates a new instance of
     * <code>RecipientNotAvailableRemoteException</code> without detail message.
     */
    public RecipientNotAvailableRemoteException() {
        super();
    }

    /**
     * Constructs an instance of
     * <code>RecipientNotAvailableRemoteException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public RecipientNotAvailableRemoteException(String msg) {
        super(msg);
    }
}
