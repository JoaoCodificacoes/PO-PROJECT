package prr.core.exception;

/** Exception thrown when a terminal key is duplicated. */
public class DuplicateTerminalKeyException extends Exception {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	private String _key;
	/** @param key Duplicate terminal to report. */
	public DuplicateTerminalKeyException(String key) {
		_key = key;
	}

	public String getKey() {
		return _key;
	}
}
