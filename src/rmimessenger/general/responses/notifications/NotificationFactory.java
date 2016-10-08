package rmimessenger.general.responses.notifications;

import rmimessenger.general.FriendStatus;
import rmimessenger.general.Friendship;
import rmimessenger.general.responses.*;

/**
 * Dies ist die Implementierung der abstrakten ResponseFactory fuer die <br>
 * Responses. Sie stellt die Moeglichkeit eine bestimmte Art von Responses,
 * naemlich Notifications zu erzeugen.
 *
 * @author Himanshu Mongia
 * @version 1.0
 */
public class NotificationFactory implements AbstractResponseFactory {

    /**
     * Diese Metode emoeglicht die erzeugung einer Instanz einer Notification.
     *
     * @param param Alle Parameter, die zur Eruzeugung benoietigt werden.
     * @return Die erzeugte Notification
     */
    @Override
    public Response create(Object... param) {
        if ((param[0] instanceof String) && (param[1] instanceof String)) {
            String co = (String) param[0];
            String uname = (String) param[1];
            if (co.equalsIgnoreCase("FriendStatusNotification")) {
                return new FriendStatusNotification(uname, (FriendStatus) param[2]);
            } else if (co.equalsIgnoreCase("FriendshipNotification")) {
                return new FriendshipNotification(uname, (Friendship) param[2]);
            } else if (co.equalsIgnoreCase("MessageNotification")) {
                return new MessageNotification(uname, (String) param[2]);
            } else if (co.equalsIgnoreCase("FriendUnregisteredNotification")) {
                return new FriendUnregisteredNotification(uname);
            }
        }
        return null;
    }
}
