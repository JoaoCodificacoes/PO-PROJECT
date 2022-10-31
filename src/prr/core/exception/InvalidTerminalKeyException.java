package prr.core.exception;

/** Exception for unknown terminals. */
public class InvalidTerminalKeyException extends Exception {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	private String _key;
	/** @param key Unknown terminal to report. */
	public InvalidTerminalKeyException(String key) {
		_key = key;
	}

	public String getKey() {
		return _key;
	}
}
