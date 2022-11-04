package prr.core.exception;

import java.io.Serial;

/**
 * Exception thrown when a client key is duplicated.
 */
public class DuplicateClientKeyException extends Exception {

    /**
     * Serial number for serialization.
     */
    @Serial
    private static final long serialVersionUID = 202208091753L;
    private final String _key;

    /**
     * @param key the duplicated key
     */
    public DuplicateClientKeyException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}
