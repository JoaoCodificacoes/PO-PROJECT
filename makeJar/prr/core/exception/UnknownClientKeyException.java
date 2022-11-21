package prr.core.exception;

import java.io.Serial;

/**
 * Exception for unknown clients.
 */
public class UnknownClientKeyException extends Exception {

    /**
     * Serial number (serialization)
     */
    @Serial
    private static final long serialVersionUID = 202208091753L;
    private final String _key;

    /**
     * @param key Unknown client to report.
     */
    public UnknownClientKeyException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}
