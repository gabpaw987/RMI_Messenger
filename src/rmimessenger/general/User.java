package rmimessenger.general;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import rmimessenger.general.responses.notifications.FriendStatusNotification;
import rmimessenger.general.responses.notifications.FriendshipNotification;
import rmimessenger.general.responses.notifications.MessageNotification;
import rmimessenger.general.responses.notifications.Notification;

/**
 * Die Userklasse repraesentiert einen Benutzer mit seinen Daten im System.
 * Dabei besteht dieser nicht nur aus einfachen Attributen sondern auch aus
 * Referenzen zu anderen Usern.
 *
 * @author Timon Hoebert
 * @author Gabriel Pawlowsky
 */
public class User implements Serializable {

    private String uname;
    private String pwHash;
    private IClient callBack;
    private Calendar lastLogin;
    private CopyOnWriteArrayList<Notification> notificationQueue;
    private ConcurrentHashMap<String, User> friends;
    private CopyOnWriteArrayList<User> outgoingRequests;
    private CopyOnWriteArrayList<User> incomingRequests;
    private String firstName;
    private String lastName;
    private Calendar birthday;
    private String profession;
    private String email;
    private CopyOnWriteArrayList<String> hobbies;

    /**
     * Erstellt einen User mit den notwendigsten Attributen.
     *
     * @param callBack das Callback
     * @param uname der Username
     * @param pwHash der Passworthash
     * @param firstName der Vorname
     * @param lastName der Nachname
     * @param birthday der Geburtstag
     * @param profession der Beruf
     * @param email die Emailadresse
     * @param hobbies die Hobbies als List
     */
    public User(IClient callBack, String uname, String pwHash, String firstName, String lastName, Calendar birthday, String profession, String email, CopyOnWriteArrayList<String> hobbies) {
        this.callBack = callBack;
        this.uname = uname;
        this.pwHash = pwHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.profession = profession;
        this.email = email;
        this.hobbies = hobbies;

        this.notificationQueue = new CopyOnWriteArrayList<Notification>();
        this.friends = new ConcurrentHashMap<String, User>();
        this.outgoingRequests = new CopyOnWriteArrayList<User>();
        this.incomingRequests = new CopyOnWriteArrayList<User>();
    }

    /**
     * Benachrichtigt einen Freund falls dieser Online ist, andernfalls
     * hinterlaesst er ihm die Nachricht in dessen Notificationqueue
     *
     * @param n die zu sendende Notification
     */
    public void notify(Notification n) {
        if (callBack != null) {
            try {
                callBack.doResponse(n);
            } catch (RemoteException ex) {
                callBack = null;
                notifyFriends(new FriendStatusNotification(uname, FriendStatus.OFFLINE));
                if (!(n instanceof FriendStatusNotification)) {
                    notificationQueue.add(n);
                }
            }

        } else {
            if (!(n instanceof FriendStatusNotification)) {
                notificationQueue.add(n);
            }
        }
    }

    /**
     * Benachrichtigt alle Freunde
     *
     * @param n die zu sendende Notification
     */
    public void notifyFriends(Notification n) {
        for (User friend : getFriends().values()) {
            friend.notify(n);
        }
    }

    /**
     * @return the uname
     */
    public String getUname() {
        return uname;
    }

    /**
     * @param uname the uname to set
     */
    public void setUname(String uname) {
        this.uname = uname;
    }

    /**
     * @return the pwHash
     */
    public String getPwHash() {
        return pwHash;
    }

    /**
     * @param pwHash the pwHash to set
     */
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    /**
     * @return the lastLogin
     */
    public Calendar getLastLogin() {
        return lastLogin;
    }

