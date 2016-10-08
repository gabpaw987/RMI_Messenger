package rmimessenger.general;

import rmimessenger.general.commands.Command;
import rmimessenger.general.commands.CommandFactory;
import rmimessenger.general.commands.TextCommandFactory;
import rmimessenger.general.responses.Response;

/**
 * This is a remote interface for illustrating RMI client callback.
 *
 */
public interface IClient extends java.rmi.Remote {

    /**
     * This remote method is invoked by a callback server to make a callback to
     * an client which implements this interface.
     *
     * @param message a string containing information for the client to process
     * upon being called back.
     * @return the Responsecommand
     * @throws java.rmi.RemoteException
     */
    public Command doResponse(Response r) throws java.rmi.RemoteException;

    public CommandFactory getCommandFactory() throws java.rmi.RemoteException;

    public TextCommandFactory getTextFactory() throws java.rmi.RemoteException;
}
