package prr.app.terminal;

import prr.core.Network;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
// Add more imports if needed

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

  DoPerformPayment(Network context, Terminal terminal) {
    super(Label.PERFORM_PAYMENT, context, terminal);
    //FIXME add command fields
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
  }
}