    /**
     * @param lastLogin the lastLogin to set
     */
    public void setLastLogin(Calendar lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * @return the frieds
     */
    public ConcurrentHashMap<String, User> getFriends() {
        return friends;
    }

    /**
     * @param frieds the frieds to set
     */
    public void setFriends(ConcurrentHashMap<String, User> friends) {
        this.friends = friends;
    }

    /**
     * @return the outgoingRequests
     */
    public CopyOnWriteArrayList<User> getOutgoingRequests() {
        return outgoingRequests;
    }

    /**
     * @param outgoingRequests the outgoingRequests to set
     */
    public void setOutgoingRequests(CopyOnWriteArrayList<User> outgoingRequests) {
        this.outgoingRequests = outgoingRequests;
    }

    /**
     * @return the incomingRequests
     */
    public CopyOnWriteArrayList<User> getIncomingRequests() {
        return incomingRequests;
    }

    /**
     * @param incomingRequests the incomingRequests to set
     */
    public void setIncomingRequests(CopyOnWriteArrayList<User> incomingRequests) {
        this.incomingRequests = incomingRequests;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the birthday
     */
    public Calendar getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the profession
     */
    public String getProfession() {
        return profession;
    }

    /**
     * @param profession the profession to set
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the hobbies
     */
    public CopyOnWriteArrayList<String> getHobbies() {
        return hobbies;
    }

    /**
     * @param hobbies the hobbies to set
     */
    public void setHobbies(CopyOnWriteArrayList<String> hobbies) {
        this.hobbies = hobbies;
    }

    /**
     * @return the notificationQueue
     */
    public CopyOnWriteArrayList<Notification> getNotificationQueue() {
        return notificationQueue;
    }

    /**
     * @param notificationQueue the notificationQueue to set
     */
    public void setNotificationQueue(CopyOnWriteArrayList<Notification> notificationQueue) {
        this.notificationQueue = notificationQueue;
    }

    public IClient getCallBack() {
        return callBack;
    }

    public void setCallBack(IClient callBack) {
        this.callBack = callBack;
    }

    /**
     * Gibt die Userdaten schoen formatiert als String zurueck.
     *
     * @param onlyPublicInfo gibt an ob auch private Informationen gezeigt
     * werden sollen
     * @return die Userdaten als String
     */
    public String getFormattedUserInfo(boolean onlyPublicInfo) {
        String userInfo = "";
        //Allgemeine Daten
        if (!onlyPublicInfo) {
            userInfo = "Your Data:\n";
        }
        userInfo += "\tName: " + this.getFirstName() + " " + this.getLastName() + "\n";
        userInfo += "\tBirthday: " + (MessengerHelper.DATEFORMAT.format(this.getBirthday().getTime())) + "\n";
        userInfo += "\tProfession: " + this.getProfession() + "\n";
        userInfo += "\tHobbies:\n";
        if (this.getHobbies().size() > 0) {
            for (String hobby : this.getHobbies()) {
                userInfo += "\t\t" + hobby + "\n";
            }
        }
        //Daten ueber Freunde
        if (this.getFriends().size() > 0) {
            userInfo += "Friends:\n";
            for (User friend : this.getFriends().values()) {
                userInfo += "\t\t" + friend.getUname() + " ";
                if (!onlyPublicInfo) {
                    if (friend.getCallBack() != null) {
                        userInfo += "online!";
                    } else {
                        userInfo += "offline (last online: ";
                        if (friend.getLastLogin() != null) {
                            userInfo += (MessengerHelper.TIMEFORMAT_SHORT.format(friend.getLastLogin().getTime()));
                        } else {
                            userInfo += "-";
                        }
                        userInfo += ")";
                    }
                }
                userInfo += "\n";
            }
        }
        if (!onlyPublicInfo) {
            //Freundschaftsanfragen
            if (this.getOutgoingRequests().size() > 0) {
                userInfo += "\tOutgoing friendships requests:\n";
                for (User outFriendRequest : this.getOutgoingRequests()) {
                    userInfo += "\t\t" + outFriendRequest.getUname() + " since ";
                    userInfo += (MessengerHelper.TIMEFORMAT_SHORT.format(outFriendRequest.getLastLogin().getTime()));
                    userInfo += "\n";
                }
            }
            if (this.getIncomingRequests().size() > 0) {
                userInfo += "\tIncoming friendships requests:\n";
                for (User inFriendRequest : this.getIncomingRequests()) {
                    userInfo += "\t\t" + inFriendRequest.getUname() + " since ";
                    userInfo += (MessengerHelper.TIMEFORMAT_SHORT.format(inFriendRequest.getLastLogin().getTime()));
                    userInfo += "\n";
                }
            }
        } else if (onlyPublicInfo) {
            //Status, falls oeffentliche Informationen
            userInfo += "State: ";
            if (this.getCallBack() != null) {
                userInfo += "online!";
            } else {
                userInfo += "offline, last online on: ";
                if (this.getLastLogin() != null) {
                    userInfo += (MessengerHelper.TIMEFORMAT_SHORT.format(this.getLastLogin().getTime()));
                } else {
                    userInfo += "-";
                }
            }
        }

        return userInfo;
    }

    /**
     * Gibt daten ueber die Events aus, die bei einem User noch in der
     * Warteschlange sind.
     *
     * @return die Events als String zusammengefasst
     */
    public String getFormattedEvents() {
        String userInfo = "";
        if (this.getNotificationQueue().size() > 0) {
            userInfo = "Events since last logout:\n";
            for (Notification event : this.getNotificationQueue()) {
                userInfo += "\t" + (MessengerHelper.TIMEFORMAT_LONG.format(event.getTime().getTime())) + "|";
                userInfo += event.getUser();
                if (event instanceof FriendshipNotification) {
                    if (((FriendshipNotification) event).getS().equals(Friendship.REQUESTED)) {
                        userInfo += " requested friendship.";
                    } else if (((FriendshipNotification) event).getS().equals(Friendship.CANCELLED)) {
                        userInfo += " cancelled friendship";
                    } else if (((FriendshipNotification) event).getS().equals(Friendship.ACCEPTED)) {
                        userInfo += " accepted friendship";
                    } else if (((FriendshipNotification) event).getS().equals(Friendship.REJECTED)) {
                        userInfo += " rejected friendship";
                    }
                } else if (event instanceof MessageNotification) {
                    userInfo += ": " + ((MessageNotification) event).getMessage() + "\n";
                }
                userInfo += "\n";
            }
        }
        this.getNotificationQueue().clear();
        return userInfo;
    }
}
