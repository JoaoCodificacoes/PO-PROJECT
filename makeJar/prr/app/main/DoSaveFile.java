package prr.app.main;

import prr.app.exception.FileOpenFailedException;
import prr.core.NetworkManager;
import prr.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {
    DoSaveFile(NetworkManager receiver) {
        super(Label.SAVE_FILE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            if (_receiver.getFileName() == null)
                _receiver.saveAs(Form.requestString(Message.newSaveAs()));
            else
                _receiver.save();
        } catch (FileNotFoundException fnfe) {
            throw new FileOpenFailedException(fnfe);
        } catch (MissingFileAssociationException | IOException e) {
            e.printStackTrace();
        }
    }
}

