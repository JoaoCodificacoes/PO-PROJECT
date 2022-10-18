package prr.app.main;

import prr.core.NetworkManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;

import java.io.IOException;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {
  DoSaveFile(NetworkManager receiver) {
    super(Label.SAVE_FILE, receiver);
  }
  
  @Override
  protected final void execute() {

  }
}

