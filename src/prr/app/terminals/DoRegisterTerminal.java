package prr.app.terminals;

import prr.core.Network;
import prr.core.exception.DuplicateTerminalKeyException;
import prr.core.exception.InvalidTerminalKeyException;
import prr.core.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);
    addStringField("id", Message.terminalKey());
    addStringField("client", Message.clientKey());
    addStringField("type", Message.terminalType());
  }

  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.registerTerminal(stringField("type"),stringField("id"), stringField("client"));

    } catch (DuplicateTerminalKeyException e) {
      throw new prr.app.exception.DuplicateTerminalKeyException(e.getKey());
    } catch (InvalidTerminalKeyException e) {
      throw new prr.app.exception.InvalidTerminalKeyException(e.getKey());
    } catch (UnknownClientKeyException e) {
      throw new prr.app.exception.UnknownClientKeyException(e.getKey());
    }
  }
}
