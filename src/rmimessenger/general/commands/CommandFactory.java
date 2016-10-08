package rmimessenger.general.commands;

import rmimessenger.general.IClient;
import rmimessenger.general.User;

/**
 * Dies ist die Implementierung der abstrakten CommandFactory fuer die <br>
 * ClientCommands.
 *
 * @author Timon Hoebert
 * @author Gabriel Pawlowsky
 * @version 1.0
 */
public class CommandFactory implements AbstractCommandFactory {

    /**
     * Diese Metode emoeglicht die erzeugung einer Instanz eines ClientCommands.
     *
     * @param param Alle Parameter, die zur Eruzeugung benoietigt werden.
     * @return Das erzeugte ClientCommand
     */
    @Override
    public Command create(Object... param) {
        //Abfragen die das entsprechende erfragte Kommando wie gewuenscht erstellen

        if (param.length == 4 && param[0] instanceof String && param[0].equals("AcceptFriendCommand") && param[1] instanceof String && param[2] instanceof String && param[3] instanceof String) {
            return new AcceptFriendCommand((String) param[1], (String) param[2], (String) param[3]);
        } else if (param.length == 4 && param[0] instanceof String && param[0].equals("CancelFriendCommand") && param[1] instanceof String && param[2] instanceof String && param[3] instanceof String) {
            return new CancelFriendCommand((String) param[1], (String) param[2], (String) param[3]);
        } else if (param.length == 4 && param[0] instanceof String && param[0].equals("FindUserCommand") && param[1] instanceof String && param[2] instanceof String && param[3] instanceof String) {
            return new FindUserCommand((String) param[1], (String) param[2], (String) param[3]);
        } else if (param.length == 4 && param[0] instanceof String && param[0].equals("GetUserInfoCommand") && param[1] instanceof String && param[2] instanceof String && param[3] instanceof String) {
            return new GetUserInfoCommand((String) param[1], (String) param[2], (String) param[3]);
        } else if (param.length == 3 && param[0] instanceof String && param[0].equals("LogOutCommand") && param[1] instanceof String && param[2] instanceof String) {
            return new LogOutCommand((String) param[1], (String) param[2]);
        } else if (param.length == 4 && param[0] instanceof String && param[0].equals("LoginCommand") && param[1] instanceof IClient && param[2] instanceof String && param[3] instanceof String) {
            return new LoginCommand((IClient) param[1], (String) param[2], (String) param[3]);
        } else if (param.length == 2 && param[0] instanceof String && param[0].equals("RegisterCommand") && param[1] instanceof User) {
            return new RegisterCommand((User) param[1]);
        } else if (param.length == 4 && param[0] instanceof String && param[0].equals("RejectFriendCommand") && param[1] instanceof String && param[2] instanceof String && param[3] instanceof String) {
            return new RejectFriendCommand((String) param[1], (String) param[2], (String) param[3]);
        } else if (param.length == 4 && param[0] instanceof String && param[0].equals("RequestFriendCommand") && param[1] instanceof String && param[2] instanceof String && param[3] instanceof String) {
            return new RequestFriendCommand((String) param[1], (String) param[2], (String) param[3]);
        } else if (param.length == 5 && param[0] instanceof String && param[0].equals("SendMessageCommand") && param[1] instanceof String && param[2] instanceof String && param[3] instanceof String && param[4] instanceof String) {
            return new SendMessageCommand((String) param[1], (String) param[2], (String) param[3], (String) param[4]);
        } else if (param.length == 3 && param[0] instanceof String && param[0].equals("StopCommand") && param[1] instanceof String && param[2] instanceof String) {
            return new StopCommand((String) param[1], (String) param[2]);
        } else if (param.length == 3 && param[0] instanceof String && param[0].equals("UnregisterCommand") && param[1] instanceof String && param[2] instanceof String) {
            return new UnregisterCommand((String) param[1], (String) param[2]);
        }



        return null;

    }
}
