package prr.app.exception;

import pt.tecnico.uilib.menus.CommandException;

import java.io.Serial;

/**
 * Exception for unknown clients.
 */
public class UnknownClientKeyException extends CommandException {

    /**
     * Serial number (serialization)
     */
    @Serial
    private static final long serialVersionUID = 202208091753L;

    /**
     * @param key Unknown client to report.
     */
    public UnknownClientKeyException(String key) {
        super(Message.unknownClientKey(key));
    }

}
