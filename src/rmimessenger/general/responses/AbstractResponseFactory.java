package rmimessenger.general.responses;

/**
 * Die abstrakte Fabrik des abstract Factory Patterns. Diese stellt die
 * Methode<br> zum, erzeugen eines Kommandos zur Verfuegung.
 *
 * @author Gabriel Pawlwosky
 * @version 1.0
 */
public interface AbstractResponseFactory {

    /**
     * Abstrakte Methode zur erzeugung eines Kommndos.
     *
     * @param param alle Parameter die zur Erzeugung benoetigt werden.
     * @return das entsprechende erzeugte Kommando.
     */
    public abstract Response create(Object... param);
}
