package prr.app.exception;

import pt.tecnico.uilib.menus.CommandException;

import java.io.Serial;

/**
 * Exception for reporting general problems opening and processing files.
 */
public class FileOpenFailedException extends CommandException {

    /**
     * Serial number for serialization.
     */
    @Serial
    private static final long serialVersionUID = 202208091753L;

    /**
     * @param e the cause of this exception.
     */
    public FileOpenFailedException(Exception e) {
        super(Message.problemOpeningFile(e), e);
    }

}
