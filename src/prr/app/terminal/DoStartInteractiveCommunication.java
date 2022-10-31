package prr.app.terminal;

import prr.core.Network;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

  DoStartInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("toID", Message.terminalKey());
  }

  @Override
  protected final void execute() throws CommandException {
    try {
      _network.getTerminal(stringField("toID")).useTerminal();
      _receiver.useTerminal();
    } catch (prr.core.exception.UnknownTerminalKeyException e) {
      throw new prr.app.exception.UnknownTerminalKeyException(e.getKey());
    }
  }
}
