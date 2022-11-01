package prr.core.exception;

/**
 * Exception for unknown terminals.
 */
public class UnknownTerminalKeyException extends Exception {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091753L;
    private String _key;

    /**
     * @param key Unknown terminal to report.
     */
    public UnknownTerminalKeyException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}
