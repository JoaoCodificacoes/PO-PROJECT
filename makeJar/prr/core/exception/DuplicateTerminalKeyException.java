package prr.core.exception;

import java.io.Serial;

/**
 * Exception thrown when a terminal key is duplicated.
 */
public class DuplicateTerminalKeyException extends Exception {

    /**
     * Serial number for serialization.
     */
    @Serial
    private static final long serialVersionUID = 202208091753L;
    private final String _key;

    /**
     * @param key Duplicate terminal to report.
     */
    public DuplicateTerminalKeyException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}
