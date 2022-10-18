package prr.app.terminals;

import java.util.List;

import prr.core.Network;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show all terminals.
 */
class DoShowAllTerminals extends Command<Network> {

  DoShowAllTerminals(Network receiver) {
    super(Label.SHOW_ALL_TERMINALS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    List<Terminal> list = _receiver.getTerminals();
    for (Terminal t: list)
      _display.addLine(t.toString());
    _display.display();
  }
}
