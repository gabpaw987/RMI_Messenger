package rmimessenger.general.responses;

/**
 * Dies ist die Implementierung der abstrakten CommandFactory fuer die <br>
 * ClientCommands. Sie stellt die Moeglichkeit
 *
 * @author Timon Hoebert
 * @author Gabriel Pawlowsky
 * @version 1.0
 */
public class ResponseFactory implements AbstractResponseFactory {

    /**
     * Diese Metode emoeglicht die erzeugung einer Instanz eines ClientCommands.
     *
     * @param param Alle Parameter, die zur Eruzeugung benoietigt werden.
     * @return Das erzeugte ClientCommand
     */
    @Override
    public Response create(Object... param) {

        if (param.length == 2 && param[0] instanceof String && param[0].equals("StringResponse") && param[1] instanceof String) {
            return new StringResponse((String) param[1]);
        }
        return null;
    }
}
