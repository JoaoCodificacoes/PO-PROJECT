package prr.app.client;

import prr.core.Network;
import prr.core.clients.Client;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
//FIXME add more imports if needed

/**
 * Show all clients.
 */
class DoShowAllClients extends Command<Network> {

  DoShowAllClients(Network receiver) {
    super(Label.SHOW_ALL_CLIENTS, receiver);
  }
  
  @Override
  protected final void execute() throws CommandException {
    for (Client c: _receiver.getClients())
      _display.addLine(c.toString());
    _display.display();
    //FIXME implement command
  }
}
