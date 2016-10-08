package rmimessenger.general.commands;

import rmimessenger.client.Client;

/**
 * TextCommandFactory erweitert die AbstractCommandFactory, und ermoeglicht das
 * Erzeugen von Commands welche ueber die Console eingegeben werden.
 *
 * @author Himanshu Mongia
 */
public class TextCommandFactory implements AbstractCommandFactory {

    /**
     * die create-Methode liest die uebergebenen Argumente ein, verarbeitet
     * diese und erstellt die jeweiligen Commands dazu. Diese werden
     * anschliessend zurueckgegeben.
     *
     * @param param Argumente
     * @return das erstellte Command
     */
    @Override
    public Command create(Object... param) {
        if (param.length == 2 && param[0] instanceof Client && param[1] instanceof String) {
            Client client = (Client) param[0];
            String un = client.getCurrentUser();
            String pw = client.getCurrentUserPwHash();

            String message = (String) param[1];
            String co = null;
            String pa = null;
            int index = message.indexOf(" ");
            if (index != -1) {
                co = message.substring(0, index);
                pa = message.substring(index + 1);
            } else {
                co = message;
            }

            if (co.equalsIgnoreCase("login")) {
                return new LoginCommand(client, pa);
            } else if (co.equalsIgnoreCase("findUser")) {
                return new FindUserCommand(un, pw, pa);
            } else if (co.equalsIgnoreCase("register")) {
                return new RegisterCommand(pa);
            } else if (co.equalsIgnoreCase("stop")) {
                return new StopCommand(un, pw);
            } else if (co.equalsIgnoreCase("unregister")) {
                return new UnregisterCommand(un, pw);
            } else if (co.equalsIgnoreCase("logout")) {
                return new LogOutCommand(un, pw);
            } else if (co.equalsIgnoreCase("getUserInfo")) {
                return new GetUserInfoCommand(un, pw, pa);
            } else if (co.equalsIgnoreCase("requestFriend")) {
                return new RequestFriendCommand(un, pw, pa);
            } else if (co.equalsIgnoreCase("acceptFriend")) {
                return new AcceptFriendCommand(un, pw, pa);
            } else if (co.equalsIgnoreCase("rejectFriend")) {
                return new RejectFriendCommand(un, pw, pa);
            } else if (co.equalsIgnoreCase("cancelFriend")) {
                return new CancelFriendCommand(un, pw, pa);
            } else if (co.equalsIgnoreCase("sendMsg")) {
                return new SendMessageCommand(un, pw, pa);
            }

        }
        throw new IllegalArgumentException("Invalid Command");
    }
}
