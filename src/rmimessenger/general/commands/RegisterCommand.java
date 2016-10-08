package rmimessenger.general.commands;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;
import rmimessenger.general.IServer;
import rmimessenger.general.MessengerHelper;
import rmimessenger.general.User;
import rmimessenger.general.exceptions.RegisterRemoteException;
import rmimessenger.general.responses.Response;

/**
 * Das RegisterCommand ist dafuer gedacht einen neuen User zu registrieren.
 *
 * @author Josef Sochovsky
 * @version 1.0
 */
public class RegisterCommand extends Command {

    // Der User der neu erstellt werden soll
    private User user;

    /**
     * Default-Konstruktor
     *
     * @param user der erzeugt werden soll
     */
    public RegisterCommand(User user) {
        this.user = user;
    }

    /**
     * Konstruktor mit einem String input
     *
     * @param input "<userName> <password> <firstName> <lastName> <birthday in
     * format dd.MM.yyyy> <profession> <emailAddress> {hobby}"
     * @throws IllegalArgumentException wird geworfen, wenn das String falsch
     * war </br>
     */
    public RegisterCommand(String input) throws IllegalArgumentException {
        if (input == null) throw new IllegalArgumentException("register: Invalid input parameter");


        //split the input
        String[] inputData = input.split(" ");
        //
        if (!(inputData.length >= 7 && inputData.length <= 8)) {
            throw new IllegalArgumentException("register: Invalid input parameter");
        }
        //split the hobbies
        CopyOnWriteArrayList<String> hobby = new CopyOnWriteArrayList<>();
        if (inputData.length == 8) {
            String[] hobbies = inputData[7].split(",");

            hobby.addAll(Arrays.asList(hobbies));
        }
        // generate the Date
        Calendar date = Calendar.getInstance();
        try {
            date.setTime(MessengerHelper.DATEFORMAT.parse(inputData[4]));
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Illegal Dateformat");
        }
        user = new User(null, inputData[0], MessengerHelper.hashPassword(inputData[1]), inputData[2], inputData[3], (Calendar) date, inputData[5], inputData[6], hobby);
    }

    /**
     * Uebergibt der Clientlist einen neuen User
     *
     * @param server
     * @return benachrigt den User ueber einen positiven Ausgang der
     * Registration
     * @throws RegisterRemoteException benachrigt den User ueber einen negativen
     * Ausgang der Registration
     * @throws RemoteException
     */
    @Override
    public Response execute(IServer server) throws RemoteException {
        System.out.println("User registered " + user.getUname());
        //User darf nicht neu doppelt erstellt werden
        if (!server.getClientList().containsKey(user.getUname())) {
            server.getClientList().put(user.getUname(), user);
        } else {
            throw new RegisterRemoteException("User already exists");
        }
        // User wird ueber den Ausgang 
        return server.getResponseFactory().create("StringResponse", "User was successfully registered");
    }
}
