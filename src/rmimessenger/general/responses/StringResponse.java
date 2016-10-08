package rmimessenger.general.responses;

import rmimessenger.general.IClient;
import rmimessenger.general.commands.Command;

/**
 * Die Stringresponse besteht aus einer Textnachricht welche zur Ausfuehrung
 * ausgegeben wird.
 *
 * @author Timon Hoebert
 */
public class StringResponse implements Response {

    private String message;

    /**
     * Erstellt einen neue StringResponse mit Textnachricht
     *
     * @param message die anzuzeigende Nachricht
     */
    public StringResponse(String message) {
        this.message = message;
    }

    /**
     * Gibt die beinhaltende Nachricht aus
     *
     * @param client der aufrufende Client
     * @return die Antwortnachricht: null
     */
    @Override
    public Command execute(IClient client) {
        System.out.println(message);
        return null;
    }
}
