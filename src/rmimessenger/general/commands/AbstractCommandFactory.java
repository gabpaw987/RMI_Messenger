package rmimessenger.general.commands;

/**
 * Die abstrakte Fabrik des abstract Factory Patterns. Diese stellt die
 * Methode<br> zum, erzeugen eines Kommandos zur Verfuegung.
 *
 * @author Gabriel Pawlwosky
 * @version 1.0
 */
public interface AbstractCommandFactory {

    /**
     * Abstrakte Methode zur erzeugung eines Kommndos.
     *
     * @param param alle Parameter die zur Erzeugung benoetigt werden.
     * @return das entsprechende erzeugte Kommando.
     */
    public Command create(Object... param);
}
