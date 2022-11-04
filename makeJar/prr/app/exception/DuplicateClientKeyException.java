package prr.app.exception;

import pt.tecnico.uilib.menus.CommandException;

import java.io.Serial;

/**
 * Exception thrown when a client key is duplicated.
 */
public class DuplicateClientKeyException extends CommandException {

    /**
     * Serial number for serialization.
     */
    @Serial
    private static final long serialVersionUID = 202208091753L;

    /**
     * @param key the duplicated key
     */
    public DuplicateClientKeyException(String key) {
        super(Message.duplicateClientKey(key));
    }

}
