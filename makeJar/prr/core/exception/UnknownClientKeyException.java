package prr.core.exception;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Exception for unknown clients.
 */
public class UnknownClientKeyException extends Exception {

	/** Serial number (serialization) */
	private static final long serialVersionUID = 202208091753L;
	private String _key;
	/** @param key Unknown client to report. */
	public UnknownClientKeyException(String key) {
		_key = key;
	}

	public String getKey() {
		return _key;
	}
}
