package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.UnknownTerminalKeyException;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

  DoRemoveFriend(Network context, Terminal terminal) {
    super(Label.REMOVE_FRIEND, context, terminal);
    addStringField("friendID",Message.terminalKey());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      _network.removeFriend(_receiver.getId(), stringField("friendID"));
    } catch (UnknownTerminalKeyException e) {
      throw new prr.app.exception.UnknownTerminalKeyException(e.getKey());
    }
  }
}