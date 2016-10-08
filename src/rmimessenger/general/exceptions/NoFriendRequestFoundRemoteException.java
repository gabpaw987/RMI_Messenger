package rmimessenger.general.exceptions;

import java.rmi.RemoteException;

/**
 * Exception if the friendrequest ist not found
 *
 * @author Josef Sochovsky
 */
public class NoFriendRequestFoundRemoteException extends RemoteException {

    /**
     * Creates a new instance of
     * <code>NoFriendRequestFoundException</code> without detail message.
     */
    public NoFriendRequestFoundRemoteException() {
        super();
    }

    /**
     * Constructs an instance of
     * <code>NoFriendRequestFoundException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public NoFriendRequestFoundRemoteException(String msg) {
        super(msg);
    }
}
