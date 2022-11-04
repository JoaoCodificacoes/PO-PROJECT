package prr.core.exception;

import java.io.Serial;

/**
 * Exception for unknown terminals.
 */
public class InvalidTerminalKeyException extends Exception {

    /**
     * Serial number for serialization.
     */
    @Serial
    private static final long serialVersionUID = 202208091753L;
    private final String _key;

    /**
     * @param key Unknown terminal to report.
     */
    public InvalidTerminalKeyException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}
