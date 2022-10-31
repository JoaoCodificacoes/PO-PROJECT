package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.UnknownTerminalKeyException;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

  DoSendTextCommunication(Network context, Terminal terminal) {
    super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("toID",Message.terminalKey());
    addStringField("message",Message.textMessage());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      _network.getTerminal(stringField("toID")).useTerminal();
      _receiver.useTerminal();
    } catch (UnknownTerminalKeyException e) {
      throw new prr.app.exception.UnknownTerminalKeyException(e.getKey());

    }
  }
} 
